package Scheduling;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import Recievers.NotificationReciever;

import static android.content.Context.ALARM_SERVICE;

public class MyAlarmManager {

    public static void setAlarm(Context ctx, int hour, int min)
    {
        new Thread(){
            public void run(){

                System.out.println("---------------------------");
                System.out.println("ALARM TIME: " + hour + ":" + min);

        Calendar calendar = Calendar.getInstance();



        //calendar.set(Calendar.MONTH, 8);
        //calendar.set(Calendar.YEAR, 2016);
        //calendar.set(Calendar.DAY_OF_MONTH, 18);

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE,  min);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM,Calendar.PM);

        Intent myIntent = new Intent(ctx.getApplicationContext(), NotificationReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx.getApplicationContext(), 5, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)ctx.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
            }
        }.start();

    }

    public static void snoozeAlarm(Context ctx)
    {
        new Thread(){
            public void run(){

                System.out.println("---------------------------");
               // System.out.println("ALARM TIME: " + hour + ":" + min);

                Calendar c = Calendar.getInstance();
                c.add(Calendar.MINUTE, 1);
                long afterMinutes = c.getTimeInMillis();

                Intent myIntent = new Intent(ctx.getApplicationContext(), NotificationReciever.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx.getApplicationContext(), 4, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager)ctx.getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC, afterMinutes, pendingIntent);
            }
        }.start();

    }
}
