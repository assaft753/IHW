package com.moshesteinvortzel.assaftayouri.ihw.GUI.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Student;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.User;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class CreateAccountActivity extends AppCompatActivity
{

    private TextView firstName;
    private TextView lastName;
    private TextView email;
    private TextView password;
    private CircularProgressButton circularProgressButton;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firstName = findViewById(R.id.firstNameText);
        lastName = findViewById(R.id.lastNameText);
        email = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);
        circularProgressButton = findViewById(R.id.buttonSignUp);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        circularProgressButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (CheckInput())
                {
                    DisEnableText(false);
                    circularProgressButton.startAnimation();
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        CreateAccount(user.getUid());
                                        MoveToMainActivity();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        DisEnableText(true);
                                        circularProgressButton.revertAnimation();
                                    }

                                }
                            });
                }
            }
        });

    }

    private void MoveToMainActivity()
    {
        circularProgressButton.doneLoadingAnimation(0xff4f4f4f, BitmapFactory.decodeResource(getResources(), R.drawable.v));
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    });

                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private boolean CheckInput()
    {
        if (firstName.getText().toString().trim().equals("") || lastName.getText().toString().trim().equals("")
                || email.getText().toString().trim().equals("")
                || password.getText().toString().trim().equals(""))
        {
            return false;
        }
        if (! email.getText().toString().contains("@") || ! email.getText().toString().contains("."))
        {
            return false;
        }
        if (password.getText().toString().length() < 6)
        {
            return false;
        }
        return true;
    }

    private void DisEnableText(boolean enable)
    {
        firstName.setEnabled(enable);
        lastName.setEnabled(enable);
        email.setEnabled(enable);
        password.setEnabled(enable);
    }

    private void CreateAccount(String userID)
    {
        Student student = new Student(1, email.getText().toString(), password.getText().toString(), userID, firstName.getText().toString() + " " + lastName.getText().toString());
        User.Student = student;
        myRef.child(userID).setValue(student);

    }
}
