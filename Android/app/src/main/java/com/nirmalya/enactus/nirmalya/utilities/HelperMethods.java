package com.nirmalya.enactus.nirmalya.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nirmalya.enactus.nirmalya.model.User;
import com.nirmalya.enactus.nirmalya.model.VisitRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelperMethods {

    private static final String LOG_TAG = HelperMethods.class.getSimpleName();

    public static boolean isEmailValid(String email) {
        //Check if the email entered matches the following regexp
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /* The password is considered valid if it is
    at least 4 characters long.
     */
    public static boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    /* The phone number is considered valid if its length is
    at least 10 characters. This should probably be replaced by a regexp
    check instead.
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.trim().length() == 10;
    }

    /* The address should not be empty to be considered
    a valid address
     */
    public static boolean isAddressValid(String address) {
        return address.trim().length() > 0;
    }

    /* The name shouldn't be empty to be considered
    a valid name.
     */
    public static boolean isNameValid(String name) {
        return name.trim().length() > 0;
    }

    /*Setting the display name of the user to the profile itself.
      The rest will be saved to a dedicated node in the 'users' child
      of the database tree.
      */

    public static void postUserToDatabase(DatabaseReference databaseReference, FirebaseUser firebaseUser, Context applicationContext) {

        SharedPreferences preferences = applicationContext.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE);

        String name = preferences.getString("name", "");
        String email = preferences.getString("email", "");
        String address = preferences.getString("address", "");
        String phoneNumber = preferences.getString("phoneNumber", "");
        String accountType = preferences.getString("accountType", "");

        User user = new User(
                name,
                email,
                phoneNumber,
                address,
                accountType
        );

        databaseReference.getRoot().child("registered_users").child(firebaseUser.getUid()).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Crashlytics.log(0,  LOG_TAG, "Successfully pushed user to the database");
                        } else {
                            Crashlytics.log(0,  LOG_TAG, task.getException().toString());
                        }
                    }
                });
    }

    public static void updateUserInSharedPreferences(DatabaseReference databaseReference, FirebaseUser firebaseUser, final Context applicationContext) {

        Query userQuery = databaseReference.child("registered_users").child(firebaseUser.getUid());
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Crashlytics.log(0,  LOG_TAG, dataSnapshot.toString());
                    User user = dataSnapshot.getValue(User.class);
                    // Save the user details to a sharedPreferences file to persist changes
                    SharedPreferences preferences = applicationContext.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(Constants.SHARED_PREFS_NAME, user.getName());
                    editor.putString(Constants.SHARED_PREFS_EMAIL, user.getEmail());
                    editor.putString(Constants.SHARED_PREFS_PHONE_NUMBER, user.getPhone());
                    editor.putString(Constants.SHARED_PREFS_ADDRESS, user.getAddress());
                    editor.putString(Constants.SHARED_PREFS_ACCOUNT_TYPE, user.getAccountType());
                    editor.putBoolean(Constants.SHARED_PREFS_IS_VISIT_REQUESTED, user.isMeetingRequested());
                    editor.putBoolean(Constants.SHARED_PREFS_IS_PREMIUM_USER, user.isPremiumUser());
                    editor.putBoolean(Constants.SHARED_PREFS_IS_VISIT_DONE, user.isVisitDone());
                    editor.putBoolean(Constants.SHARED_PREFS_IS_PROPOSAL_SELECTED, user.isProposalSelected());
                    editor.putBoolean(Constants.SHARED_PREFS_IS_ORDER_MANUFACTURED, user.isOrderManufactured());
                    editor.putBoolean(Constants.SHARED_PREFS_HAS_TRAINER_VISITED, user.isTrainerVisitComplete());
                    editor.putBoolean(Constants.SHARED_PREFS_IS_INSTALLATION_DONE, user.isInstallationDone());
                    editor.apply();
                    Crashlytics.log(0,  LOG_TAG, "Currently stored user details:" + preferences.getAll());
                } else {
                    Crashlytics.log(0,  LOG_TAG, "Failed to get current user: " + dataSnapshot.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Crashlytics.log(0,  LOG_TAG, "Database error in getting current User: " + databaseError.getDetails());

            }
        });
    }

    public static void postVisitRequestToDatabase(DatabaseReference databaseReference, VisitRequest visitRequest) {

        // Post visit request to database
        databaseReference.getRoot().child("visit_requests").child(visitRequest.getCustomerName() + "-" + visitRequest.getCustomerUid()).setValue(visitRequest)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Crashlytics.log(0,  LOG_TAG,"Successfully posted visit request to database");
                        } else {
                            Crashlytics.log(0,  LOG_TAG, "Failed to post request visit to database: " + task.getException().toString());
                        }
                    }
                });

        // Set meetingRequested parameter to true for the user
        databaseReference.child("registered_users").child(visitRequest.getCustomerUid()).child("meetingRequested").setValue(true)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Crashlytics.log(0,  LOG_TAG, "Succesfully updated meetingRequested parameter for the user");
                        } else {
                            Crashlytics.log(0,  LOG_TAG, "Could not update meetingRequested parameter for the user: " + task.getException().toString());
                        }
                    }
                });
    }
}
