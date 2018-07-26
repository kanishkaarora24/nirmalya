package com.nirmalya.enactus.nirmalya.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nirmalya.enactus.nirmalya.R;
import com.nirmalya.enactus.nirmalya.utilities.Constants;
import com.nirmalya.enactus.nirmalya.utilities.HelperMethods;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    // Private variables for activity widgets
    private TextInputEditText emailInputEditText;
    private TextInputEditText passwordInputEditText;
    private Button loginButton;
    private Button forgotPasswordButton;
    private ProgressBar loginProgressBar;

    // Private variables for Firebase instances
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mDatabase;
    // Reference to the root of the database;
    private DatabaseReference registeredUsersReference;
    private DatabaseReference rootReference;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get toolbar and set title
        Toolbar loginToolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(loginToolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initialize firebase member variables
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        registeredUsersReference = mDatabase.getReference().child("registered_users");
        rootReference = mDatabase.getReference();

        sharedPreferences = getApplicationContext().getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE);

        //Get reference to widgets from the acitivty layout
        emailInputEditText = findViewById(R.id.email_input_edit_text_login_activity);
        passwordInputEditText = findViewById(R.id.password_input_edit_text_login_activity);
        loginButton = findViewById(R.id.login_button_login_activity);
        forgotPasswordButton = findViewById(R.id.forgot_password_button_login_activity);
        loginProgressBar = findViewById(R.id.progressBar_login_activity);
        loginProgressBar.setVisibility(View.GONE);

        // Set onClickListener to Login button to initiate login process
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Assume Email and Password are valid
                boolean passed = true;
                //Retrieve Email and Password
                String email = emailInputEditText.getText().toString();
                String password = passwordInputEditText.getText().toString();

                if (!HelperMethods.isEmailValid(email)) { emailInputEditText.setError("Invalid email"); passed = false; }
                if (password.length() == 0) {
                    passwordInputEditText.setError("Please enter a password");
                    passed = false;
                }

                //Will only execute if both checks were successful
                if (passed) {
                    //Display a loading indicator
                    loginProgressBar.setVisibility(View.VISIBLE);
                    //Disable the login button so that it can not be clicked anymore
                    loginButton.setEnabled(false);
                    // Invoke Firebase Auth's sign in flow
                    mFirebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // When loading is completed, remove the progress bar
                                    if (task.isSuccessful()) {
                                        mFirebaseUser = mFirebaseAuth.getCurrentUser();
                                        HelperMethods.updateUserInSharedPreferences(rootReference, mFirebaseUser, getApplicationContext());
                                        loginProgressBar.setVisibility(View.GONE);
                                        Intent intent = new Intent(LoginActivity.this, ServicesActivity.class);
                                        // Adding these flags to the intent to make sure the user can not navigate back to authenticate/login activity
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    } else if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                        loginProgressBar.setVisibility(View.GONE);
                                        // This block is executed if the email address entered is not registered in the database
                                        Toast.makeText(LoginActivity.this, "Email is not registered with us", Toast.LENGTH_SHORT).show();
                                        //Re-enable the login button
                                        loginButton.setEnabled(true);
                                    } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                        loginProgressBar.setVisibility(View.GONE);
                                        // This block is executed if the password is not valid for the given email address
                                        Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                                        //Re-enable the login button
                                        loginButton.setEnabled(true);
                                    } else {
                                        loginProgressBar.setVisibility(View.GONE);
                                        // If the login fails for some other reason, then this block is executed
                                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                        //Re-enable the login button
                                        loginButton.setEnabled(true);
                                    }
                                }
                            });
                }
            }
        });

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInputEditText.getText().toString();
                if (HelperMethods.isEmailValid(email)) {
                    mFirebaseAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Check your email for password reset instructions.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Password reset request failed.", Toast.LENGTH_SHORT).show();
                                        Crashlytics.log(0,  LOG_TAG, task.getException().toString());
                                    }
                                }
                            });
                }
            }
        });


    }
}
