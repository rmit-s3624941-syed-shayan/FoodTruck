package HttpHelper;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import Models.DistNDurationModel;
import Models.TrackingDetailModel;

public class RequestAsync extends AsyncTask<String, Void, ArrayList<DistNDurationModel>> {

    private static final String TAG = "Request ASync";
    ArrayList<TrackingDetailModel> trackings;

    @Override
        protected ArrayList<DistNDurationModel> doInBackground(String... params){
            trackings = new ArrayList<>();
            double userLatitude = -37.807425;
            double userLongitude = 144.963814;
            String formattedCoordinates = params[0];//"-37.810045,144.964220|-37.810045,144.964220";
            String stringUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&mode=walking&origins=" + userLatitude + "," + userLongitude
                    + "&destinations=" + formattedCoordinates + "&key=AIzaSyAEd5FA1zBD9lW5wIWSBTq1L4agBvnrws8";
            String result;
            String inputLine;
            ArrayList<DistNDurationModel> dNd = new ArrayList<>();
            try {
                //Create a URL object holding our url

                result = HttpUtility.getJSON(stringUrl);
                System.out.println("######" + result);
                JSONObject jsonObject = new JSONObject(result);



                JSONArray resRows = (JSONArray) jsonObject.get("rows");
                JSONObject rowObj = (JSONObject) resRows.get(0);
                JSONArray resElements = (JSONArray) rowObj.get("elements");

                for (int i = 0; i < resElements.length(); i++) {
                    Log.d(TAG, "GETRequest: i= " + i);
                    JSONObject elementObj = (JSONObject) resElements.get(i);
                    JSONObject distanceObj = (JSONObject) elementObj.get("distance");
                    JSONObject durationObj = (JSONObject) elementObj.get("duration");
                    String disT = distanceObj.getString("text");
                    int disV = distanceObj.getInt("value");
                    String durT = durationObj.getString("text");
                    int durV = durationObj.getInt("value");

                    dNd.add(new DistNDurationModel(disT,durT,durV,disV));
                    //relativeTrackableLocations.add(new RelativeTrackableLocation(MainActivity.TRACKINGS_LIST.get(i).trackableId, String.valueOf(destinations.get(i)), trucks.get(i).getLatitude(), trucks.get(i).getLongitude(), Integer.parseInt(distance), Integer.parseInt(duration), trucks.get(i).date));
                }
            }
            catch(Exception e){
                e.printStackTrace();
                result = null;
            }
            return dNd;
        }


}
