package com.nirmalya.enactus.nirmalya.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.nirmalya.enactus.nirmalya.R;
import com.nirmalya.enactus.nirmalya.activities.OrderActivity;
import com.nirmalya.enactus.nirmalya.adapters.ProposalViewHolder;
import com.nirmalya.enactus.nirmalya.model.Proposal;
import com.nirmalya.enactus.nirmalya.utilities.Constants;

public class ProposalsFragment extends Fragment {

    private static final String LOG_TAG = ProposalsFragment.class.getSimpleName();

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseRecyclerAdapter<Proposal, ProposalViewHolder> proposalsAdapter;

    private RecyclerView proposalsRecyclerView;
    private ProgressBar progressBar;
    private TextView noProposalsTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.proposals_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
        Get an instance of Firebase member variables after the fragment view
        has been created.
         */
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        proposalsRecyclerView = view.findViewById(R.id.proposals_recycler_view);
        progressBar = view.findViewById(R.id.proposals_progress_bar);
        noProposalsTextView = view.findViewById(R.id.no_proposals_textview);
        progressBar.setVisibility(View.INVISIBLE);


        if (Constants.currentUser.isMeetingRequested() && Constants.currentUser.isVisitDone()) {

            /*
            If the visit is complete, then hide the no proposals textview.
             */
            noProposalsTextView.setVisibility(View.GONE);
            proposalsRecyclerView.setVisibility(View.VISIBLE);

                /*
            Set up the query for getting proposals for
            this user from the database.
             */
                Query proposalsQuery = mDatabaseReference.getRoot().child("proposals").child(mFirebaseUser.getUid());

            /*
            Set up the Firebase Recycler Options with the
            query defined above and the model class to hold
            the results of that query.
             */
                FirebaseRecyclerOptions<Proposal> options = new FirebaseRecyclerOptions.Builder<Proposal>()
                        .setQuery(proposalsQuery, Proposal.class)
                        .build();

            /*
            Set up the adapter to be used for the FireabseRecyclerView.
             */
                proposalsAdapter = new FirebaseRecyclerAdapter<Proposal, ProposalViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull final ProposalViewHolder holder, int position, @NonNull final Proposal model) {

                        String numberOfMeshes = "Meshes: " + model.getNumberOfMeshes();
                        String numberOfPits = "Pits: " + model.getNumberOfPits();
                        String numberOfHomeComposters = "Home Composters: " + model.getNumberOfHomeComposters();
                        String tempUrl = model.getUrl();
                    /*
                    If the URL does not start with http:// or https://
                    then android doesn't recognize it is a weburl and throws
                    an exception.
                     */
                        if (!tempUrl.startsWith("http://") || !tempUrl.startsWith("https://")) {
                            tempUrl = "http://" + tempUrl;
                        }
                        final String url = tempUrl;

                        holder.proposalNameView.setText(model.getProposalName());
                        holder.numberOfMeshesView.setText(numberOfMeshes);
                        holder.numberOfPitsView.setText(numberOfPits);
                        holder.numberOfHomeCompostersView.setText(numberOfHomeComposters);
                        holder.selectedImageView.setVisibility(View.INVISIBLE);
                    /*
                    Take the user to the URL retrieved for downloading the query.
                     */
                        holder.downloadButton.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse(url));
                                        startActivity(intent);
                                    }
                                }
                        );
                    /*
                    Set a click listener to the select proposal button.
                     */
                        holder.selectProposalButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            /*
                            We don't know how long would it take to complete
                            this operation. Display a progress bar to make sure
                            that the user knows they have to wait for it to complete.
                             */
                                progressBar.setVisibility(View.VISIBLE);

                            /*
                            This block of code below unselects all the other proposals
                            when the user presses Select on one of them.
                            This code also involves writing changes to the database,
                            so it may run for a long time.
                             */
                                if (!OrderActivity.isUserPremium) {
                                /*
                                 Only allow the user to change proposals if the payment
                                 has not already been made.
                                  */
                                    for (int i = 0; i < proposalsAdapter.getItemCount(); i++) {
                                    /*
                                    For each item in the adapter, unselect them locally as well as
                                    on the database.
                                     */
                                        proposalsAdapter.getItem(i).setIsSelected(false);
                                    /*
                                    I have assumed that the name of the proposal
                                    and the name of the node in which it is stored would
                                    be the same always. Otherwise there is no way for me to find one
                                    proposal from the others.
                                     */
                                        String tempName = proposalsAdapter.getItem(i).getProposalName();
                                        mDatabaseReference.getRoot().child("proposals").child(mFirebaseUser.getUid()).child(tempName).child("isSelected").setValue(false);
                                    }
                                /*
                                Mark the item the user selected as true
                                and post the change to the database.
                                 */
                                    proposalsAdapter.getItem(holder.getAdapterPosition()).setIsSelected(true);
                                    progressBar.setVisibility(View.VISIBLE);
                                    mDatabaseReference.getRoot().child("proposals").child(mFirebaseUser.getUid()).child(model.getProposalName()).child("isSelected").setValue(true)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    if (task.isSuccessful()) {
                                                        Crashlytics.log(0,  LOG_TAG, "Proposal successfully updated to the database");
                                                    } else {
                                                        Crashlytics.log(0,  LOG_TAG, task.getException().toString());
                                                    }
                                                }
                                            });
                                    mDatabaseReference.getRoot().child("registered_users").child(mFirebaseUser.getUid()).child("proposalSelected").setValue(true);
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), "Changing proposal is not allowed after payment is made.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    /*
                    Disable the Select button if the current proposal is already selected.
                     */
                        if (model.getIsSelected()) {
                            holder.selectProposalButton.setEnabled(false);
                            holder.selectedImageView.setVisibility(View.VISIBLE);
                        } else {
                            holder.selectProposalButton.setEnabled(true);
                            holder.selectedImageView.setVisibility(View.INVISIBLE);
                        }
                    }

                    /*
                    Required method to create and return a view holder.
                    The view holder used here is defined in the adapters package.
                     */
                    @NonNull
                    @Override
                    public ProposalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        return new ProposalViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.proposal_item, parent, false));
                    }

                    /*
                    If there is an error in retrieving the proposals,
                    show a toast.
                     */
                    @Override
                    public void onError(@NonNull DatabaseError error) {
                        super.onError(error);
                        Toast.makeText(getContext(), "Error retrieving proposals", Toast.LENGTH_SHORT).show();
                    }
                };
                // Set up the recycler view
                proposalsRecyclerView.setAdapter(proposalsAdapter);
                proposalsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            proposalsRecyclerView.setVisibility(View.GONE);
            noProposalsTextView.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        if (proposalsAdapter != null) {
        /*
        Start listening for changes when the fragment
        is in the started state.
         */
            proposalsAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (proposalsAdapter != null) {
            /*
            Stop listening for changes when the fragment
            is stopped.
            */
            proposalsAdapter.stopListening();
        }
    }
}
