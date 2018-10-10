package helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import Models.SelectedTrackingModel;
import Models.TrackableDataModel;
import Models.TrackingDetailModel;
import Services.TrackableService;
import Services.TrackingService;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static Context ctx;
    private static final int DATABASE_VERSION = 1;
    private static ArrayList<TrackableDataModel> trackableList;
    private static ArrayList<TrackingDetailModel> detailList;
    private static final String DATABASE_NAME = "FoodTruckDB.db";
    private static String DB_PATH = "";
    private static final String CREATE_trackable_TABLE = "CREATE TABLE TRACKABLES (ID INTEGER PRIMARY KEY UNIQUE, CATEGORY VARCHAR (100), LINK VARCHAR (100), TITLE  VARCHAR (100), DESCRIPTION VARCHAR (100))";
    private static final String TrackingDetails = "CREATE TABLE trackingDetail (TrackableID INTEGER, date DATETIME, StopTime INTEGER, Latitude VARCHAR (100), Longitude VARCHAR (100))";
    private static final String MyTrackingTable = "CREATE TABLE mytracking (Selection_ID INTEGER, Trackable_ID INTEGER,  Latitude VARCHAR (100), Longitude VARCHAR (100), Meetup_Time TIME)";
    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS TRACKABLES";
    private static SQLiteDatabase MyDB;
    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
        File file = new File(context.getApplicationInfo().dataDir + "/databases/"+DATABASE_NAME);

            this.ctx = context;
            System.out.println("-----------Database Created-----------");
            trackableList = new ArrayList<>();
            detailList = new ArrayList<>();
            MyDB = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        MyDB = sqLiteDatabase;
        /*
        Cursor c =MyDB.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = 'TRACKABLES'", null);
        c.moveToFirst();
        System.out.println("------------------" + c);
        if(c.toString().contains("TRACKABLES"))
        {
            System.out.println("---------Database and Trackable table already exists----------");
        }  */
       // else {
            sqLiteDatabase.execSQL(CREATE_trackable_TABLE);
            System.out.println("-----------Trackable Table Created --------");
            loadtrackableArray();
            for (TrackableDataModel t : trackableList) {
                sqLiteDatabase.execSQL("INSERT INTO TRACKABLES (ID, CATEGORY, LINK, TITLE, DESCRIPTION) VALUES (" + t.getId() + ",'" + t.getCategory() + "', '" + t.getLink() + "', '" + t.getTitle() + "', '" + t.getDescription() + "');");
            }
            sqLiteDatabase.execSQL(TrackingDetails);
            System.out.println("-----------Tracking Details Table Created --------");
            loadtrackingdetailsArray();
            System.out.println(detailList);
            for (TrackingDetailModel d : detailList) {
                sqLiteDatabase.execSQL("INSERT INTO trackingDetail (TrackableID, date, StopTime, Latitude, Longitude) VALUES (" + d.getTrackableId() + ",'" + d.getDate() + "', " + d.getStopTime() + ",'" + d.getLatitude() + "','" + d.getLongitude() + "');");
            }
            sqLiteDatabase.execSQL(MyTrackingTable);

       // }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldv, int newV)
    {
        Log.e(TAG, "Updating table from " + oldv + " to " + newV);
        System.out.println("-----------Deleting trackbles-----------");
        sqLiteDatabase.execSQL(DELETE_TABLE);
        onCreate(sqLiteDatabase);
        loadtrackableArray();
    }

    public void loadtrackableArray()
    {
        TrackableService TS = new TrackableService(ctx);
        trackableList = TS.parseFile(ctx);
    }
    public void loadtrackingdetailsArray()
    {
        TrackingService TSD = TrackingService.getSingletonInstance(ctx);
        detailList = TSD.getAllTrackings();
    }
    public static void Addmytracking(SelectedTrackingModel mytracking)
    {
        TrackableDataModel tdm = mytracking.getTrackable();
        TrackingDetailModel tDtlM = mytracking.getTrackingDetail();
        String d = mytracking.getMeetupTime();
        int id = mytracking.getSelectionId();
        MyDB.execSQL("INSERT INTO mytracking (Selection_ID, Trackable_ID, Latitude, Longitude, Meetup_Time) " +
                "VALUES (" + id + ", " + tdm.getId()+ ", '" + tDtlM.getLatitude() + "', '" + tDtlM.getLongitude() + "', '" + d +"' )");
    }
    public Cursor loadmytrackings()
    {
        MyDB.execSQL("Select * from mytracking");
        Cursor res = MyDB.rawQuery("select * from mytracking",null);
        return res;
    }
}
