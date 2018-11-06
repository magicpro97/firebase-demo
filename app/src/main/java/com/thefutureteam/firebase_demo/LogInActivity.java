package com.thefutureteam.firebase_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thefutureteam.firebase_demo.models.User;

public class LogInActivity
    extends BaseActivity implements View.OnClickListener
{
    private static final String TAG = LogInActivity.class.getSimpleName();

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mLogInButton;
    private Button mSignUpButton;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.fieldPassword);
        mLogInButton = findViewById(R.id.buttonSignIn);
        mSignUpButton = findViewById(R.id.buttonSignUp);

        mLogInButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            onAuthSuccess(mAuth.getCurrentUser());
        }
    }

    private void logIn() {
        Log.d(TAG, "logIn: ");
        if (vadidateForm()) {
            return;
        }

        showProgressDialog();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
             .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
             {
                 @Override
                 public void onComplete(@NonNull final Task<AuthResult> task)
                 {
                     Log.d(TAG, "onComplete: " + task.isSuccessful());
                     hideProgressDialog();

                     if (task.isSuccessful()) {
                        onAuthSuccess(task.getResult().getUser());
                     } else {
                         Toast.makeText(LogInActivity.this, "Log In failed", Toast.LENGTH_SHORT).show();
                     }
                 }
             });
    }

    private void signUp() {
        Log.d(TAG, "signUp: ");
        if (vadidateForm()) {
            return;
        }

        showProgressDialog();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
             .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
             {
                 @Override
                 public void onComplete(@NonNull final Task<AuthResult> task)
                 {
                     Log.d(TAG, "onComplete: " + task.isSuccessful());
                     hideProgressDialog();

                     if (task.isSuccessful()) {
                         onAuthSuccess(task.getResult().getUser());
                     } else {
                         Toast.makeText(LogInActivity.this, "Sign Up failed", Toast.LENGTH_SHORT).show();
                     }
                 }
             });
    }

    private void onAuthSuccess(final FirebaseUser currentUser)
    {
        String username = usernameFromEmail(currentUser.getEmail());

        writeNewUser(currentUser.getUid(), username, currentUser.getEmail());

        startActivity(new Intent(LogInActivity.this, MainActivity.class));
        finish();
    }

    private String usernameFromEmail(final String email)
    {
        if(email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private boolean vadidateForm() {
        boolean result = true;
        if(TextUtils.isEmpty(mEmailField.getText().toString())) {
            mEmailField.setError("Required");
            result = false;
        } else {
            mEmailField.setError(null);
        }

        if(TextUtils.isEmpty(mPasswordField.getText().toString())) {
            mPasswordField.setError("Required");
            result = false;
        } else {
            mPasswordField.setError(null);
        }

        return !result;
    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
    }


    @Override
    public void onClick(final View v)
    {
        switch (v.getId()) {
            case R.id.buttonSignIn: {
                logIn();
            }
            case R.id.buttonSignUp: {
                signUp();
            }
        }
    }

}
