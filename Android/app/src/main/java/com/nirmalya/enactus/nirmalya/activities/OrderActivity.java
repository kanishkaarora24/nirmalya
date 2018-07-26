package com.nirmalya.enactus.nirmalya.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nirmalya.enactus.nirmalya.R;
import com.nirmalya.enactus.nirmalya.adapters.OrdersFragmentAdapter;
import com.nirmalya.enactus.nirmalya.model.User;
import com.nirmalya.enactus.nirmalya.utilities.Constants;

public class OrderActivity extends AppCompatActivity {

    private static final String LOG_TAG = OrderActivity.class.getSimpleName();

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseUser mFirebaseUser;

    private ViewPager viewPager;
    private OrdersFragmentAdapter adapterViewPager;
    private TabLayout ordersTabs;

    public static String accountType;
    public static String address;
    public static boolean isVisitDone;
    public static boolean isProposalSelected;
    public static boolean isOrderManufactured;
    public static boolean hasTrainerVisited;
    public static boolean isVisitRequested;
    public static boolean isUserPremium;
    public static boolean isInstallationDone;

    public static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        // Don't let the activity proceed further if the user is signed out
        if (mFirebaseUser == null) { finish(); }

        Toolbar toolbar = findViewById(R.id.orders_activity_toolbar);
        viewPager = findViewById(R.id.order_activity_view_pager);
        ordersTabs = findViewById(R.id.orders_activity_tabs);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Orders");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapterViewPager = new OrdersFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        ordersTabs.setupWithViewPager(viewPager);

        viewPager.setOffscreenPageLimit(4);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Constants.currentUser.isMeetingRequested() && Constants.currentUser.isVisitDone()) {
            adapterViewPager.removeRequestVisitFragment();
        }
    }
}
