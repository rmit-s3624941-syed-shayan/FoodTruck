package Storage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tutelab.haseebpaul.mada1.R;

import java.util.ArrayList;
import Models.SelectedTrackingModel;

public class MyTrackingStorage {



    private static ArrayList<SelectedTrackingModel> myList;



    public void addTracking(Context context, SelectedTrackingModel myModel) {
        if (myList == null)
            myList = new ArrayList();
        myList.add(myModel);
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


}
