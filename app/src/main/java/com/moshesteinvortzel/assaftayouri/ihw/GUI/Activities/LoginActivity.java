package com.moshesteinvortzel.assaftayouri.ihw.GUI.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Student;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.User;
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
    // private View progressView;
    private LinearLayout signInFormContainer;

    private TextView signUp;
    private ViewGroup signInContainer;

    @Override
    protected void onResume()
    {
        SetVisibility(View.GONE);
        ActivateLogoTransitionInStart();
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
                                    User.Student = new Student(1, "assaft753@gmail.com", "1234567", "Assaf Tayouri");
                                    Thread.sleep(5000);
                                } catch (InterruptedException e)
                                {
                                    e.printStackTrace();
                                }
                                runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        ActivateLogoTransition();
                                        circularButton.revertAnimation();
                                        /*if (CheckInput() == false)
                                        {
                                            Toast toast = new Toast(getApplicationContext());
                                            toast.setDuration(Toast.LENGTH_LONG);
                                            toast.setText("Not Valid Login");
                                            toast.show();
                                         }*/

                                    }
                                });

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

                TransitionManager.go(new Scene(signInContainer), mySwapTransition);
                TransitionManager.beginDelayedTransition(signInContainer);
                signInFormContainer.setVisibility(View.GONE);
                signUp.setVisibility(View.GONE);


                //Intent intent = new Intent(getApplicationContext(), AddNewCourseActivity.class);
                //startActivity(intent);
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
                /*ChangeBounds mySwapTransition = new ChangeBounds();
                mySwapTransition.addListener(new Transition.TransitionListener() {
                    @Override
                    public void onTransitionStart(Transition transition) { }

                    @Override
                    public void onTransitionEnd(Transition transition) {
                        btn.revertAnimation();
                    }

                    @Override
                    public void onTransitionCancel(Transition transition) {}

                    @Override
                    public void onTransitionPause(Transition transition) { }

                    @Override
                    public void onTransitionResume(Transition transition) { }
                });

                TransitionManager.go(new Scene(transitionsContainer), mySwapTransition);
                text.setVisibility(View.VISIBLE);
                pass.setVisibility(View.VISIBLE);*/


                //Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                //Pair pair=new Pair<View,String>(imageView,"assaf");
                //ActivityOptions activityOptions=ActivityOptions.makeCustomAnimation(LoginActivity.this,R.anim.anim2,R.anim.anim1);
                //startActivity(intent,activityOptions.toBundle());
            }
        });
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
        if (this.emailText.getText().toString().isEmpty() == true || this.emailText.getText().toString().contains(" ") || ! this.emailText.getText().toString().contains("@") || ! this.emailText.getText().toString().contains("."))
        {
            return false;
        }
        if (this.passwordText.toString().length() < 6)
        {
            return false;
        }
        return true;
    }


}
