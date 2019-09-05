package net.ddns.tetraowl.vertpln.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.webkit.WebView;
import com.toddway.shelf.Shelf;
import net.ddns.tetraowl.vertpln.MoodleTricks;
import net.ddns.tetraowl.vertpln.VertretungsplanTricks;

import java.io.File;

public class BackgroundService extends Service {
    private WebView tm;
    private WebView td;

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
        getNewestPlan(true);
        getNewestPlan(false);
        countdown();
        return super.onStartCommand(intent, flags, startId);
    }

    private void getNewestPlan(boolean today) {
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
        MoodleTricks moodleTricks = new MoodleTricks(this.getBaseContext());
        if (today) {
            this.td =wv;
            moodleTricks.getMoodleSite(this.td,"https://moodle.gym-voh.de/pluginfile.php/3952/mod_resource/content/4/schuelerheute.htm?embed=1",this::onLoadToday);
        } else {
            this.tm =wv;
            moodleTricks.getMoodleSite(this.tm,"https://moodle.gym-voh.de/pluginfile.php/3953/mod_resource/content/3/schuelermorgen.htm?embed=1",this::onLoadTomorrow);
        }


    }

    private void onLoadToday() {
        VertretungsplanTricks plan = new VertretungsplanTricks(this.getBaseContext());
        plan.setOfflinePlanToday(this.td);
        Shelf shelf = new Shelf(new File(getApplication().getFilesDir(),"latest"));
        shelf.item("today").put(plan.getHours());
        System.out.println("SAVED_T");
    }
    private void onLoadTomorrow() {
        VertretungsplanTricks plan = new VertretungsplanTricks(this.getBaseContext());
        plan.setOfflinePlanTomorrow(this.tm);
        System.out.println("SAVED_TOM");
    }

    private void countdown() {
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                BackgroundService.this.getNewestPlan(true);
                BackgroundService.this.getNewestPlan(false);
                BackgroundService.this.countdown();
            }
        }.start();
    }
}
