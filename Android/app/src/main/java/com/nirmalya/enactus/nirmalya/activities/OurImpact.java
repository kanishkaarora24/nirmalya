package com.nirmalya.enactus.nirmalya.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nirmalya.enactus.nirmalya.R;

public class OurImpact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_impact);

        Toolbar toolbar = findViewById(R.id.our_impact_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Our Impact");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
