package net.ddns.tetraowl.vertpln;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MoodleTricks{
    private MainActivity mainActivity;
    public MoodleTricks(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    public String getMoodleCookie() {
        WebView webView = this.mainActivity.findViewById(R.id.htmlf);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                Log.d("test",view.getUrl());
                if ("https://moodle.gym-voh.de/login/index.php".equals(view.getUrl())) {
                    view.loadUrl("javascript:("
                            +"var u = document.getElementById('username');"
                            + "var p = document.getElementById('password');"
                            + "var b = document.getElementById('loginbtn');"
                            + "u.value = 'becker.jo';"
                            + "p.value ='jonibeck';"
                            + "b.click();"
                            +"})");
                    Log.d("test","vjdajdhsadk");
                }
                else {
                    SharedPreferences pref = mainActivity.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("moodleCookie", CookieManager.getInstance().getCookie("https://moodle.gym-voh.de"));
                    editor.apply();
                    Log.d("test", CookieManager.getInstance().getCookie("https://moodle.gym-voh.de")+view.getUrl() );
                }
            }
        });
        webView.loadUrl("https://moodle.gym-voh.de/login/index.php");
        return null;
    }

}
