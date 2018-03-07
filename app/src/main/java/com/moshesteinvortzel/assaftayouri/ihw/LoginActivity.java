package com.moshesteinvortzel.assaftayouri.ihw;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AndroidException;
import android.util.Pair;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity
{
    private AutoCompleteTextView emailView;
    private EditText passwordView;
    private View progressView;
    private View loginFormView;
    private ViewGroup signInContainer;
    private ViewGroup inputFormContainer;
    private ImageView imageView;
    private EditText emailText ;
    private EditText passwordText ;
    private CircularProgressButton circularButton;
    private TextView signUp;

    @Override
    protected void onResume()
    {
        inputFormContainer.setVisibility(View.GONE);
        circularButton.setVisibility(View.GONE);
        signUp.setVisibility(View.GONE);
        ActivateLogoTransitionInStart();
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputFormContainer = (ViewGroup) findViewById(R.id.inputForm);
        signInContainer = (ViewGroup) findViewById(R.id.signInContainer);
        imageView=(ImageView)findViewById(R.id.logoImage);
        emailText = (EditText) inputFormContainer.findViewById(R.id.emailTextBox);
        passwordText=(EditText) inputFormContainer.findViewById(R.id.passwordTextBox);
        signUp= findViewById(R.id.signUpLink);
        circularButton = (CircularProgressButton) findViewById(R.id.buttonLogin);


        circularButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                ChangeBounds mySwapTransition = new ChangeBounds();
                mySwapTransition.addListener(new Transition.TransitionListener() {
                    @Override
                    public void onTransitionStart(Transition transition) { }

                    @Override
                    public void onTransitionEnd(Transition transition) {
                        circularButton.startAnimation();
                        new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                try
                                {
                                    Thread.sleep(5000);
                                }
                                catch (InterruptedException e)
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

                                    }
                                });

                            }
                        }).start();
                    }

                    @Override
                    public void onTransitionCancel(Transition transition) {}

                    @Override
                    public void onTransitionPause(Transition transition) { }

                    @Override
                    public void onTransitionResume(Transition transition) { }
                });

                TransitionManager.go(new Scene(signInContainer), mySwapTransition);


                //TransitionManager.beginDelayedTransition(transitionsContainer);
                inputFormContainer.setVisibility(View.GONE);
                //emailText.setVisibility(View.GONE);
                //passwordText.setVisibility(View.GONE);
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
                ActivityOptions activityOptions=ActivityOptions.makeCustomAnimation(LoginActivity.this,R.anim.anim2,R.anim.anim1);
                startActivity(intent,activityOptions.toBundle());
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
        inputFormContainer.setVisibility(View.VISIBLE);
        circularButton.setVisibility(View.VISIBLE);
        signUp.setVisibility(View.VISIBLE);
    }


}
