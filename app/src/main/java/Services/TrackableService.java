package Services;

import android.content.Context;

import java.util.ArrayList;
import java.util.Scanner;

import android.content.res.Resources;

import com.tutelab.haseebpaul.mada1.R;

import Models.TrackableDataModel;

public class TrackableService {
    private ArrayList<TrackableDataModel> trackableList;
    private static Context context;

    public TrackableService(Context ctx) {
        this.trackableList = new ArrayList<>();
        this.context = ctx;
    }

    public ArrayList<TrackableDataModel> parseFile(Context context) {
        trackableList.clear();
        // resource reference to tracking_data.txt in res/raw/ folder of your project
        // supports trailing comments with //
        try (Scanner scanner = new Scanner(context.getResources().openRawResource(R.raw.food_truck_data))) {
            // match comma and 0 or more whitespace OR trailing space and newline
            scanner.useDelimiter(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)|\\n+");//(",|[\"]|\\n+");

            while (scanner.hasNext())
            {


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
}
