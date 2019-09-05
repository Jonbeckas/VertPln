package net.ddns.tetraowl.vertpln.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class Autostart extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent2 = new Intent(context,BackgroundService.class);
        context.startService(intent2);
        Log.i("VertPln","Service Autostart");
    }
}
