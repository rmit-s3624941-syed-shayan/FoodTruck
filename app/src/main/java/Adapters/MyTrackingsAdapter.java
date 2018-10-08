package Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tutelab.haseebpaul.mada1.EditTrackingActivity;
import com.tutelab.haseebpaul.mada1.MyTrackingsActivity;
import com.tutelab.haseebpaul.mada1.R;

import java.util.ArrayList;
import java.util.Map;

import Models.SelectedTrackingModel;
import Storage.MyTrackingStorage;

public class MyTrackingsAdapter extends RecyclerView.Adapter<MyTrackingsAdapter.MyListViewHolder> {
    ArrayList<SelectedTrackingModel> allTrackings;
    Context ctx;

    public MyTrackingsAdapter(ArrayList<SelectedTrackingModel> allTrackings, Context ctx) {
        this.allTrackings = allTrackings;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_tracking_list, parent, false);
        return new MyListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyListViewHolder holder, int position) {
        final SelectedTrackingModel obj = allTrackings.get(position);

        holder.title.setText(obj.getTrackable().getTitle());
        holder.category.setText(obj.getTrackable().getCategory());
        holder.time.setText(obj.getTrackingDetail().getDate().toString());
        holder.stoptime.setText("Stoptime: "+String.valueOf(obj.getTrackingDetail().getStopTime()));
        holder.latitude.setText(Double.toString(obj.getTrackingDetail().getLatitude()));
        holder.longitude.setText(Double.toString(obj.getTrackingDetail().getLongitude()));
        holder.meetupTime.setText("Meetup Time: "+obj.getMeetupTime());

        holder.edittrackingObj.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {

                Intent detailIntent = new Intent(ctx, EditTrackingActivity.class);
                detailIntent.putExtra("trackingId", obj.getSelectionId());
                ctx.startActivity(detailIntent);
            }
        });

        holder.removeBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {

                MyTrackingStorage.removeTracking(ctx, obj);
                Intent trackableIntent = new Intent(ctx, MyTrackingsActivity.class);
                ctx.startActivity(trackableIntent);
                /*Intent detailIntent = new Intent(ctx, EditTrackingActivity.class);
                detailIntent.putExtra("trackingId", obj.getSelectionId());
                ctx.startActivity(detailIntent);*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return allTrackings.size();
    }

    public class MyListViewHolder extends RecyclerView.ViewHolder {

        TextView title,time, stoptime, latitude, longitude,category, meetupTime;
        LinearLayout edittrackingObj;
        Button removeBtn;

        public MyListViewHolder(View itemView) {
            super(itemView);

            edittrackingObj = itemView.findViewById(R.id.edittrackingObj);
            title = itemView.findViewById(R.id.edittrackableTitle);
            time = itemView.findViewById(R.id.edittrackabletime);
            stoptime = itemView.findViewById(R.id.edittrackablestoptime);
            latitude = itemView.findViewById(R.id.edittrackablelatitude);
            longitude = itemView.findViewById(R.id.edittrackablelongitude);
            category = itemView.findViewById(R.id.edittrackablecategory);
            removeBtn = itemView.findViewById(R.id.removebutton);
            meetupTime = itemView.findViewById(R.id.edittrackableMT);

        }
    }
}
