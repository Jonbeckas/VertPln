package net.ddns.tetraowl.vertpln.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import net.ddns.tetraowl.vertpln.MainActivity;
import net.ddns.tetraowl.vertpln.R;

public class Notifications {
    private final Context mainActivity;
    private int nid;

    public Notifications(Context mainActivity) {
            this.mainActivity = mainActivity;
            nid = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    public void createNotificationChannel() {
            CharSequence name = "VertPln";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("VPlan", name, importance);
            NotificationManager notificationManager = this.mainActivity.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
    }
    public void sendNotification(String text) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mainActivity, "VPlan");
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentTitle("Stundenausfall");
        builder.setContentText(text);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(text));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mainActivity);
        notificationManager.notify(1598145+nid,builder.build());
        nid++;
    }
}
