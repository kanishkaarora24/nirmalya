package com.nirmalya.enactus.nirmalya.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.nirmalya.enactus.nirmalya.R;

import java.util.Arrays;
import java.util.List;

public class AuthenticateActivity extends AppCompatActivity {

    private static final String LOG_TAG = AuthenticateActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 1; // Sign-in code returned by FirebaseAuthUI

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    //Setting Email as the only authentication option for now
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);
        /*
        Set the status bar color to black so that it better blends in
        with the background image.
         */
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.statusBarColorMainActivity));

        mFirebaseAuth = FirebaseAuth.getInstance();

        Button mLoginButton = findViewById(R.id.login_button);
        Button mRegisterButton = findViewById(R.id.register_button);
        Button mGuestButton = findViewById(R.id.guest_button);
        Button mAbout_Us = findViewById(R.id.about_nirmalya_button);


        // If the user clicks the register button, take him to the registration screen
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(AuthenticateActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        // If the user clicks the login button, take him to the login activity
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(AuthenticateActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        // If the user clicks on the About us button then take him to abouts us activity
        mAbout_Us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthenticateActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });

        mGuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthenticateActivity.this, ServicesActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
