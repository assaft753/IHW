package com.moshesteinvortzel.assaftayouri.ihw.GUI.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Course;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Exam;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.HomeWork;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Student;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.User;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary.CalendarHelper;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity
{
    private EditText emailText;
    private EditText passwordText;
    private CircularProgressButton circularButton;
    private ImageView logoImage;
    private LinearLayout signInFormContainer;
    private DatabaseReference myRef;
    private TextView signUp;
    private ViewGroup signInContainer;
    private FirebaseAuth mAuth;

    @Override
    protected void onResume()
    {
        SetVisibility(View.GONE);
        if (mAuth.getCurrentUser() == null)
        {
            System.out.println("No Auto User LOGerd IN");
            ActivateLogoTransitionInStart();
        }
        else
        {
            System.out.println("Auto User LOGerd IN");
            MoveToMain();
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signInFormContainer = (LinearLayout) findViewById(R.id.signInInputContainer);
        signInContainer = (ViewGroup) findViewById(R.id.signInContainer);
        logoImage = (ImageView) findViewById(R.id.logoImage);
        emailText = (EditText) signInFormContainer.findViewById(R.id.emailText);
        passwordText = (EditText) signInFormContainer.findViewById(R.id.passwordText);
        signUp = (TextView) findViewById(R.id.signUpLink);
        circularButton = (CircularProgressButton) findViewById(R.id.buttonLogin);
        myRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        circularButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                ChangeBounds mySwapTransition = new ChangeBounds();
                mySwapTransition.addListener(new Transition.TransitionListener()
                {
                    @Override
                    public void onTransitionStart(Transition transition)
                    {
                    }

                    @Override
                    public void onTransitionEnd(Transition transition)
                    {

                        circularButton.startAnimation();
                        new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                try
                                {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e)
                                {
                                    e.printStackTrace();
                                }

                                mAuth.signInWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString()).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>()
                                {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task)
                                    {
                                        if (task.isSuccessful())
                                        {
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            System.out.println(user.getUid());
                                            MoveToMain();
                                        }
                                        else
                                        {
                                            FailLogin();
                                            Toast.makeText(LoginActivity.this, "No Such User Exists", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });





                                    /*LoginActivity.this.myRef.addListenerForSingleValueEvent(new ValueEventListener()
                                    {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot)
                                        {

                                            for (DataSnapshot snapshot : dataSnapshot.getChildren())
                                            {
                                                System.out.println(snapshot.getKey());
                                                //if(emailText.equals(snapshot.getKey()))
                                                //{
                                                for (DataSnapshot field : snapshot.getChildren())
                                                {
                                                    System.out.println(field.getKey());
                                                }
                                                //}
                                                //System.out.println(snapshot.getValue());
                                                //InitStudent(snapshot);
                                            }
                                            runOnUiThread(new Runnable()
                                            {
                                                @Override
                                                public void run()
                                                {
                                                    MoveToMain();
                                                    //ActivateLogoTransition();
                                                    //circularButton.revertAnimation();
                                                }
                                            });


                                        @Override
                                        public void onCancelled(DatabaseError error)
                                        {

                                        }
                                    });*/
                            }
                        }).start();
                    }

                    @Override
                    public void onTransitionCancel(Transition transition)
                    {
                    }

                    @Override
                    public void onTransitionPause(Transition transition)
                    {
                    }

                    @Override
                    public void onTransitionResume(Transition transition)
                    {
                    }
                });

                if (CheckInput())
                {
                    TransitionManager.go(new Scene(signInContainer), mySwapTransition);
                    TransitionManager.beginDelayedTransition(signInContainer);
                    signInFormContainer.setVisibility(View.GONE);
                    signUp.setVisibility(View.GONE);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                }
            }
        });


        signUp.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(LoginActivity.this, R.anim.anim2, R.anim.anim1);
                startActivity(intent, activityOptions.toBundle());
            }
        });
    }

    private void InitStudent(DataSnapshot snapshot)
    {
        Student value = snapshot.getValue(Student.class);
        User.Student = value;
        if (value.getCalendarManager() != null)
        {
            for (CalendarHelper calendarHelper : value.getCalendarManager())
            {
                calendarHelper.setCourse(value.FindCourseByName(calendarHelper.getCourseName()));
            }
        }
        for (Course course : value.getCourses())
        {
            for (HomeWork homeWork : course.getHomeWorks())
            {
                homeWork.setCourse(course);
                if (homeWork.getFinished())
                {
                    value.getCompletedHW().add(homeWork);
                }
                else
                {
                    value.getUncompletedHW().add(homeWork);
                }
            }

            for (Exam exam : course.getExams())
            {
                if (exam != null)
                {
                    exam.setCourse(course);
                    course.GetExamsAsArray()[exam.getTerm().ordinal()] = exam;
                    if (exam.getGraded())
                    {
                        value.getGradedExams().add(exam);
                    }
                    else
                    {
                        value.getUngradedExams().add(exam);
                    }
                }
            }

        }
    }

    private void MoveToMain()
    {
        System.out.println("Moving Tosssss Main");
        circularButton.doneLoadingAnimation(0xff4f4f4f, BitmapFactory.decodeResource(getResources(), R.drawable.v));
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(2000);
                    final String userID = mAuth.getCurrentUser().getUid();
                    LoginActivity.this.myRef.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {
                            boolean found = false;
                            for (DataSnapshot snapshot : dataSnapshot.getChildren())
                            {
                                if (snapshot.getKey().equals(userID))
                                {
                                    InitStudent(snapshot);
                                    found = true;
                                }
                            }
                            if (! found)
                            {
                                runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        ActivateLogoTransition();
                                        circularButton.revertAnimation();
                                    }
                                });
                            }
                            else
                            {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error)
                        {
                            Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                    });

                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();



        /*Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(LoginActivity.this, R.anim.anim2, R.anim.anim1);
        startActivity(intent, activityOptions.toBundle());*/
    }

    private void ActivateLogoTransitionInStart()
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            ActivateLogoTransition();
                        }
                    });
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void ActivateLogoTransition()
    {
        TransitionManager.beginDelayedTransition(signInContainer);
        SetVisibility(View.VISIBLE);
    }

    private void SetVisibility(int visibility)
    {
        signInFormContainer.setVisibility(visibility);
        circularButton.setVisibility(visibility);
        signUp.setVisibility(visibility);
    }

    private boolean CheckInput()
    {
        if (emailText.getText().toString().trim().equals("") || emailText.getText().toString().trim().equals(""))
        {
            return false;
        }
        if (! emailText.getText().toString().contains("@") || ! emailText.getText().toString().contains("."))
        {
            return false;
        }
        return true;
    }

    private void FailLogin()
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                ActivateLogoTransition();
                circularButton.revertAnimation();
            }
        });
    }


}
