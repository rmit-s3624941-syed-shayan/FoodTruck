package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.LayoutInflater;

import com.tutelab.haseebpaul.mada1.R;

import java.util.ArrayList;

import Models.TrackingDetailModel;

public class TrackingDetailAdapter extends RecyclerView.Adapter<TrackingDetailAdapter.TDViewHolder>  {

    ArrayList<TrackingDetailModel> data;
    Context ctx;

    public TrackingDetailAdapter(ArrayList<TrackingDetailModel> data, Context ctx) {
        this.data = data;
        this.ctx = ctx;
    }


    @NonNull
    @Override
    public TrackingDetailAdapter.TDViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trackingdetail_list, parent, false);
        return new TDViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackingDetailAdapter.TDViewHolder holder, int position) {
        final TrackingDetailModel obj = data.get(position);

        holder.time.setText(obj.getDate().toString());
        holder.stoptime.setText(String.valueOf(obj.getStopTime()));
        holder.latitude.setText(Double.toString(obj.getLatitude()));
        holder.longitude.setText(Double.toString(obj.getLongitude()));


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TDViewHolder extends RecyclerView.ViewHolder {

        TextView time, stoptime, latitude, longitude;
        LinearLayout TdObj;

        public TDViewHolder(View itemView) {
            super(itemView);
            TdObj = itemView.findViewById(R.id.trackingdetailObj);

            time = itemView.findViewById(R.id.time);
            stoptime = itemView.findViewById(R.id.stoptime);
            latitude = itemView.findViewById(R.id.latitude);
            longitude = itemView.findViewById(R.id.longitude);

        }
    }
}
