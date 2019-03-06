package net.ddns.tetraowl.vertpln.scenes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import net.ddns.tetraowl.vertpln.MainActivity;
import net.ddns.tetraowl.vertpln.R;
import net.ddns.tetraowl.vertpln.scene_managing.SceneClass;

@Deprecated
public class SceneGet extends SceneClass {
    MainActivity mainActivity;
    WebView web;
    SceneClass scene;
    public SceneGet() {}
    public SceneGet(SceneClass scene) {
        if (scene != null) {
            this.scene = scene;
        }
    }
    @Override
    public int getLayoutId() {
        return R.layout.web;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public boolean handleBackButtonPress() {
        if (this.scene != null) {
            super.getController().changeTo(this.scene, R.transition.normal);
        }
        else {
            super.getController().changeTo(new SceneSettings(),R.transition.normal);
        }
        return true;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onBoth() {
        this.mainActivity = super.getController().getActivity();
        TextView topic = this.mainActivity.findViewById(R.id.topic);
        topic.setText("Login (bitte 'angemeldet bleiben' auswählen)");
        this.web = this.mainActivity.findViewById(R.id.toBr);
        this.web.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                Log.d("test",view.getUrl());
                if (!"https://moodle.gym-voh.de/login/index.php".equals(view.getUrl())) {
                    SharedPreferences pref = SceneGet.this.mainActivity.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("moodleCookie",CookieManager.getInstance().getCookie("https://moodle.gym-voh.de"));
                    editor.apply();
                    handleBackButtonPress();
                }
            }
        });
        this.web.loadUrl("https://moodle.gym-voh.de/login/index.php");
    }
}
