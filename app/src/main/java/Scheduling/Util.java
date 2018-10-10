package Scheduling;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class Util {

    // schedule the start of the service every 10 - 30 seconds
    public static void scheduleJob(Context context) {
        System.out.println("SCHEDULE JOB");

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        int suggInterval = Integer.parseInt(sharedPrefs.getString("suggestionFrequency", "30"));

        ComponentName serviceComponent = new ComponentName(context, SuggestionJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(suggInterval * 1000); // wait at least
        builder.setOverrideDeadline((suggInterval+10) * 1000); // maximum delay
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        //builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }

}
