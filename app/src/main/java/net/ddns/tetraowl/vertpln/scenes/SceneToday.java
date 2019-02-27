package net.ddns.tetraowl.vertpln.scenes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import net.ddns.tetraowl.vertpln.MainActivity;
import net.ddns.tetraowl.vertpln.R;
import net.ddns.tetraowl.vertpln.scene_managing.SceneClass;

public class SceneToday extends SceneClass {
    MainActivity mainActivity;
    WebView web;
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
        TextView topic = this.mainActivity.findViewById(R.id.topic);
        topic.setText("Vertretungsplan Heute");
        this.mainActivity.findViewById(R.id.back).setOnClickListener(this::back);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (view.getUrl().equals("https://moodle.gym-voh.de")) {
                    SceneToday.super.getController().changeTo(new SceneGet(new SceneToday()), R.transition.normal);
                }
            }
        });
        SharedPreferences pref = this.mainActivity.getPreferences(Context.MODE_PRIVATE);
        String cookieString = pref.getString("moodleCookie",null)+"; path=/";
        CookieManager.getInstance().setCookie("moodle.gym-voh.de",cookieString);
        this.web.loadUrl("https://moodle.gym-voh.de/pluginfile.php/3952/mod_resource/content/4/schuelerheute.htm?embed=1");
        countdown();
    }
    private void countdown() {
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                SceneToday.this.web.reload();
                SceneToday.this.countdown();
            }
        }.start();
    }
    private void back(View view) {
        handleBackButtonPress();
    }
}
