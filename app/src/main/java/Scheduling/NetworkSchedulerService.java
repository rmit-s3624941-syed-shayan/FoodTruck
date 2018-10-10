package Scheduling;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import com.tutelab.haseebpaul.mada1.R;

import Recievers.NetworkReceiver;
import Services.SuggestionService;


public class NetworkSchedulerService extends JobService implements
        NetworkReceiver.ConnectivityReceiverListener {

    private static final String TAG = NetworkSchedulerService.class.getSimpleName();

    private NetworkReceiver mConnectivityReceiver;
    boolean postFirstRun, alreadyConnected;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service created");
        postFirstRun = false;
        alreadyConnected = false;
        mConnectivityReceiver = new NetworkReceiver(this);
    }



    /**
     * When the app's NetworkConnectionActivity is created, it starts this service. This is so that the
     * activity and this service can communicate back and forth. See "setUiCallback()"
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return START_NOT_STICKY;
    }


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob" + mConnectivityReceiver);
        registerReceiver(mConnectivityReceiver, new IntentFilter(getString(R.string.CONNECTIVITY_ACTION)));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob");
        unregisterReceiver(mConnectivityReceiver);
        return true;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        if(isConnected && this.postFirstRun && !this.alreadyConnected)
        {
            String message = "Connected to Internet";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

            this.alreadyConnected = true;
            new Thread(){
                public void run(){
                    //getApplicationContext().startService(service);
                    System.out.println("Firing Suggestion Job For Network _+_+_+_+_");
                    SuggestionService s = new SuggestionService(getApplicationContext());


                }
            }.start();
        }
        if(!this.postFirstRun)
        {
            this.postFirstRun = true;
        }
        if(!isConnected)
            this.alreadyConnected = false;
    }
}