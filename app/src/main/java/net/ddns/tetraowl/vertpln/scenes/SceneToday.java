package net.ddns.tetraowl.vertpln.scenes;

import android.os.CountDownTimer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import net.ddns.tetraowl.vertpln.*;
import net.ddns.tetraowl.vertpln.scene_managing.SceneClass;

public class SceneToday extends SceneClass {
    MainActivity mainActivity;
    WebView web;
    ProgressBar load;
    VertretungsplanTricks plan;
    SwipeRefreshLayout swipeRefreshLayout;
    CountDownTimer cd;

    @Override
    public int getLayoutId() {
        return R.layout.web;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public boolean handleBackButtonPress() {
        this.cd.cancel();
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
        this.web.setVisibility(View.INVISIBLE);
        this.swipeRefreshLayout = this.mainActivity.findViewById(R.id.refresh);
        this.swipeRefreshLayout.setOnRefreshListener(this::refresh);
        this.plan = new VertretungsplanTricks(this.mainActivity);
        this.load = this.mainActivity.findViewById(R.id.load);
        TextView topic = this.mainActivity.findViewById(R.id.topic);
        this.mainActivity.findViewById(R.id.back).setOnClickListener(this::back);
        this.web.getSettings().setJavaScriptEnabled(true);
        if (Utils.isConnected(this.mainActivity)) {
            topic.setText("Vertretungsplan Heute");
        } else {
            topic.setText("Vertretungsplan Heute (Offline)");
        }
        this.web.loadDataWithBaseURL("", new VertretungsplanTricks(this.mainActivity).getOfflinePlanToday(), "text/html", "UTF-8", "");
        onFinished();
        /*MoodleTricks moodle = new MoodleTricks(this.mainActivity);
        if (Utils.isConnected(this.mainActivity)) {
            topic.setText("Vertretungsplan Heute");
            this.plan.getOfflinePlanToday(this.web);
            onFinished();
            //moodle.getMoodleSite(this.web,"https://moodle.gym-voh.de/pluginfile.php/3952/mod_resource/content/4/schuelerheute.htm?embed=1",this::onFinished);
        } else {
            topic.setText("Vertretungsplan Heute (Offline)");
            this.plan.getOfflinePlanToday(this.web);
            onFinished();
        }
        SwipeRefreshLayout refresh = this.mainActivity.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(this::refresh);*/
        countdown();
    }

    private void onFinished() {
        this.web.setVisibility(View.VISIBLE);
        this.load.setVisibility(View.GONE);
    }

    private void refresh() {
        try {
            MoodleTricks moodle = new MoodleTricks(this.mainActivity);
            TextView topic = this.mainActivity.findViewById(R.id.topic);
            this.mainActivity.getBackgroundService().getNewestPlan(true);
            if (Utils.isConnected(this.mainActivity)) {
                topic.setText("Vertretungsplan Heute");
            } else {
                topic.setText("Vertretungsplan Heute (Offline)");
            }
            this.web.loadDataWithBaseURL("", new VertretungsplanTricks(this.mainActivity).getOfflinePlanToday(), "text/html", "UTF-8", "");
            this.swipeRefreshLayout.setRefreshing(false);
        } catch (NullPointerException e) {
            //
        }

    }

    private void countdown() {
        this.cd = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                SceneToday.this.refresh();
                SceneToday.this.countdown();
            }
        };
        this.cd.start();
    }
    private void back(View view) {
        handleBackButtonPress();
    }
}
