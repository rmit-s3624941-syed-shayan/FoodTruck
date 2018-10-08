package com.tutelab.haseebpaul.mada1;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

import Adapters.AddTrackingAdapter;
import Models.SelectedTrackingModel;
import Services.AddTrackingService;

public class AddTrackingActivity extends AppCompatActivity {

    RecyclerView atRecycler;
    ArrayList<SelectedTrackingModel> searchTrackingList, displayList;
    AddTrackingAdapter atAdapter;
    AddTrackingService atService;

    Button AccessTime;
    TextView DisplayTime;
    String format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tracking);


        AccessTime = (Button)findViewById(R.id.searchHour);
        DisplayTime = (TextView)findViewById(R.id.selectedMeetupTime);
        AccessTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                showHourPicker();

            }
        });

        Intent intent = getIntent();
        atService = new AddTrackingService(this);
        displayList = new ArrayList<>();
        searchTrackingList = new ArrayList<>();
        displayList = atService.process();
        searchTrackingList.addAll(displayList);

        atAdapter = new AddTrackingAdapter(displayList, this, DisplayTime);
        atRecycler = findViewById(R.id.add_list_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        atRecycler.setLayoutManager(layoutManager);
        atRecycler.setItemAnimator(new DefaultItemAnimator());
        atRecycler.setAdapter(atAdapter);
    }

    public void updateList() {
        displayList = atService.process();
        atAdapter = new AddTrackingAdapter(displayList, this, DisplayTime);
        atRecycler.setAdapter(atAdapter);

    }

    public void showHourPicker() {
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);


        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalender.set(Calendar.MINUTE, minute);

                }
                if (hourOfDay == 0) {
                    hourOfDay += 12;
                    format = "AM";
                }
                else if (hourOfDay == 12) {
                    format = "PM";
                }
                else if (hourOfDay > 12) {
                    hourOfDay -= 12;
                    format = "PM";
                }
                else {
                    format = "AM";
                }

                DisplayTime.setText(hourOfDay + ":" + minute + format);
                atService.updateTrackingService(AddTrackingActivity.this, hourOfDay, minute, format);
                updateList();

            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
        timePickerDialog.setTitle("Choose hour:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }
}
