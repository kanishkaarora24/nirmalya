package com.nirmalya.enactus.nirmalya.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nirmalya.enactus.nirmalya.R;
import com.nirmalya.enactus.nirmalya.utilities.Constants;
import com.nirmalya.enactus.nirmalya.utilities.HelperMethods;

public class RegisterActivity extends AppCompatActivity {
    
    private static String LOG_TAG = RegisterActivity.class.getSimpleName();

    //Private variables for activity widgets
    private TextInputEditText emailInputEditText;
    private TextInputEditText passwordInputEditText;
    private TextInputEditText phoneNumberEditText;
    private TextInputEditText addressEditText;
    private TextInputEditText nameEditText;
    private TextInputLayout addressInputLayout;
    private Button registerButton;
    private Spinner userTypeSpinner;
    private ProgressBar progressBar;

    // Private variables for Firebase instances
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mDatabase;
    // Reference to the root of the database;
    private DatabaseReference mDatabaseReference;

    // User attributes
    private String email;
    private String password;
    private String name;
    private String address;
    private String phoneNumber;
    private boolean checkAddress;
    // Set the default value of userType to customer
    private String userType = "customer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Get toolbar and set title
        Toolbar registerToolbar = findViewById(R.id.register_toolbar);
        setSupportActionBar(registerToolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Initialize firebase member variables
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference();

        // Get reference to widgets from the acitivty layout
        nameEditText = findViewById(R.id.name_input_edit_text_register_activity);
        emailInputEditText = findViewById(R.id.email_input_edit_text_register_activity);
        passwordInputEditText = findViewById(R.id.password_input_edit_text_register_activity);
        phoneNumberEditText = findViewById(R.id.phone_number_input_edittext_register_activity);
        addressEditText = findViewById(R.id.address_input_edittext_register_activity);
        addressInputLayout = findViewById(R.id.address_input_layout_register_activity);
        registerButton = findViewById(R.id.register_user_button_register_activity);
        userTypeSpinner = findViewById(R.id.user_type_spinner);
        progressBar = findViewById(R.id.progressBarRegisterActivity);

        // The progress bar should be hidden by default
        progressBar.setVisibility(View.GONE);

        // Create an adapter for the userType Spinner and attach it
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.user_types_array, R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(spinnerAdapter);
        userTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                switch(selectedItem) {
                    case "Customer" : userType = "customer";
                        updateUiOnSpinnerChange(userType);
                        break;
                    case "Trainer" : userType = "trainer";
                        updateUiOnSpinnerChange(userType);
                        break;
                    case "Manufacturer" : userType = "manufacturer";
                        updateUiOnSpinnerChange(userType);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                userType = "customer";
            }
        });

        //Set onClickListener to register button to initiate the registration process
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Assume Email and Password are valid
                boolean passed;
                //Retrieve Email and Password
                email = emailInputEditText.getText().toString();
                password = passwordInputEditText.getText().toString();
                phoneNumber = phoneNumberEditText.getText().toString();
                address = addressEditText.getText().toString();
                name = nameEditText.getText().toString();

                passed = performInputChecks(name, email, password, phoneNumber, address, checkAddress);

                //Will only execute if both checks were successful
                if (passed) {
                    //Disable the register button so that it can't be clicked anymore
                    registerButton.setEnabled(false);
                    //Display a loading indicator
                    progressBar.setVisibility(View.VISIBLE);
                    //Invoke firebase auth's user creation method
                    mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //If user was successfully registered
                                        Toast.makeText(RegisterActivity.this, "You are now registered", Toast.LENGTH_SHORT).show();

                                        mFirebaseUser = mFirebaseAuth.getCurrentUser();

                                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(Constants.SHARED_PREFS_NAME, name);
                                        editor.putString(Constants.SHARED_PREFS_EMAIL, email);
                                        editor.putString(Constants.SHARED_PREFS_PHONE_NUMBER, phoneNumber);
                                        editor.putString(Constants.SHARED_PREFS_ADDRESS, address);
                                        editor.putString(Constants.SHARED_PREFS_ACCOUNT_TYPE, userType);
                                        editor.putBoolean(Constants.SHARED_PREFS_IS_VISIT_REQUESTED, false);
                                        editor.putBoolean(Constants.SHARED_PREFS_IS_PREMIUM_USER, false);
                                        editor.putBoolean(Constants.SHARED_PREFS_IS_VISIT_DONE, false);
                                        editor.putBoolean(Constants.SHARED_PREFS_IS_PROPOSAL_SELECTED, false);
                                        editor.putBoolean(Constants.SHARED_PREFS_IS_ORDER_MANUFACTURED, false);
                                        editor.putBoolean(Constants.SHARED_PREFS_HAS_TRAINER_VISITED, false);
                                        editor.putBoolean(Constants.SHARED_PREFS_IS_INSTALLATION_DONE, false);
                                        editor.apply();

                                        HelperMethods.postUserToDatabase(mDatabaseReference, mFirebaseUser, getApplicationContext());
                                        // When loading is completed, remove the progress bar
                                        progressBar.setVisibility(View.GONE);

                                        Intent intent = new Intent(RegisterActivity.this, ServicesActivity.class);
                                        // Adding these flags to make sure user can't navigate back to Register/Authenticate activity
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        // Once the user has logged in, finish this activity so that they can not go back to it
                                        finish();
                                    } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        // When loading is completed, remove the progress bar
                                        progressBar.setVisibility(View.GONE);
                                        //This block will be executed if there already exists and account with this email address
                                        Toast.makeText(RegisterActivity.this, "Email address is already registered with us", Toast.LENGTH_SHORT).show();
                                        //Re-enable the register button
                                        registerButton.setEnabled(true);
                                    }
                                    else {
                                        // When loading is completed, remove the progress bar
                                        progressBar.setVisibility(View.GONE);
                                        //Failure due to some other reason
                                        Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                        Crashlytics.log(0,  LOG_TAG, task.getException().toString());
                                        //Re-enable the register button
                                        registerButton.setEnabled(true);
                                    }
                                }
                            });
                }
            }
        });

    }

    private void updateUiOnSpinnerChange (String userType) {
        switch (userType) {
            // If user is a customer, don't perform address checks
            case "customer" :
                checkAddress = false;
                addressEditText.setHint("Address (Optional)"); // Not 'Address'
                addressInputLayout.setHint("Address (Optional)");
                break;
            // If user is a manufacturer/trainer, perform address checks and correct hint
            case "manufacturer" :
                checkAddress = true;
                addressEditText.setHint("Address"); // Not 'Address (optional)'
                addressInputLayout.setHint("Address");
                break;
            case "trainer" :
                checkAddress = true;
                addressEditText.setHint("Address"); // Not 'Address (optional)'
                addressInputLayout.setHint("Address");
                break;
        }
    }

    private boolean performInputChecks(String name, String email, String password, String phoneNumber, String address, boolean checkAddress) {
        boolean passed = true;
        //Perform checks on Email, Password and others. If any of the checks fail, set pass value to false
        if (!HelperMethods.isNameValid(name)) { nameEditText.setError("Name can't be empty"); passed = false; }
        if (!HelperMethods.isEmailValid(email)) { emailInputEditText.setError("Invalid email"); passed = false; }
        if (!HelperMethods.isPasswordValid(password)) { passwordInputEditText.setError("Password should be at least 6 characters"); passed = false; }
        if (!HelperMethods.isPhoneNumberValid(phoneNumber)) { phoneNumberEditText.setError("Phone number should be 10 digits"); passed = false; }
        if (checkAddress) {
            if (!HelperMethods.isAddressValid(address)) { addressEditText.setError("Address can't be empty"); passed = false; }
        }
        return passed;
    }
}
