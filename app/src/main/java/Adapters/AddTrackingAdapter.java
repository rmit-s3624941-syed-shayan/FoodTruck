package Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tutelab.haseebpaul.mada1.MainMenuActivity;
import com.tutelab.haseebpaul.mada1.R;

import java.util.ArrayList;

import Models.SelectedTrackingModel;
import Storage.MyTrackingStorage;
import helpers.DatabaseHelper;

public class AddTrackingAdapter extends RecyclerView.Adapter<AddTrackingAdapter.STViewHolder> {

    ArrayList<SelectedTrackingModel> allTrackings;
    Context ctx;
    TextView displayTime;

    public AddTrackingAdapter(ArrayList<SelectedTrackingModel> allTrackings, Context ctx, TextView displayTime) {
        this.allTrackings = allTrackings;
        this.ctx = ctx;
        this.displayTime = displayTime;
    }

    @NonNull
    @Override
    public STViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.selecttracking_list, parent, false);
        return new STViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull STViewHolder holder, int position) {
        final SelectedTrackingModel obj = allTrackings.get(position);

        holder.title.setText(obj.getTrackable().getTitle());
        holder.category.setText(obj.getTrackable().getCategory());
        holder.time.setText(obj.getTrackingDetail().getDate().toString());
        holder.stoptime.setText("Stoptime: "+String.valueOf(obj.getTrackingDetail().getStopTime()));
        holder.latitude.setText(Double.toString(obj.getTrackingDetail().getLatitude()));
        holder.longitude.setText(Double.toString(obj.getTrackingDetail().getLongitude()));

        holder.selecttrackingObj.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                if(displayTime.getText().toString().equalsIgnoreCase("No Time Selected"))
                {
                    Toast.makeText(ctx, "Select Meetup Time", Toast.LENGTH_SHORT).show();
                }
                else {

                    if (obj.getTrackingDetail().getStopTime() > 0) {
                        MyTrackingStorage mts = new MyTrackingStorage();
                        obj.setMeetupTime(displayTime.getText().toString());
                        mts.addTracking(ctx, obj);
                        DatabaseHelper.Addmytracking(obj);
                        Toast.makeText(ctx, "Tracking Added to List", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ctx, MainMenuActivity.class);
                        ctx.startActivity(intent);
                    } else {
                        Toast.makeText(ctx, "Trackable is moving!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return  allTrackings.size();
    }

    public class STViewHolder extends RecyclerView.ViewHolder {

        TextView title,time, stoptime, latitude, longitude,category;
        LinearLayout selecttrackingObj;

        public STViewHolder(View itemView) {
            super(itemView);

            selecttrackingObj = itemView.findViewById(R.id.selecttrackingObj);
            title = itemView.findViewById(R.id.addtrackableTitle);
            time = itemView.findViewById(R.id.addtrackabletime);
            stoptime = itemView.findViewById(R.id.addtrackablestoptime);
            latitude = itemView.findViewById(R.id.addtrackablelatitude);
            longitude = itemView.findViewById(R.id.addtrackablelongitude);
            category = itemView.findViewById(R.id.addtrackablecategory);

        }
    }
}
