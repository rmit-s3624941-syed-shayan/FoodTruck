package com.tutelab.haseebpaul.mada1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Locale;

import Adapters.TrackableAdapter;
import Models.TrackableDataModel;
import Services.TrackableService;

public class trackableListActivity extends AppCompatActivity {

    EditText searchBar;
    RecyclerView list;
    ArrayList<TrackableDataModel> data,search;
    TrackableAdapter adapter;
    TrackableService ts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackable_list);

        Intent intent = getIntent();
        searchBar = findViewById(R.id.filter_search);
        list = findViewById(R.id.trackable_recycler);

        ts = new TrackableService(this);
        data = ts.parseFile(this);
        search = new ArrayList<>();


        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = searchBar.getText().toString().toLowerCase(Locale.getDefault());
                if(!search.isEmpty()){
                    filter(text);
                }
            }
        });

        if(!search.isEmpty()){
            search.clear();
        }

        search.addAll(data);

        adapter = new TrackableAdapter(data,this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(adapter);
    }

    private void filter(String text) {

        data.clear();
        if(text.length() == 0){
            data.addAll(search);
        }
        else{
            for(TrackableDataModel md : search){
                if(md.getCategory().toLowerCase(Locale.getDefault()).contains(text)){
                    data.add(md);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }
}
