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
import net.ddns.tetraowl.vertpln.Utils;
import net.ddns.tetraowl.vertpln.VertretungsplanTricks;

import java.io.File;
import java.io.IOException;

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

    public void getNewestPlan(boolean today) { ;
        MoodleTricks moodleTricks = new MoodleTricks(this.getBaseContext());
        new Thread(()-> {
            try{
                if (today) {
                    moodleTricks.getMoodleSite("https://moodle.gym-voh.de/pluginfile.php/3952/mod_resource/content/4/schuelerheute.htm?embed=1",this::onLoadToday);
                } else {
                    moodleTricks.getMoodleSite("https://moodle.gym-voh.de/pluginfile.php/3953/mod_resource/content/3/schuelermorgen.htm?embed=1",this::onLoadTomorrow);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    private void onLoadToday(String s) {
        VertretungsplanTricks plan = new VertretungsplanTricks(this.getBaseContext());
        plan.setOfflinePlanToday(s);
        Shelf shelf = new Shelf(new File(getApplication().getFilesDir(),"latest"));
        try{
            shelf.item("today").put(plan.getHours(s));
        } catch (IndexOutOfBoundsException e) {

        }
        System.out.println("SAVED_T");
    }
    private void onLoadTomorrow(String s) {
        VertretungsplanTricks plan = new VertretungsplanTricks(this.getBaseContext());
        plan.setOfflinePlanTomorrow(s);
        System.out.println("SAVED_TOM");
    }

    private void countdown() {
        new CountDownTimer(1800, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                BackgroundService.this.countdown();
                if(Utils.viaMobile(BackgroundService.this.getBaseContext())) return;
                BackgroundService.this.getNewestPlan(true);
                BackgroundService.this.getNewestPlan(false);
            }
        }.start();
    }
}
