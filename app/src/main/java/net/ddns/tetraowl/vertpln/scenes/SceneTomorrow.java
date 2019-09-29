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
    CountDownTimer cd;
    private SwipeRefreshLayout swipeRefreshLayout;

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
        this.swipeRefreshLayout = this.mainActivity.findViewById(R.id.refresh);
        this.swipeRefreshLayout.setOnRefreshListener(this::refresh);
        this.web.setVisibility(View.INVISIBLE);
        this.load = this.mainActivity.findViewById(R.id.load);
        TextView topic = this.mainActivity.findViewById(R.id.topic);
        this.mainActivity.findViewById(R.id.back).setOnClickListener(this::back);
        if (Utils.isConnected(this.mainActivity)) {
            topic.setText("Vertretungsplan Morgen");
        } else {
            topic.setText("Vertretungsplan Morgen (Offline)");
        }
        this.web.getSettings().setJavaScriptEnabled(true);
        this.web.loadDataWithBaseURL("", new VertretungsplanTricks(this.mainActivity).getOfflinePlanTomorrow(), "text/html", "UTF-8", "");
        onFinished();
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
            this.mainActivity.getBackgroundService().getNewestPlan(false);
            if (Utils.isConnected(this.mainActivity)) {
                topic.setText("Vertretungsplan Morgen");
            } else {
                topic.setText("Vertretungsplan Morgen (Offline)");
            }
            this.web.loadDataWithBaseURL("", new VertretungsplanTricks(this.mainActivity).getOfflinePlanTomorrow(), "text/html", "UTF-8", "");
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
