package net.ddns.tetraowl.vertpln;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.toddway.shelf.Shelf;

import java.io.File;


public class MoodleTricks{
    private Context mainActivity;
    String cookie;
    String url;
    OnPageLoad load;

    public MoodleTricks(Context mainActivity) {
        this.mainActivity = mainActivity;
    }



    @SuppressLint("SetJavaScriptEnabled")
    public void getMoodleSite(WebView web, String url, OnPageLoad load) {
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
                    web.loadUrl(url);
                }
            }
        });
        web.loadUrl(url);
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
