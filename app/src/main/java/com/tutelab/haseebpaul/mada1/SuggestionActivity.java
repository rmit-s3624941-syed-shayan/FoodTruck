package com.tutelab.haseebpaul.mada1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Models.SuggestedTrackingModel;
import Services.SuggestionService;
import Services.TrackableService;

public class SuggestionActivity extends AppCompatActivity {

    TextView truckId, stopTime, duration, sTrkblemtp;
    Button CnclBtn, NextBtn, AddBtn;
    int ind;
    ArrayList<SuggestedTrackingModel> mySuggestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        ind = 0;
        truckId = findViewById(R.id.sTrkbleId);
        duration = findViewById(R.id.sTrkbleDur);
        stopTime = findViewById(R.id.sTrkbleStp);
        NextBtn = findViewById(R.id.NxtBtn);
        AddBtn = findViewById(R.id.AddBtn);
        CnclBtn = findViewById(R.id.CnclBtn);
        sTrkblemtp = findViewById(R.id.sTrkblemtp);
        mySuggestions = new ArrayList<>();
        mySuggestions = SuggestionService.getSuggestionList();

        try {
            if (mySuggestions.get(0) != null)
                setViewItems(mySuggestions.get(0));
            else
                setViewItems();
        }
        catch (NullPointerException e)
        {
            setViewItems();
        }
        CnclBtn.setOnClickListener((View v) -> {
            ind = 0;

            gotoMain();
            finish();
        });
        AddBtn.setOnClickListener((View v) -> {
            SuggestionService.AddTracking(mySuggestions.get(ind));
            ind = 0;
            gotoMain();
        });

            NextBtn.setOnClickListener((View v) -> {
            // Code here executes on main thread after user presses button
            try {

                ind++;
                SuggestedTrackingModel stm = mySuggestions.get(ind);
                    setViewItems(stm);

            }
            catch (Exception e)
            {
                setViewItems();
            }

        });
    }

    public void gotoMain()
    {
        Intent mainIntent = new Intent(SuggestionActivity.this, MainMenuActivity.class);
        startActivity(mainIntent);
    }

    public void setViewItems(SuggestedTrackingModel stm)
    {
        if (stm != null) {

            String tName = "Trackable ID: " + stm.gettDM().getTrackableIdAsString();
            String traveltime = "Travel Time: " + stm.getdNd().getDurationTxt();
            String traveldist = "Travel Distance: " + stm.getdNd().getDistanceTxt();

            Date d = stm.gettDM().getDate();
            SimpleDateFormat s = new SimpleDateFormat("hh:mm a");
            String meetupTime = "Meetup Time: "+s.format(d);


            truckId.setText(tName);
            sTrkblemtp.setText(meetupTime);
            duration.setText(traveltime);
            stopTime.setText(traveldist);
        }
        else
        {
            truckId.setText(R.string.no_sugg);
            duration.setText("");
            stopTime.setText("");
            sTrkblemtp.setText("");

            Toast.makeText(SuggestionActivity.this,R.string.no_sugg, Toast.LENGTH_SHORT);
        }

    }

    public void setViewItems()
    {
        truckId.setText(R.string.no_sugg);
        duration.setText("");
        stopTime.setText("");
        sTrkblemtp.setText("");

        Toast.makeText(SuggestionActivity.this,"No More Suggestions", Toast.LENGTH_SHORT);
    }
}
