package com.nirmalya.enactus.nirmalya.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.nirmalya.enactus.nirmalya.R;
import com.nirmalya.enactus.nirmalya.adapters.KnowOurTeamAdapter;
import com.nirmalya.enactus.nirmalya.utilities.Constants;

public class KnowOurTeam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_our_team);

        //Working With The Recycler View
        RecyclerView rv = findViewById(R.id.know_our_team_recycler_view);
        KnowOurTeamAdapter TeamDetailAdapter = new KnowOurTeamAdapter(this, Constants.getTeamMemberDetails(), Constants.getTeamImages(this));
        rv.setHasFixedSize(true);//
        rv.setAdapter(TeamDetailAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        //Adding Back Button and Title to the Toolbar
        Toolbar mActionBarToolbar;
        mActionBarToolbar = findViewById(R.id.know_our_team_activity_toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Our Team");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
