package com.tutelab.haseebpaul.mada1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import Adapters.TrackingDetailAdapter;
import Models.TrackingDetailModel;
import Services.TrackingService;

public class trackingDetailsActivity extends AppCompatActivity {

    RecyclerView tdrecycler;
    ArrayList<TrackingDetailModel> detailsList;
    TrackingDetailAdapter tdAdapter;
    TrackingService ts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_details);

        Intent intent = getIntent();
        int trackableId = Integer.parseInt(intent.getExtras().get("trackableId").toString());
        ts = TrackingService.getSingletonInstance(this);
        detailsList = new ArrayList<>();
        detailsList.clear();

        detailsList = ts.getTrackingInfoForId(trackableId);
//        detailsList.add(new TrackingDetailModel(new Date("05/07/2018 1:00:00 PM"), 2, 10,  -37.814644, 144.955412));
//        detailsList.add(new TrackingDetailModel(new Date("05/07/2018 1:00:00 PM"), 2, 5,  -37.814644, 144.955412));

        tdAdapter = new TrackingDetailAdapter(detailsList, this);
        tdrecycler = findViewById(R.id.trackabledetail_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        tdrecycler.setLayoutManager(layoutManager);
        tdrecycler.setItemAnimator(new DefaultItemAnimator());
        tdrecycler.setAdapter(tdAdapter);
    }
}
