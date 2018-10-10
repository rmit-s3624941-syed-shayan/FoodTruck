package Services;

import android.content.Context;

import com.tutelab.haseebpaul.mada1.R;

import java.util.ArrayList;
import java.util.Scanner;

import Models.TrackableDataModel;

public class TrackableService {
    private static ArrayList<TrackableDataModel> trackableList;
    private static Context context;

    public TrackableService(Context ctx) {
        trackableList = new ArrayList<>();
        context = ctx;

        parseFile(context);
    }

    public ArrayList<TrackableDataModel> parseFile(Context context) {
        trackableList.clear();
        // resource reference to tracking_data.txt in res/raw/ folder of your project
        // supports trailing comments with //
        try (Scanner scanner = new Scanner(context.getResources().openRawResource(R.raw.food_truck_data))) {
            // match comma and 0 or more whitespace OR trailing space and newline
            scanner.useDelimiter(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)|\\n+");//(",|[\"]|\\n+");

            while (scanner.hasNext()) {


                int id = Integer.parseInt(scanner.next());
                String title = scanner.next();
                String description = scanner.next();
                String link = scanner.next();
                String category = scanner.next();

                TrackableDataModel tDM = new TrackableDataModel(id, title, description, link, category);
                tDM.printData();
                trackableList.add(tDM);


            }

            return trackableList;
        }
    }
        public static TrackableDataModel getTrackablebyId(int id)
        {

            for (TrackableDataModel t:trackableList
                 ) {
                if(t.getId() == id)
                    return t;

            }

            return null;
        }





}
