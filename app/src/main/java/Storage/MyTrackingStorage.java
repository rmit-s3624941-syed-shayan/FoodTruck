package Storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.SparseArray;
import android.widget.Toast;

import java.util.ArrayList;

import HttpHelper.RequestAsync;
import Models.DistNDurationModel;
import Models.SelectedTrackingModel;
import Scheduling.MyAlarmManager;

public class MyTrackingStorage {



    private static ArrayList<SelectedTrackingModel> myList;
    private static SparseArray<DistNDurationModel> travelTimes;



    public static void addTracking(Context context, SelectedTrackingModel myModel) {
        if (myList == null)
            myList = new ArrayList();

        if (travelTimes == null)
            travelTimes = new SparseArray<>();

        myList.add(myModel);
        setTravelTime(context, myModel);

    }

    private static void setTravelTime(Context context,SelectedTrackingModel stm)
    {
        new Thread(){
            public void run(){

                System.out.println("---------------------------");

        RequestAsync getReq = new RequestAsync();
        try {
            String params = stm.getTrackingDetail().getLatitude() + "," + stm.getTrackingDetail().getLongitude();
            ArrayList<DistNDurationModel> suggestionTrackings = getReq.execute(params).get();

            travelTimes.put(stm.getSelectionId(),suggestionTrackings.get(0));

            String time = stm.getMeetupTime();
            String[] HM = time.split(":");
            String mins = HM[1].replaceAll("[^\\d.]", "");
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            int suggInterval = Integer.parseInt(sharedPrefs.getString("notificationPeriod", "60"));
            int interval = suggInterval/60;


            int travelMins = suggestionTrackings.get(0).getDurationVal()/60;
            int sendMins = Integer.parseInt(mins) - travelMins - interval;
            int sendHrs = Integer.parseInt(HM[0]);
            if(sendMins < 0){
                sendHrs--;
                sendMins += 60;
            }
            if(sendHrs == 0){sendHrs=12;}
            MyAlarmManager.setAlarm(context, sendHrs,sendMins);
        } catch (Exception e) {
            e.printStackTrace();
        }

            }
        }.start();
    }

    public static void removeTracking(Context context, SelectedTrackingModel myModel) {
        for (SelectedTrackingModel stm: myList)
        {
            if(stm.getSelectionId() == myModel.getSelectionId())
            myList.remove(stm);
            return;
        }

        Toast.makeText(context,"Not in List", Toast.LENGTH_LONG);
    }

    public static SelectedTrackingModel getTracking(Context context, int sId) {
        for (SelectedTrackingModel stm: myList)
        {
            if(stm.getSelectionId() == sId)
            return stm;
        }

        Toast.makeText(context,"Not in List", Toast.LENGTH_LONG);
        return null;
    }

    public static void editTracking(Context context, SelectedTrackingModel myModel) {
        for (SelectedTrackingModel stm: myList)
        {
            if(stm.getSelectionId() == myModel.getSelectionId()) {
                myList.remove(stm);
                myList.add(myModel);

                return;
            }
        }

        Toast.makeText(context,"Failed To Edit", Toast.LENGTH_LONG);
    }

    public static ArrayList<SelectedTrackingModel> getMyList() {

        if(myList == null)
            myList = new ArrayList<>();
        return myList;
    }

    public static void setMyList(ArrayList<SelectedTrackingModel> myList) {
        MyTrackingStorage.myList = myList;
    }

    public static int getTravelTimeforId(int id)
    {
        int res = 99;
        res = travelTimes.get(id).getDurationVal();
        return res;
    }


}
