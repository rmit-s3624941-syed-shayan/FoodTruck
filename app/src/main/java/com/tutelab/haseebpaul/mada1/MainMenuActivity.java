package com.tutelab.haseebpaul.mada1;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import Scheduling.NetworkSchedulerService;
import Services.SuggestionService;
import helpers.DatabaseHelper;

public class MainMenuActivity extends AppCompatActivity {

    Button showTrackables, addTracking, showMyTrackings, showMapBtn, suggestNowBtn;
    private DatabaseHelper db;
    FloatingActionButton settingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        showTrackables = findViewById(R.id.showTrackables);
        addTracking = findViewById(R.id.addTracking);
        showMyTrackings = findViewById(R.id.mytrackings);
        showMapBtn = findViewById(R.id.showMapBtn);
        settingBtn = findViewById(R.id.fab);
        suggestNowBtn = findViewById(R.id.SuggestNow);


        //scheduleJob(this);
        //updateTrackablePos(MainMenuActivity.this);

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
                mapIntent.putExtra("trackableId", 0);
                startActivity(mapIntent);
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(MainMenuActivity.this);
                System.out.println("SFREQ::: " + sharedPrefs.getString("suggestionFrequency", "30"));
                Intent settingsIntent = new Intent(MainMenuActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });

        suggestNowBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateTrackablePos(MainMenuActivity.this);
            }
        });


    }

    @Override
    protected void onStop() {
        // A service can be "started" and/or "bound". In this case, it's "started" by this Activity
        // and "bound" to the JobScheduler (also called "Scheduled" by the JobScheduler). This call
        // to stopService() won't prevent scheduled jobs to be processed. However, failing
        // to call stopService() would keep it alive indefinitely.
        //stopService(new Intent(this, NetworkSchedulerService.class));
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        scheduleJob(getApplicationContext());
        //SQLiteDatabase db_new = new DatabaseHelper(this).getWritableDatabase();
        // Start service and provide it a way to communicate with this class.
        /*Intent startServiceIntent = new Intent(this, NetworkSchedulerService.class);
        startService(startServiceIntent);*/
    }

    private void updateTrackablePos(final Context ctx){
        System.out.println("In updateTrackablePos");


                System.out.println("---------------------------" );
        new Thread(){
            public void run(){
                SuggestionService s = new SuggestionService(ctx);

                System.out.println("---------------------------");

            }
        }.start();
    }

    private void scheduleJob(final Context ctx) {

        new Thread(){
            public void run(){

                JobInfo myJob;
                myJob = new JobInfo.Builder(2, new ComponentName(ctx, NetworkSchedulerService.class))
                        .setRequiresCharging(true)
                        .setMinimumLatency(1000)
                        .setOverrideDeadline(3000)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .setPersisted(true)
                        .build();

                JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
                if (jobScheduler != null) {
                    jobScheduler.schedule(myJob);
                }

            }
        }.start();

    }
}
