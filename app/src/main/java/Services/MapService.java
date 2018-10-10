package Services;


import android.content.Context;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import Models.TrackingDetailModel;

public class MapService {


    private static Context context;
    private ArrayList<TrackingDetailModel> trackings;

    public MapService(Context ctx) {
        this.context = ctx;

        this.trackings = new ArrayList<>();

        TrackingService trackingSvc = TrackingService.getSingletonInstance(ctx);
        this.trackings = trackingSvc.getAllTrackings();
    }

    public void plotAllPoints(GoogleMap mMap)
    {
        for (TrackingDetailModel td: trackings)
        {
            String title = "Trackable id: " + Integer.toString(td.trackableId) + " at " + td.getDate().toString();
            LatLng point = new LatLng(td.getLatitude(),td.getLongitude());//new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions().position(point).title(title));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
        }

        setCamera(mMap);
    }

    public void plotPointsforId(GoogleMap mMap, int selectedTrackableId)
    {
        for (TrackingDetailModel td: trackings)
        {
            if(td.getTrackableId() == selectedTrackableId) {
                String title = "Trackable id: " + Integer.toString(td.trackableId) + " at " + td.getDate().toString();
                LatLng point = new LatLng(td.getLatitude(), td.getLongitude());//new LatLng(-34, 151);
                mMap.addMarker(new MarkerOptions().position(point).title(title));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
            }
        }

        setCamera(mMap);
    }

    private void setCamera(GoogleMap mMap)
    {

        LatLngBounds AUSTRALIA = new LatLngBounds(
                new LatLng(-37.823642, 144.945832), new LatLng(-37.791524, 144.978783));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AUSTRALIA.getCenter(), 13));
    }
}
