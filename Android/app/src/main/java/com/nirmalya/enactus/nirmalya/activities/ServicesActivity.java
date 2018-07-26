package com.nirmalya.enactus.nirmalya.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nirmalya.enactus.nirmalya.R;
import com.nirmalya.enactus.nirmalya.adapters.ServicesAdapter;
import com.nirmalya.enactus.nirmalya.model.User;
import com.nirmalya.enactus.nirmalya.utilities.Constants;
import com.nirmalya.enactus.nirmalya.utilities.HelperMethods;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.Crash;

public class ServicesActivity extends AppCompatActivity {

    private static final String LOG_TAG = ServicesActivity.class.getSimpleName();
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private FirebaseUser mFirebaseUser;
    private ValueEventListener userDetailsUpdateEventListener;
    private DatabaseReference currentUserReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        // Get an instance of Firebase Authentication
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Toolbar servicesActivityToolbar = findViewById(R.id.services_activity_toolbar);
        setSupportActionBar(servicesActivityToolbar);

        RecyclerView rv = findViewById(R.id.services_recycler_view);
        ServicesAdapter servicesAdapter = new ServicesAdapter(this, Constants.getServiceItems(), Constants.getServiceItemDrawableIds());
        rv.setHasFixedSize(true);
        rv.setAdapter(servicesAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        if (mFirebaseUser != null) {
            currentUserReference = mDatabaseReference.child("registered_users").child(mFirebaseUser.getUid());
            userDetailsUpdateEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Constants.currentUser = dataSnapshot.getValue(User.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            };

            /*
             Update FCM token in the database on every onCreate of this activity.
             FCM Token is important for notifications. I want to make sure that
             we always have the latest version for any given user.
              */
            String token = FirebaseInstanceId.getInstance().getToken();
            Crashlytics.log(0,  LOG_TAG, "Retrieved token from Instance ID Service: " + token);
            mDatabaseReference.child("registered_users").child(mFirebaseUser.getUid()).child("fcm_token").setValue(token)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Crashlytics.log(0,  "NirmalyaApp", "Successfully updated FCM token in the database.");
                        }
                    });
        }
    }

    @Override
    protected void onStart() {
        if (mFirebaseUser != null) {
            currentUserReference.addValueEventListener(userDetailsUpdateEventListener);
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (mFirebaseUser != null) {
            currentUserReference.removeEventListener(userDetailsUpdateEventListener);
        }
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Crashlytics.log(0,  "Menu", "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.services_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Crashlytics.log(0,  "Menu", "onPrepareOptionsMenu");
        if (mFirebaseUser == null) {
            // If the user is currently not signed in, remove the options to sign out and edit profile
            menu.findItem(R.id.sign_in_menu_option).setVisible(true);
            menu.findItem(R.id.sign_out_menu_option).setVisible(false);
        } else {
            // If the user is currently signed in, remove the option to sign in from the menu
            menu.findItem(R.id.sign_out_menu_option).setVisible(true);
            menu.findItem(R.id.sign_in_menu_option).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Crashlytics.log(0,  "Menu", "onOptionsItemSelected");
        switch(item.getItemId()) {
            case R.id.sign_out_menu_option:
                if (mFirebaseUser != null) {
                    mFirebaseAuth.signOut();
                    // IT IS NECESSARY TO UPDATE THE CURRENT USER VALUE TO NULL AGAIN
                    // I WASTED AN ENTIRE DAY FIGURING OUT WHY THE MENU WON'T UPDATE AFTER SIGNING OUT
                    // DON'T MAKE THE SAME MISTAKES I DID.
                    mFirebaseUser = null;
                    Toast.makeText(this, "You are now signed out!", Toast.LENGTH_SHORT).show();

                    // Clear the data stored in shared preferences when the user signs out
                    SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(Constants.SHARED_PREFS, 0).edit();
                    editor.clear().apply();

                }
                invalidateOptionsMenu();
                return true;
            case R.id.sign_in_menu_option:
                if (mFirebaseUser == null) {
                    Intent intent = new Intent (this, AuthenticateActivity.class);
                    startActivity(intent);
                }
                invalidateOptionsMenu();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}
