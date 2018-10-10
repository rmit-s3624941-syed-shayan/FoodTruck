package Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.tutelab.haseebpaul.mada1.R;

import Recievers.NotificationActionReceiver;

public class NotificationService {


    public NotificationService() {

    }

    public static void sendNotification(Context ctx)
    {
        Intent acceptIntent = new Intent(ctx, NotificationActionReceiver.class);
        acceptIntent.putExtra("action","accept");
        PendingIntent pAcceptIntent = PendingIntent.getBroadcast(ctx, 1, acceptIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent snoozeIntent = new Intent(ctx, NotificationActionReceiver.class);
        snoozeIntent.putExtra("action","snooze");
        PendingIntent pSnoozeIntent = PendingIntent.getBroadcast(ctx, 2 , snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        System.out.println("Inside Service");
        String title = "Time to leave!";
        String body = "Trackable nearby";
        String subject = "Start Walking...";
        NotificationManager notif=(NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification.Builder
                (ctx).setContentTitle(title).setContentText(body).
                setContentTitle(subject).setSmallIcon(R.drawable.abc_ic_star_black_36dp)
                .addAction(R.drawable.abc_ic_star_black_36dp, "Accept", pAcceptIntent)
                .addAction(R.drawable.abc_ic_star_black_36dp, "Snooze", pSnoozeIntent).build();

        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.notify(0, notify);
    }
}
