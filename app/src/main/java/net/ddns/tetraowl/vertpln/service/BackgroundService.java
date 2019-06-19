package net.ddns.tetraowl.vertpln.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import net.ddns.tetraowl.vertpln.MoodleTricks;
import net.ddns.tetraowl.vertpln.R;
import net.ddns.tetraowl.vertpln.VertretungsplanTricks;
import net.ddns.tetraowl.vertpln.scenes.SceneToday;
import org.jetbrains.annotations.Nullable;

public class BackgroundService extends Service {
    private WebView web;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("VertPln","Der VertPln Service wurde gestoppt");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("VertPln","Der VertPln Service wurde gestartet");
        getNewestPlan();
        countdown();
        return super.onStartCommand(intent, flags, startId);
    }

    private void getNewestPlan() {
        final WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 0;
        params.width = 0;
        params.height = 0;

        final WebView wv = new WebView(this);
        this.web =wv;
        MoodleTricks moodleTricks = new MoodleTricks(this.getBaseContext());
        moodleTricks.getMoodleSite(wv,"https://moodle.gym-voh.de/pluginfile.php/3952/mod_resource/content/4/schuelerheute.htm?embed=1",this::onLoad);

    }

    private void onLoad() {
        VertretungsplanTricks plan = new VertretungsplanTricks(this.getBaseContext());
        plan.setOfflinePlanToday(this.web);
    }

    private void countdown() {
        new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                BackgroundService.this.getNewestPlan();
                BackgroundService.this.countdown();
            }
        }.start();
    }
}
