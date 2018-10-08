package Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tutelab.haseebpaul.mada1.R;
import com.tutelab.haseebpaul.mada1.trackingDetailsActivity;

import java.util.ArrayList;

import Models.TrackableDataModel;

public class TrackableAdapter extends RecyclerView.Adapter<TrackableAdapter.MyViewHolder> {

    ArrayList<TrackableDataModel> data;
    Context ctx;

    public TrackableAdapter(ArrayList<TrackableDataModel> data, Context ctx) {
        this.data = data;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trackable_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TrackableDataModel obj = data.get(position);

        holder.title.setText(obj.getTitle());
        holder.description.setText(obj.getDescription());
        holder.link.setText(obj.getLink());
        holder.category.setText(obj.getCategory());

        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(ctx, obj.getLink(), Toast.LENGTH_SHORT).show();
                System.out.println("URL: " + obj.getLink());
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(obj.getLink()));

                try {
                    ctx.startActivity(browserIntent);
                }
                catch (Exception e){
                    Toast.makeText(ctx, "URL Badly Formatted", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.trackableObj.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                Intent detailIntent = new Intent(ctx, trackingDetailsActivity.class);
                detailIntent.putExtra("trackableId", obj.getId());
                ctx.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title,description,link,category;
        LinearLayout trackableObj;

        public MyViewHolder(View itemView) {
            super(itemView);

            trackableObj = itemView.findViewById(R.id.trackableObj);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            link = itemView.findViewById(R.id.link);
            category = itemView.findViewById(R.id.category);
        }
    }
}
