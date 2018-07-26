package com.nirmalya.enactus.nirmalya.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nirmalya.enactus.nirmalya.R;
import com.nirmalya.enactus.nirmalya.activities.OrderActivity;
import com.nirmalya.enactus.nirmalya.utilities.Constants;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

public class OverviewFragment extends Fragment implements VerticalStepperForm {

    String[] titles = {"Location Visit",
            "Proposal Selection",
            "Payment",
            "Installation",
            "Trainer Visit"};
    String[] subtitles = {"A visit from our team to your address",
            "Selection of a proposal from the given list",
            "Payment status for the order",
            "Installation of equipment",
            "A visit from our trainer to your address"};
    private VerticalStepperFormLayout verticalStepperForm;

    /*
    Setting completedStep to a negative value so that
    it can not correspond to an actual step value by default.
     */
    private int completedStep = -1;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private TextView nextMonitoringVisitTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.overview_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nextMonitoringVisitTextView = view.findViewById(R.id.monitoring_visit_date_textview);

        int colorPrimary = ContextCompat.getColor(getContext(), R.color.primaryColor);
        int colorSecondary = ContextCompat.getColor(getContext(), R.color.secondaryColor);
        int colorWhite = ContextCompat.getColor(getContext(), android.R.color.white);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        verticalStepperForm = view.findViewById(R.id.vertical_stepper_form_overview_fragment);
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, titles, this, getActivity())
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorSecondary)
                .stepsSubtitles(subtitles)
                .displayBottomNavigation(false)
                .stepNumberBackgroundColor(colorPrimary)
                .stepNumberTextColor(colorWhite)
                .showVerticalLineWhenStepsAreCollapsed(true)
                .materialDesignInDisabledSteps(true)
                .init();

        if (Constants.currentUser.getNextMonitoringVisit() != null) {
            nextMonitoringVisitTextView.setText(Constants.currentUser.getNextMonitoringVisit());
        } else {
            nextMonitoringVisitTextView.setText("Not scheduled yet");
        }
    }

    @Override
    public View createStepContentView(int stepNumber) {

        TextView view = new TextView(getContext());
        switch (stepNumber) {
            case 0:
                view = createLocationVisitedStep();
                break;
            case 1:
                view = createProposalSelectedStep();
                break;
            case 2:
                view = createPaymentStep();
                break;
            case 3:
                view = createInstallationStep();
                break;
            case 4:
                view = createTrainerVisitStep();
                break;
        }
        return view;
    }

    @Override
    public void onStepOpening(int stepNumber) {
        switch (stepNumber) {
            case 0:
                if (Constants.currentUser.isMeetingRequested()) {
                    if (Constants.currentUser.isVisitDone()) {
                        verticalStepperForm.setActiveStepAsCompleted();
                    } else {
                        verticalStepperForm.setActiveStepAsUncompleted(Constants.WE_WILL_VISIT_SOON_STRING);
                    }
                } else {
                    verticalStepperForm.setActiveStepAsUncompleted(Constants.VISIT_NOT_REQUESTED_STRING);
                }
                break;
            case 1:
                if (Constants.currentUser.isProposalSelected()) {
                    verticalStepperForm.setActiveStepAsCompleted();
                } else {
                    verticalStepperForm.setActiveStepAsUncompleted(Constants.WORKING_ON_IT_STRING);
                }
                break;
            case 2:
                if (Constants.currentUser.isPremiumUser()) {
                    verticalStepperForm.setActiveStepAsCompleted();
                } else {
                    verticalStepperForm.setActiveStepAsUncompleted(Constants.PAYMENT_NOT_DONE);
                }
                break;
            case 3:
                if (Constants.currentUser.isInstallationDone()) {
                    verticalStepperForm.setActiveStepAsCompleted();
                } else {
                    verticalStepperForm.setActiveStepAsUncompleted(Constants.WORKING_ON_IT_STRING);
                }
                break;
            case 4:
                if (Constants.currentUser.isTrainerVisitComplete()) {
                    verticalStepperForm.setActiveStepAsCompleted();
                } else {
                    verticalStepperForm.setActiveStepAsUncompleted(Constants.WORKING_ON_IT_STRING);
                }
                break;
            default:
                // Default case will be executed for the confirmation step
                verticalStepperForm.setActiveStepAsCompleted();
        }
    }

    @Override
    public void sendData() {
        Toast.makeText(getContext(), "Order complete! Thanks for being a Nirmalya customer.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        goToLastCompletedStep();
    }

    private TextView createLocationVisitedStep() {
        TextView tv = new TextView(getContext());
        if (Constants.currentUser.isVisitDone()) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
            tv.setText(Constants.PRESS_CONTINUE_TO_GO_TO_NEXT_STEP);
        } else {
            /* We need to return a view with fixed size because
            otherwise the stepper will keep expanding forever.
            This is a bug in the library.
            */
            tv.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
        }
        return tv;
    }

    private TextView createProposalSelectedStep() {
        TextView tv = new TextView(getContext());
        if (Constants.currentUser.isProposalSelected()) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
            tv.setText(Constants.PRESS_CONTINUE_TO_GO_TO_NEXT_STEP);
        } else {
            /* We need to return a view with fixed size because
            otherwise the stepper will keep expanding forever.
            This is a bug in the library.
            */
            tv.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
        }
        return tv;
    }

    private TextView createInstallationStep() {
        TextView tv = new TextView(getContext());
        if (Constants.currentUser.isInstallationDone()) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
            tv.setText(Constants.PRESS_CONTINUE_TO_GO_TO_NEXT_STEP);
        } else {
            /* We need to return a view with fixed size because
            otherwise the stepper will keep expanding forever.
            This is a bug in the library.
            */
            tv.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
        }
        return tv;
    }

    private TextView createPaymentStep() {
        TextView tv = new TextView(getContext());
        if (Constants.currentUser.isPremiumUser()) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
            tv.setText(Constants.PRESS_CONTINUE_TO_GO_TO_NEXT_STEP);
        } else {
            /* We need to return a view with fixed size because
            otherwise the stepper will keep expanding forever.
            This is a bug in the library.
            */
            tv.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
        }
        return tv;
    }

    private TextView createTrainerVisitStep() {
        TextView tv = new TextView(getContext());
        if (OrderActivity.hasTrainerVisited) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
            tv.setText(Constants.PRESS_CONTINUE_TO_GO_TO_NEXT_STEP);
        } else {
            /* We need to return a view with fixed size because
            otherwise the stepper will keep expanding forever.
            This is a bug in the library.
            */
            tv.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
        }
        return tv;
    }

    private void goToLastCompletedStep() {
        /*
        This is a workaround for the library limitation of not being able
        to go the last completed step.
         */
        if (Constants.currentUser.isVisitDone()) {
            completedStep = 0;
        }
        if (Constants.currentUser.isProposalSelected()) {
            completedStep = 1;
        }
        if (Constants.currentUser.isPremiumUser()) {
            completedStep = 2;
        }
        if (Constants.currentUser.isInstallationDone()) {
            completedStep = 3;
        }
        if (Constants.currentUser.isTrainerVisitComplete()) {
            completedStep = 4;
        }
        for (int i = 0; i <= completedStep; i++) {
            verticalStepperForm.goToNextStep();
        }
    }
}
