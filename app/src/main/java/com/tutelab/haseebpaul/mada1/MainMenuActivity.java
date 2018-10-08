package com.tutelab.haseebpaul.mada1;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import helpers.DatabaseHelper;
public class MainMenuActivity extends AppCompatActivity {

    Button showTrackables, addTracking, showMyTrackings, showMapBtn;
    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        //db = new DatabaseHelper(this);
        SQLiteDatabase db_new = new DatabaseHelper(this).getWritableDatabase();
        showTrackables = findViewById(R.id.showTrackables);
        addTracking = findViewById(R.id.addTracking);
        showMyTrackings = findViewById(R.id.mytrackings);
        showMapBtn = findViewById(R.id.showMapBtn);

        showTrackables.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent trackableIntent = new Intent(MainMenuActivity.this, trackableListActivity.class);
                startActivity(trackableIntent);
            }
        });

        addTracking.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent trackableIntent = new Intent(MainMenuActivity.this, AddTrackingActivity.class);
                startActivity(trackableIntent);
            }
        });


        showMyTrackings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent trackableIntent = new Intent(MainMenuActivity.this, MyTrackingsActivity.class);
                startActivity(trackableIntent);
            }
        });


        showMapBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent mapIntent = new Intent(MainMenuActivity.this, MapsActivity.class);
                startActivity(mapIntent);
            }
        });
    }
}
