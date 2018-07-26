package com.nirmalya.enactus.nirmalya.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.nirmalya.enactus.nirmalya.R;
import com.nirmalya.enactus.nirmalya.adapters.TrainerVisitViewHolder;
import com.nirmalya.enactus.nirmalya.model.TrainerVisit;

public class TrainerActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<TrainerVisit, TrainerVisitViewHolder> visitsAdapter;

    private RecyclerView trainerVisitsRecyclerView;
    private Toolbar trainerActivityToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabaseReference = mFirebaseDatabase.getReference();

        trainerVisitsRecyclerView = findViewById(R.id.trainer_visits_recycler_view);
        trainerActivityToolbar = findViewById(R.id.trainer_activity_toolbar);
        setSupportActionBar(trainerActivityToolbar);
        getSupportActionBar().setTitle("Your Visits");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Query trainerVisitsQuery = mDatabaseReference.getRoot().child("trainer_visits").child(mFirebaseUser.getUid());

        FirebaseRecyclerOptions<TrainerVisit> options = new FirebaseRecyclerOptions.Builder<TrainerVisit>()
                .setQuery(trainerVisitsQuery, TrainerVisit.class)
                .build();

        visitsAdapter = new FirebaseRecyclerAdapter<TrainerVisit, TrainerVisitViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final TrainerVisitViewHolder holder, int position, @NonNull final TrainerVisit model) {

                holder.visitNameTextView.setText(model.visitName);
                holder.customerNameTextView.setText(model.customerName);
                holder.customerContactTextView.setText(model.customerContact);
                holder.customerAddressTextView.setText(model.customerAddress);
                holder.visitTimeTextView.setText(model.visitTime);
                holder.visitDateTextView.setText(model.visitDate);

                if (model.isAccepted) {
                    holder.acceptVisitButton.setText("Accepted");
                    holder.acceptVisitButton.setEnabled(false);
                } else {
                    holder.acceptVisitButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDatabaseReference.getRoot()
                                    .child("trainer_visits")
                                    .child(mFirebaseUser.getUid())
                                    .child(model.visitName)
                                    .child("isAccepted")
                                    .setValue(true);
                            holder.acceptVisitButton.setEnabled(false);
                            holder.acceptVisitButton.setText("Accepted");
                        }
                    });
                }
            }

            @NonNull
            @Override
            public TrainerVisitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new TrainerVisitViewHolder(LayoutInflater.from(TrainerActivity.this).inflate(R.layout.trainer_visit_card, parent, false));
            }
        };

        trainerVisitsRecyclerView.setAdapter(visitsAdapter);
        trainerVisitsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        visitsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        visitsAdapter.stopListening();
    }
}
