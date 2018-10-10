package Services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tutelab.haseebpaul.mada1.SuggestionActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import HttpHelper.RequestAsync;
import Models.DistNDurationModel;
import Models.SelectedTrackingModel;
import Models.SuggestedTrackingModel;
import Models.TrackableDataModel;
import Models.TrackingDetailModel;
import Storage.MyTrackingStorage;

public class SuggestionService {

    private static Context ctx;



    private static ArrayList<DistNDurationModel> suggestionTrackings;
    private static ArrayList<TrackingDetailModel> availableTrackings;

    private static ArrayList<SuggestedTrackingModel> suggestionList;

    public SuggestionService(Context c) {
        ctx = c;

        suggestionTrackings = new ArrayList<>();
        availableTrackings = new ArrayList<>();
        suggestionList = new ArrayList<>();
        String format;
        updateTrackingService(ctx);
        boolean check = loadSuggestions();

        if(check){
        Intent ActvSuggestion = new Intent(ctx.getApplicationContext(), SuggestionActivity.class);
        ctx.startActivity(ActvSuggestion);
        }
    }

    private boolean loadSuggestions()
    {
        System.out.println("In updateTrackablePos");

        suggestionList = new ArrayList<>();

        RequestAsync getReq = new RequestAsync();
        try {
            String params = getDestinationCoordinates();
            if(params == ""){return false;}
            suggestionTrackings = getReq.execute(params).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i=0; i<suggestionTrackings.size();i++)
        {
            DistNDurationModel d = suggestionTrackings.get(i);
            TrackingDetailModel tdm = availableTrackings.get(i);

            Log.d("STOPTIME", (String.valueOf(tdm.getStopTime())));

            if(tdm.getStopTime()>0)
            {
                Log.d("TRKID", (String.valueOf(tdm.getTrackableId())));
                suggestionList.add(new SuggestedTrackingModel(tdm,d));
            }

        }

        System.out.println("################## ");
        for (int i=0; i<suggestionList.size(); i++)
        {
            SuggestedTrackingModel s = suggestionList.get(i);
            s.getdNd().printdata();
            System.out.println("TRACKABLE ID: "+s.gettDM().getTrackableId());
        }
        System.out.println("################ ");

        Collections.sort(suggestionList, new Comparator<SuggestedTrackingModel>() {
            @Override
            public int compare(SuggestedTrackingModel o1, SuggestedTrackingModel o2) {
                return o1.getdNd().getDurationVal() - (o2.getdNd().getDurationVal());
            }
        });
        System.out.println("::::::::::::::::: ");
        for (int i=0; i<suggestionList.size(); i++)
        {
            SuggestedTrackingModel s = suggestionList.get(i);
            s.getdNd().printdata();
            System.out.println("TRACKABLE ID: "+s.gettDM().getTrackableId());
        }
        System.out.println("::::::::::::::::: ");

        return true;
    }

    public String getDestinationCoordinates()
    {
        String result = "";
        int arrSize = availableTrackings.size();
        for (int i=0;i< arrSize;i++)
        {
            TrackingDetailModel tdm = availableTrackings.get(i);

            result += tdm.getLatitude() + "," + tdm.getLongitude();

            if(i < arrSize-1)
            {
                result += "|";
            }
        }

        return result;
    }

    private void updateTrackingService(Context ctx) {

        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
        final long l = System.currentTimeMillis();


        long totalSecs = l/1000;

        int mins = (int) ((l / (1000*60)) % 60);
        int hours   = (int) ((l / (1000*60*60)) % 24);
        int minute = (int) mins;
        int hourOfDay = (int)hours;

        String searchDate = formatTime(hourOfDay,minute);//"10/06/2018 "+ String.valueOf(hourOfDay) +":"+String.valueOf(minute)+":00 "+format;
        System.out.println("SEARCH DATE: "+searchDate);
        int searchWindow = 10; // +/- 5 mins
        Date date = null;
        try {
            date = dateFormat.parse(searchDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TrackingService trackingService = TrackingService.getSingletonInstance(ctx);
        availableTrackings = trackingService.getAllTrackings();//getTrackingInfoForTimeRange(date, searchWindow, 0);

    }

    private String formatTime(int hourOfDay, int minutes)
    {
        String result = "09/10/2018 ";
        String format, hStr, mStr;

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

        if(hourOfDay < 10)
        hStr = "0" + hourOfDay;
        else
        hStr = String.valueOf(hourOfDay);


        if(minutes < 10)
            mStr = "0" + minutes;
        else
            mStr = String.valueOf(minutes);


        result += hStr +":"+mStr+":00 "+format;

        return result;
    }

    public static void AddTracking(SuggestedTrackingModel stm)
    {
        TrackableService TS = new TrackableService(ctx);
        TrackingDetailModel tdm = stm.gettDM();
        TrackableDataModel trackable = TrackableService.getTrackablebyId(tdm.getTrackableId());
        SelectedTrackingModel trackingToAdd = new SelectedTrackingModel(trackable,tdm);

        Date d = tdm.getDate();
        SimpleDateFormat s = new SimpleDateFormat("h:mm a");
        String addDate = s.format(d);
        trackingToAdd.setMeetupTime(addDate);

        MyTrackingStorage.addTracking(ctx,trackingToAdd);
    }

    public static SuggestedTrackingModel getSuggestionAtIndex(int i) {
        try {
            SuggestedTrackingModel s = suggestionList.get(i);
            s.getdNd().printdata();
            System.out.println("TRACKABLE ID: "+s.gettDM().getTrackableId());
            return s;

        } catch (Exception e) {
            return null;
        }
    }

    public static ArrayList<SuggestedTrackingModel> getSuggestionList() {
        return suggestionList;
    }

    public static ArrayList<DistNDurationModel> getSuggestionTrackings() {
        return suggestionTrackings;
    }

    public static void setSuggestionTrackings(ArrayList<DistNDurationModel> suggestionTrackings) {
        SuggestionService.suggestionTrackings = suggestionTrackings;
    }
}
