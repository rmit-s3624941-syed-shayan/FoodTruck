package Recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import Scheduling.MyAlarmManager;

public class NotificationActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getStringExtra("action");
        if(action.equals("accept")){
            performAction1();
        }
        else if(action.equals("snooze")){
            performAction2(context);

        }
        //This is used to close the notification tray
        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);


    }

    public void performAction1()
    {
        System.out.println("Action Add");

    }

    public void performAction2(Context ctx)
    {
        System.out.println("Action Snooze");
        MyAlarmManager.snoozeAlarm(ctx);
    }
}
