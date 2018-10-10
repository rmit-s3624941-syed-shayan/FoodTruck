package Scheduling;

import android.app.job.JobParameters;
import android.app.job.JobService;

import Services.SuggestionService;

/**
 * JobService to be scheduled by the JobScheduler.
 * start another service
 */
public class SuggestionJobService extends JobService {
    private static final String TAG = "SyncService";

    @Override
    public boolean onStartJob(JobParameters params) {
        //Intent service = new Intent(getApplicationContext(), SuggestionService.class);

        new Thread(){
            public void run(){
                //getApplicationContext().startService(service);
                System.out.println("Firing Suggestion Job _+_+_+_+_");
                SuggestionService s = new SuggestionService(getApplicationContext());
                Util.scheduleJob(getApplicationContext()); // reschedule the job


            }
        }.start();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

}
