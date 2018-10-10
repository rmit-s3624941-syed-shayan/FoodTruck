package com.tutelab.haseebpaul.mada1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Models.SelectedTrackingModel;
import Models.TrackableDataModel;
import Storage.MyTrackingStorage;

public class EditTrackingActivity extends AppCompatActivity {

    EditText editTitle, editCategory;
    TextView editTime, editStoptime;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tracking);

        editTitle = findViewById(R.id.editTitle);
        editCategory = findViewById(R.id.editCategory);
        editTime = findViewById(R.id.editTime);
        editStoptime = findViewById(R.id.editStoptime);
        saveBtn = findViewById(R.id.editsaveBtn);

        Intent intent = getIntent();
        int trackingId = Integer.parseInt(intent.getExtras().get("trackingId").toString());
        final SelectedTrackingModel stm = MyTrackingStorage.getTracking(this, trackingId);

        editTitle.setText(stm.getTrackable().getTitle());
        editCategory.setText(stm.getTrackable().getCategory());
        editTime.setText(stm.getTrackingDetail().getDate().toString());
        editStoptime.setText(String.valueOf (stm.getTrackingDetail().getStopTime()));



        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                TrackableDataModel tdm = new TrackableDataModel(stm.getTrackable().getId(),editTitle.getText().toString()
                        ,stm.getTrackable().getDescription(),stm.getTrackable().getLink(), editCategory.getText().toString());
                SelectedTrackingModel editedTracking = new SelectedTrackingModel(tdm,stm.getTrackingDetail(),stm.getSelectionId());

                MyTrackingStorage.editTracking(EditTrackingActivity.this,editedTracking);
                Intent trackableIntent = new Intent(EditTrackingActivity.this, MainMenuActivity.class);
                startActivity(trackableIntent);
            }
        });


    }
}
