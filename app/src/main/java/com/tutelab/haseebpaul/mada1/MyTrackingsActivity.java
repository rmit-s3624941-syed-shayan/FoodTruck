package com.tutelab.haseebpaul.mada1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import Adapters.MyTrackingsAdapter;
import Models.SelectedTrackingModel;
import Storage.MyTrackingStorage;

public class MyTrackingsActivity extends AppCompatActivity {

    RecyclerView myRecycler;
    ArrayList<SelectedTrackingModel> displayList;
    MyTrackingsAdapter myAdapter;
    Button mainmenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trackings);

        Intent intent = getIntent();
        displayList = new ArrayList<>();
        displayList = MyTrackingStorage.getMyList();

        myAdapter = new MyTrackingsAdapter(displayList, this);
        myRecycler = findViewById(R.id.my_list_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        myRecycler.setLayoutManager(layoutManager);
        myRecycler.setItemAnimator(new DefaultItemAnimator());
        myRecycler.setAdapter(myAdapter);


        mainmenu = findViewById(R.id.mainmenuMT);
        mainmenu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {

                Intent trackableIntent = new Intent(MyTrackingsActivity.this, MainMenuActivity.class);
                startActivity(trackableIntent);

            }
        });
    }
}
