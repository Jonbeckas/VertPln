package net.ddns.tetraowl.vertpln;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.webkit.CookieManager;
import androidx.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.toddway.shelf.Shelf;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class MoodleTricks{
    private Context mainActivity;
    String cookie;
    String url;
    OnPageLoad load;
    private WebViewClient wc;
    private boolean firstTime = false;

    public MoodleTricks(Context mainActivity) {
        this.mainActivity = mainActivity;
    }

    private HashMap<String,String> getLoginData() throws IOException {
        Connection.Response res = Jsoup.connect("https://moodle.gym-voh.de/login/index.php").method(Connection.Method.GET).execute();
        Document doc = res.parse();
        Elements inputs = doc.select("input");
        Element tokenElement = inputs.get(1);
        HashMap<String,String> map = new HashMap();
        map.put("cookie",res.cookie("MoodleSession"));
        map.put("token",tokenElement.attr("value"));
        return map;
    }
    private String getCookie() {
        if(getPassword()==null||getUsername()==null) {
            return null;
        }
        try {
            HashMap<String,String> data = getLoginData();
            Connection.Response doc = Jsoup.connect("https://moodle.gym-voh.de/login/index.php").data("logintoken",data.get("token")).data("username",getUsername()).data("password",getPassword()).userAgent("Mozilla").cookie("MoodleSession",data.get("cookie")).method(Connection.Method.POST).execute();
            //System.out.println("HTML"+doc.body());
            //System.out.println("TAT: "+data.get("cookie"));
            //System.out.println("TST: "+doc.cookie("MoodleSession"));
            //System.out.println("TET: "+data.get("token"));
            return doc.cookie("MoodleSession");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void getMoodleSite(String url, OnPageLoad load) throws IOException,IllegalArgumentException {
        Connection connection;
        Connection.Response resp =Jsoup.connect(url).cookie("MoodleSession",getCookie()).method(Connection.Method.GET).execute();
        load.onPageLoad(resp.body());
        /*this.load = load;
        this.url = url;
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie("MoodleSession",getCookie());
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setJavaScriptEnabled(true);
        this.wc = new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String ourl) {
                //super.onPageFinished(view, ourl);
                System.out.println("DO"+getPassword()+";"+getUsername());
                if (MoodleTricks.this.firstTime) {
                    return;
                }
                if ("https://moodle.gym-voh.de/login/index.php".equals(view.getUrl())) {
                    view.loadUrl("javascript:(function() {"
                            +"u = document.getElementById('username');"
                            + "p = document.getElementById('password');"
                            + "b = document.getElementById('loginbtn');"
                            + "u.value = '"+getUsername()+"';"
                            + "p.value ='"+getPassword()+"';"
                            + "b.click();"
                            +"})()");
                    MoodleTricks.this.firstTime = true;
                } else if(view.getUrl().equals(MoodleTricks.this.url)) {
                    MoodleTricks.this.load.onPageLoad();
                }
                else {
                    System.out.println("REDO");
                    web.loadUrl(url);
                }
            }
        };

        web.setWebViewClient(this.wc);
        web.loadUrl(url);*/
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
        public void onPageLoad(String body);
    }

}
