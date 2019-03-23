package net.ddns.tetraowl.vertpln;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.toddway.shelf.Shelf;

import java.io.File;


public class MoodleTricks{
    private MainActivity mainActivity;
    String cookie;
    String url;
    OnPageLoad load;

    public MoodleTricks(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Deprecated
    public String getMoodleCookie() {
        WebView webView = this.mainActivity.findViewById(R.id.html);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                Log.d("test",view.getUrl());
                if ("https://moodle.gym-voh.de/login/index.php".equals(view.getUrl())) {
                    view.loadUrl("javascript:(function() {"
                            +"u = document.getElementById('username');"
                            + "p = document.getElementById('password');"
                            + "b = document.getElementById('loginbtn');"
                            + "u.value = '"+getUsername()+"';"
                            + "p.value ='"+getPassword()+"';"
                            + "b.click();"
                            +"})()");
                }
                else {
                    MoodleTricks.this.cookie = CookieManager.getInstance().getCookie("https://moodle.gym-voh.de");
                }
            }
        });
        webView.loadUrl("https://moodle.gym-voh.de/login/index.php");
        return cookie;
    }

    public String getMoodleSite(WebView web, String url, OnPageLoad load) {
        this.load = load;
        this.url = url;
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String ourl) {
                super.onPageFinished(view, ourl);
                if ("https://moodle.gym-voh.de/login/index.php".equals(ourl)) {
                    view.loadUrl("javascript:(function() {"
                            +"u = document.getElementById('username');"
                            + "p = document.getElementById('password');"
                            + "b = document.getElementById('loginbtn');"
                            + "u.value = '"+getUsername()+"';"
                            + "p.value ='"+getPassword()+"';"
                            + "b.click();"
                            +"})()");
                } else if(ourl.equals(MoodleTricks.this.url)) {
                    MoodleTricks.this.load.onPageLoad();
                }
                else {
                    web.loadUrl(MoodleTricks.this.url);
                }
            }
        });
        return null;
    }


    public void setPassword(String password) {
        Shelf shelf = new Shelf(new File(this.mainActivity.getFilesDir(),"config"));
        shelf.item("password").put(password);
    }

    public void setUsername(String username) {
        Shelf shelf = new Shelf(new File(this.mainActivity.getFilesDir(),"config"));
        shelf.item("username").put(username);
    }
    public String getPassword() {
        Shelf shelf = new Shelf(new File(this.mainActivity.getFilesDir(),"config"));
        return shelf.item("password").get(String.class);
    }

    public String getUsername() {
        Shelf shelf = new Shelf(new File(this.mainActivity.getFilesDir(),"config"));
        return shelf.item("username").get(String.class);
    }

    public interface OnPageLoad {
        public void onPageLoad();
    }

}
