package com.nirmalya.enactus.nirmalya.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nirmalya.enactus.nirmalya.R;
import com.nirmalya.enactus.nirmalya.model.VisitRequest;
import com.nirmalya.enactus.nirmalya.utilities.Constants;
import com.nirmalya.enactus.nirmalya.utilities.HelperMethods;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RequestVisitFragment extends Fragment {

    private static final String LOG_TAG = RequestVisitFragment.class.getSimpleName();

    private TextView titleTextView;
    private TextView descriptionTextView;
    private ImageView doneIconImageView;
    private TextInputLayout addressInputLayout;
    private TextInputEditText addressInputEditText;
    private Button requestVisitButton;
    private ProgressBar progressBar;
    private String currentDate;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.request_visit_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.request_visit_progress_bar);
        titleTextView = view.findViewById(R.id.request_fragment_title_textview);
        descriptionTextView = view.findViewById(R.id.request_fragment_description_textview);
        doneIconImageView = view.findViewById(R.id.visit_requested_icon);
        addressInputLayout = view.findViewById(R.id.request_fragment_address_input_layout);
        addressInputEditText = view.findViewById(R.id.request_fragment_address_input_edittext);
        requestVisitButton = view.findViewById(R.id.request_visit_button);

        progressBar.setVisibility(View.GONE);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        /*
        If the currentUser object has requested a meeting already,
        then switch to the visit requested layout.
        */
        if (Constants.currentUser.isMeetingRequested()) {
            switchToVisitRequestedLayout();
        } else {
            switchToVisitNotRequestedLayout();
        }

        addressInputEditText.setText(Constants.currentUser.getAddress());

        requestVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addressInputEditText.getText().toString().isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);
                    requestVisitButton.setEnabled(false);
                    Date date = Calendar.getInstance().getTime();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    currentDate = dateFormat.format(date);

                    String uid = mFirebaseUser.getUid();
                    String name = Constants.currentUser.getName();
                    String address = addressInputEditText.getText().toString();
                    String phoneNumber = Constants.currentUser.getPhone();
                    VisitRequest visitRequest = new VisitRequest(uid, name, address, currentDate, phoneNumber);
                    HelperMethods.postVisitRequestToDatabase(mDatabaseReference, visitRequest);
                    progressBar.setVisibility(View.GONE);
                    switchToVisitRequestedLayout();
                } else {
                    addressInputEditText.setError("Address can't be empty");
                }
            }
        });

    }

    private void switchToVisitRequestedLayout() {
        doneIconImageView.setVisibility(View.VISIBLE);
        addressInputLayout.setVisibility(View.GONE);
        requestVisitButton.setVisibility(View.GONE);
        titleTextView.setText(R.string.vist_requested_title);
        descriptionTextView.setText(R.string.request_visited_text);
        progressBar.setVisibility(View.GONE);
    }

    private void switchToVisitNotRequestedLayout() {
        doneIconImageView.setVisibility(View.GONE);
        addressInputLayout.setVisibility(View.VISIBLE);
        requestVisitButton.setVisibility(View.VISIBLE);
        titleTextView.setText(R.string.request_a_visit_from_us_title);
        descriptionTextView.setText(R.string.request_visit_text);
    }
}
