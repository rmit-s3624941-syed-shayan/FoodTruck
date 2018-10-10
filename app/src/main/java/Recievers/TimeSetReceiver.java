package Recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import Scheduling.Util;

public class TimeSetReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("BOOT RECEIEVED");
        Util.scheduleJob(context);
    }
}
