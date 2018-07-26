package com.nirmalya.enactus.nirmalya.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nirmalya.enactus.nirmalya.R;
import com.nirmalya.enactus.nirmalya.adapters.QueryImagesAdapter;

import java.io.File;
import java.util.List;

public class QueryFragment extends Fragment {

    private static final String LOG_TAG = QueryFragment.class.getSimpleName();

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;

    private TextView titleTextView;
    private TextView subtitleTextView;
    private EditText queryMessageEditText;
    private Button selectImagesButton;
    private RecyclerView queryImagesRecyclerView;
    private Button sendQueryButton;
    private ProgressBar progressBar;
    private QueryImagesAdapter queryImagesAdapter;
    private List<Image> images;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.query_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReference();

        titleTextView = view.findViewById(R.id.query_fragment_title_textview);
        subtitleTextView = view.findViewById(R.id.query_fragment_subtitle);
        queryMessageEditText = view.findViewById(R.id.query_message_edit_text);
        selectImagesButton = view.findViewById(R.id.pick_image_button);
        queryImagesRecyclerView = view.findViewById(R.id.image_picker_recycler_view);
        sendQueryButton = view.findViewById(R.id.send_query_button);
        progressBar = view.findViewById(R.id.query_progress_bar);

        queryImagesRecyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.INVISIBLE);

        selectImagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker
                        .create(QueryFragment.this)
                        .limit(5)
                        .start();
            }
        });

        sendQueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = queryMessageEditText.getText().toString();
                if (message.length() != 0) {
                    progressBar.setVisibility(View.VISIBLE);
                    sendQuery(message);
                } else {
                    queryMessageEditText.setError("Message can not be empty.");
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            images = ImagePicker.getImages(data);
            queryImagesRecyclerView.setVisibility(View.VISIBLE);
            queryImagesAdapter = new QueryImagesAdapter(getContext(), images);
            queryImagesRecyclerView.setAdapter(queryImagesAdapter);
            queryImagesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void sendQuery(String message) {
        /*
        I know this is a terrible implementation of this function.
        I'm sorry, but I'm out of time to fix it.
         */
        DatabaseReference queryReference = mDatabaseReference.getRoot().child("queries").child(mFirebaseUser.getUid()).push();
        final DatabaseReference imageLinksReference = queryReference.child("images");
        queryReference.child("message").setValue(message);
        queryReference.child("isResolved").setValue(false);
        /*
        Checking if images is not null, because if the user has not attached any images
        then we would get a null pointer exception trying to access them.
         */
        if (images != null) {
            for (int i = 0; i < images.size(); i++) {
                final Image image = images.get(i);
                progressBar.setVisibility(View.VISIBLE);
                final File file = new File(image.getPath());
                final StorageReference imagesReference = mStorageReference.child("queries").child(mFirebaseUser.getUid()).child(image.getName());
                UploadTask uploadTask = imagesReference.putFile(Uri.fromFile(file));

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        return imagesReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        Uri downloadUri = task.getResult();
                        imageLinksReference.push().setValue(downloadUri.toString());
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }
        /*
        :'(
         */
        Toast.makeText(getContext(), "Uploading Query. We will get back to you soon.", Toast.LENGTH_LONG).show();
        if (images == null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
