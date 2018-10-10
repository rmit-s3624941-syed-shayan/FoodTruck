package Services;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import Models.SelectedTrackingModel;
import Models.TrackableDataModel;
import Models.TrackingDetailModel;

public class AddTrackingService {
    private static Context context;

    private ArrayList<TrackableDataModel> trackables;
    private ArrayList<TrackingDetailModel> trackings;
    private ArrayList<SelectedTrackingModel> trackingList;


    public AddTrackingService(Context ctx) {
        this.context = ctx;
        this.trackings = new ArrayList<>();
        this.trackables = new ArrayList<>();
        this.trackingList = new ArrayList<>();

        TrackableService trackableSvc = new TrackableService(ctx);
        this.trackables = trackableSvc.parseFile(ctx);

        TrackingService trackingSvc = TrackingService.getSingletonInstance(ctx);
        this.trackings = trackingSvc.getAllTrackings();

    }

    public void updateTrackingService(Context ctx, int hourOfDay, int minute, String format) {

        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);

        String searchDate ="09/10/2018 "+ String.valueOf(hourOfDay) +":"+String.valueOf(minute)+":00 "+format;

        int searchWindow = 10; // +/- 5 mins
        Date date = null;
        try {
            date = dateFormat.parse(searchDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TrackingService trackingService = TrackingService.getSingletonInstance(ctx);
        this.trackings = trackingService.getTrackingInfoForTimeRange(date, searchWindow, 0);

    }

    public ArrayList<SelectedTrackingModel> process()
    {
        ArrayList<SelectedTrackingModel> returnList = new ArrayList<>();
        returnList.clear();

        for (TrackingDetailModel t : trackings)
        {
            SelectedTrackingModel s = new SelectedTrackingModel(findTrackableFromId(t.getTrackableId()),t);

            returnList.add(s);
        }

        return returnList;
    }

    private TrackableDataModel findTrackableFromId(int id)
    {
        for (TrackableDataModel tdm: this.trackables
             ) {
            if(tdm.getId() == id)
                return tdm;
        }
        return null;
    }

    public ArrayList<SelectedTrackingModel> getTrackingList() {
        return trackingList;
    }

    public ArrayList<TrackableDataModel> getTrackables() {
        return trackables;
    }

    public void setTrackables(ArrayList<TrackableDataModel> trackables) {
        this.trackables = trackables;
    }

    public ArrayList<TrackingDetailModel> getTrackings() {
        return trackings;
    }

    public void setTrackings(ArrayList<TrackingDetailModel> trackings) {
        this.trackings = trackings;
    }

    public void setTrackingList(ArrayList<SelectedTrackingModel> trackingList) {
        this.trackingList = trackingList;
    }
}
