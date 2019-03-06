package net.ddns.tetraowl.vertpln.scenes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.transition.Scene;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import net.ddns.tetraowl.vertpln.*;
import net.ddns.tetraowl.vertpln.scene_managing.SceneClass;

import java.util.Timer;
import java.util.TimerTask;

public class SceneTomorrow extends SceneClass {
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
        this.plan = new VertretungsplanTricks(this.mainActivity);
        web = this.mainActivity.findViewById(R.id.toBr);
        this.load = this.mainActivity.findViewById(R.id.load);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (!url.equals("https://moodle.gym-voh.de/login/index.php")) {
                    SceneTomorrow.this.web.setVisibility(View.VISIBLE);
                    SceneTomorrow.this.load.setVisibility(View.GONE);
                    SceneTomorrow.this.plan.setOfflinePlanTomorrow(view);
                }
                super.onPageFinished(view, url);
            }
        });
        TextView topic = this.mainActivity.findViewById(R.id.topic);
        this.mainActivity.findViewById(R.id.back).setOnClickListener(this::back);
        MoodleTricks moodle = new MoodleTricks(this.mainActivity);
        String cookieString = moodle.getMoodleCookie();
        CookieManager.getInstance().setCookie("moodle.gym-voh.de",cookieString);
        if (Utils.isConnected(this.mainActivity)) {
            topic.setText("Vertretungsplan Morgen");
            web.loadUrl("https://moodle.gym-voh.de/pluginfile.php/3953/mod_resource/content/3/schuelermorgen.htm?embed=1");
        } else {
            topic.setText("Vertretungsplan Morgen (Offline)");
            this.plan.getOfflinePlanTomorrow(this.web);
        }
        countdown();
    }

    private void countdown() {
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                SceneTomorrow.this.web.reload();
                SceneTomorrow.this.countdown();
            }
        }.start();
    }

    private void back(View view) {
        handleBackButtonPress();
    }
}
