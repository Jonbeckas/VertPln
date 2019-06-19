package net.ddns.tetraowl.vertpln.scenes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import net.ddns.tetraowl.vertpln.*;
import net.ddns.tetraowl.vertpln.scene_managing.SceneClass;

import java.io.File;

public class SceneToday extends SceneClass {
    MainActivity mainActivity;
    WebView web;
    ProgressBar load;
    VertretungsplanTricks plan;

    @Override
    public int getLayoutId() {
        return R.layout.web;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public boolean handleBackButtonPress() {
        super.getController().changeTo(new SceneStart(),R.transition.normal);
        return true;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onBoth() {
        this.mainActivity = super.getController().getActivity();
        this.web = this.mainActivity.findViewById(R.id.toBr);
        this.plan = new VertretungsplanTricks(this.mainActivity);
        this.load = this.mainActivity.findViewById(R.id.load);
        TextView topic = this.mainActivity.findViewById(R.id.topic);
        this.mainActivity.findViewById(R.id.back).setOnClickListener(this::back);
        MoodleTricks moodle = new MoodleTricks(this.mainActivity);
        if (Utils.isConnected(this.mainActivity)) {
            topic.setText("Vertretungsplan Heute");
            moodle.getMoodleSite(this.web,"https://moodle.gym-voh.de/pluginfile.php/3952/mod_resource/content/4/schuelerheute.htm?embed=1",this::onFinished);
        } else {
            topic.setText("Vertretungsplan Heute (Offline)");
            this.plan.getOfflinePlanToday(this.web);
            onFinished();
        }
        SwipeRefreshLayout refresh = this.mainActivity.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(this::refresh);
        countdown();
    }

    private void onFinished() {
        this.web.setVisibility(View.VISIBLE);
        this.load.setVisibility(View.GONE);
        this.plan.setOfflinePlanToday(this.web);
    }

    private void refresh() {
        try {
            TextView topic = this.mainActivity.findViewById(R.id.topic);
            this.mainActivity.findViewById(R.id.back).setOnClickListener(this::back);
            MoodleTricks moodle = new MoodleTricks(this.mainActivity);
            if (Utils.isConnected(this.mainActivity)) {
                topic.setText("Vertretungsplan Heute");
                moodle.getMoodleSite(this.web,"https://moodle.gym-voh.de/pluginfile.php/3952/mod_resource/content/4/schuelerheute.htm?embed=1",this::onFinished);
            } else {
                topic.setText("Vertretungsplan Heute (Offline)");
                this.plan.getOfflinePlanToday(this.web);
                this.web.setVisibility(View.VISIBLE);
                this.load.setVisibility(View.GONE);
            }
            SwipeRefreshLayout refresh = this.mainActivity.findViewById(R.id.refresh);
            refresh.setRefreshing(false);
        } catch (NullPointerException e) {
            //
        }

    }

    private void countdown() {
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                SceneToday.this.refresh();
                SceneToday.this.countdown();
            }
        }.start();
    }
    private void back(View view) {
        handleBackButtonPress();
    }
}
