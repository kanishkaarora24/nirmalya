package com.nirmalya.enactus.nirmalya.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.nirmalya.enactus.nirmalya.R;
import com.nirmalya.enactus.nirmalya.adapters.TasksViewHolder;
import com.nirmalya.enactus.nirmalya.model.Task;
import com.nirmalya.enactus.nirmalya.utilities.Constants;

public class TasksFragment extends Fragment {

    private static final String LOG_TAG = TasksFragment.class.getSimpleName();

    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<Task, TasksViewHolder> tasksAdapter;

    private RecyclerView tasksRecyclerView;
    private ProgressBar progressBar;
    private TextView noTasksTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tasks_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

        tasksRecyclerView = view.findViewById(R.id.tasks_recycler_view);
        progressBar = view.findViewById(R.id.tasks_progress_bar);
        noTasksTextView = view.findViewById(R.id.no_tasks_textview);
        progressBar.setVisibility(View.GONE);

        if (Constants.currentUser.isProposalSelected() && Constants.currentUser.isPremiumUser()) {

            noTasksTextView.setVisibility(View.GONE);
            tasksRecyclerView.setVisibility(View.VISIBLE);

            Query tasksQuery = mDatabaseReference.getRoot().child("tasks").child(mFirebaseUser.getUid());

            FirebaseRecyclerOptions<Task> options = new FirebaseRecyclerOptions.Builder<Task>()
                    .setQuery(tasksQuery, Task.class)
                    .build();

            tasksAdapter = new FirebaseRecyclerAdapter<Task, TasksViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull final TasksViewHolder holder, final int position, @NonNull final Task model) {
                    holder.taskTextView.setText(model.getTaskText());

                    if (model.getIsComplete()) {
                        setCardGreen(holder);
                    } else {
                        setCardWhite(holder);
                    }

                    holder.taskCompleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressBar.setVisibility(View.VISIBLE);
                            boolean newStatus = !model.getIsComplete();
                            if (!newStatus) {
                                mDatabaseReference.getRoot()
                                        .child("tasks")
                                        .child(mFirebaseUser.getUid())
                                        .child(model.getTaskName())
                                        .child("isComplete")
                                        .setValue(newStatus)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    progressBar.setVisibility(View.GONE);
                                                    setCardGreen(holder);
                                                }
                                            }
                                        });

                            } else {
                                mDatabaseReference.getRoot()
                                        .child("tasks")
                                        .child(mFirebaseUser.getUid())
                                        .child(model.getTaskName())
                                        .child("isComplete")
                                        .setValue(newStatus)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    progressBar.setVisibility(View.GONE);
                                                    setCardWhite(holder);
                                                }
                                            }
                                        });
                            }
                        }
                    });
                }

                @NonNull
                @Override
                public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    return new TasksViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_card, parent, false));
                }
            };
            tasksRecyclerView.setAdapter(tasksAdapter);
            tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            noTasksTextView.setVisibility(View.VISIBLE);
            tasksRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (tasksAdapter != null) {
            tasksAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (tasksAdapter != null) {
            tasksAdapter.stopListening();
        }
    }

    private void setCardGreen(TasksViewHolder holder) {
        holder.taskCard.setBackgroundColor(getResources().getColor(R.color.primaryColor));
        holder.taskCompleteButton.setText("Completed");
        holder.taskCompleteButton.setTextColor(getResources().getColor(android.R.color.white));
        holder.taskTextView.setTextColor(getResources().getColor(android.R.color.white));
    }

    private void setCardWhite(TasksViewHolder holder) {
        holder.taskCard.setBackgroundColor(getResources().getColor(android.R.color.white));
        holder.taskCompleteButton.setText("Mark complete");
        holder.taskCompleteButton.setTextColor(getResources().getColor(android.R.color.black));
        holder.taskTextView.setTextColor(getResources().getColor(android.R.color.black));
    }
}
