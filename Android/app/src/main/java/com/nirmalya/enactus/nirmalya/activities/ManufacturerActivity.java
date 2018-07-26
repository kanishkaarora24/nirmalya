package com.nirmalya.enactus.nirmalya.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crashlytics.android.Crashlytics;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.nirmalya.enactus.nirmalya.R;
import com.nirmalya.enactus.nirmalya.adapters.ManufacturerOrderViewHolder;
import com.nirmalya.enactus.nirmalya.model.ManufacturerOrder;

public class ManufacturerActivity extends AppCompatActivity {

    private static final String LOG_TAG = ManufacturerActivity.class.getSimpleName();

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    FirebaseRecyclerAdapter<ManufacturerOrder, ManufacturerOrderViewHolder> ordersAdapter;

    private Toolbar manufacturerActivityToolbar;
    private RecyclerView ordersRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manufacturer);

        manufacturerActivityToolbar = findViewById(R.id.manufacturer_activity_toolbar);
        setSupportActionBar(manufacturerActivityToolbar);
        getSupportActionBar().setTitle("Your Orders");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        ordersRecyclerView = findViewById(R.id.orders_recycler_view);

        final Query manufacturerOrdersQuery = mDatabaseReference.getRoot().child("manufacturer_orders").child(mFirebaseUser.getUid());

        FirebaseRecyclerOptions<ManufacturerOrder> options = new FirebaseRecyclerOptions.Builder<ManufacturerOrder>()
                .setQuery(manufacturerOrdersQuery, ManufacturerOrder.class)
                .build();

        ordersAdapter = new FirebaseRecyclerAdapter<ManufacturerOrder, ManufacturerOrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ManufacturerOrderViewHolder holder, int position, @NonNull final ManufacturerOrder model) {
                Crashlytics.log(0,  LOG_TAG, model.toString());
                holder.numberOfPitsTextView.setText("Pits: " + model.getNumberOfPits());
                holder.numberOfMeshesTextView.setText("Meshes: " + model.getNumberOfMeshes());
                holder.numberOfHomeCompostersTextView.setText("Home Composters:" + model.getNumberOfHomeComposters());
                holder.customerNameTextView.setText("Customer Name: " + model.getCustomerName());
                holder.customerAddressTextView.setText("Customer Address: " + model.getCustomerAddress());
                holder.customerPhoneNumberTextView.setText("Customer Contact: " + model.getCustomerContact());
                holder.orderNameTextView.setText(model.getOrderName());
                if (model.getEstimatedDate().equals("none")) {
                    holder.acceptOrderButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (holder.estimatedDateEditText.getText().toString().isEmpty()) {
                                holder.estimatedDateEditText.setError("Can not be empty");
                            } else {
                                String dateEstimate = holder.estimatedDateEditText.getText().toString();
                                model.setEstimatedDate(dateEstimate);
                                mDatabaseReference.getRoot().child("manufacturer_orders").child(mFirebaseUser.getUid()).child(model.getOrderName()).child("estimatedDate").setValue(dateEstimate);
                                notifyItemChanged(holder.getAdapterPosition());
                            }
                        }
                    });
                } else {
                    holder.estimatedDateEditText.setText(model.getEstimatedDate());
                    holder.acceptOrderButton.setEnabled(false);
                    holder.acceptOrderButton.setText("Order Accepted");
                    holder.estimatedDateEditText.setEnabled(false);
                }
            }

            @NonNull
            @Override
            public ManufacturerOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ManufacturerOrderViewHolder(LayoutInflater.from(ManufacturerActivity.this).inflate(R.layout.manufacturer_order_card, parent, false));
            }
        };

        ordersRecyclerView.setAdapter(ordersAdapter);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        ordersAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ordersAdapter.stopListening();
    }
}
