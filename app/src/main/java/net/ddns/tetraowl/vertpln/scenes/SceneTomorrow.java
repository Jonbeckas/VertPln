package net.ddns.tetraowl.vertpln.scenes;

import android.os.CountDownTimer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import net.ddns.tetraowl.vertpln.*;
import net.ddns.tetraowl.vertpln.scene_managing.SceneClass;

public class SceneTomorrow extends SceneClass {
    MainActivity mainActivity;
    WebView web;
    ProgressBar load;
    VertretungsplanTricks plan;
    SwipeRefreshLayout refresh;
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
        this.plan = new VertretungsplanTricks(this.mainActivity);
        web = this.mainActivity.findViewById(R.id.toBr);
        this.web.setVisibility(View.INVISIBLE);
        this.load = this.mainActivity.findViewById(R.id.load);
        TextView topic = this.mainActivity.findViewById(R.id.topic);
        this.mainActivity.findViewById(R.id.back).setOnClickListener(this::back);
        MoodleTricks moodle = new MoodleTricks(this.mainActivity);
        if (Utils.isConnected(this.mainActivity)) {
            topic.setText("Vertretungsplan Morgen");
            this.plan.getOfflinePlanTomorrow(this.web);
            onFinished();
            //moodle.getMoodleSite(this.web,"https://moodle.gym-voh.de/pluginfile.php/3953/mod_resource/content/3/schuelermorgen.htm?embed=1",this::onFinished);
        } else {
            topic.setText("Vertretungsplan Morgen (Offline)");
            this.plan.getOfflinePlanTomorrow(this.web);
            onFinished();
        }
        this.refresh = this.mainActivity.findViewById(R.id.refresh);
        this.refresh.setOnRefreshListener(this::refresh);
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
            if (Utils.isConnected(this.mainActivity)) {
                topic.setText("Vertretungsplan Morgen");
                this.plan.getOfflinePlanTomorrow(this.web);
            } else {
                topic.setText("Vertretungsplan Morgen (Offline)");
                this.plan.getOfflinePlanTomorrow(this.web);
            }
            this.refresh.setRefreshing(false);
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
                SceneTomorrow.this.refresh();
                SceneTomorrow.this.countdown();
            }
        };
        this.cd.start();
    }

    private void back(View view) {
        handleBackButtonPress();
    }
}
