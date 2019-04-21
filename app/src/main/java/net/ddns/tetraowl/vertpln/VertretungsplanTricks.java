package net.ddns.tetraowl.vertpln;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.ContentHandler;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class VertretungsplanTricks {

    Context mainActivity;

    public VertretungsplanTricks(Context mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setOfflinePlanToday(WebView web) {
        try {
            File file = new File(this.mainActivity.getCacheDir(),"today");
            web.saveWebArchive(file.getAbsolutePath()+".mht");
        }
        catch (Exception e) {
            Log.e("VERTPLN",e.toString());
        }
    }

    public void setOfflinePlanTomorrow(WebView web) {
        try {
            File file = new File(this.mainActivity.getCacheDir(),"tomorrow");
            web.saveWebArchive(file.getAbsolutePath()+".mht");
        }
        catch (Exception e) {
            Log.e("VERTPLN",e.toString());
        }

    }

    public void getOfflinePlanTomorrow(WebView web) {
        web.loadUrl("file://"+new File(this.mainActivity.getCacheDir(),"tomorrow").getAbsolutePath()+".mht");
    }

    public void getOfflinePlanToday(WebView web) {
        web.loadUrl("file://"+new File(this.mainActivity.getCacheDir(),"today").getAbsolutePath()+".mht");
    }

   public List<VertObject> getHours() {
        String text;
        Utils utils = new Utils();
        try{
            text = utils.FiletoString(new File(this.mainActivity.getCacheDir().getAbsolutePath(),"today.mht"));
        }
        catch(IOException e) {
            text = "";
       }

        Document html = Jsoup.parse(text);
        Elements table = html.select("table");
        Elements rows = table.get(2).select("tr");
        for (Element rowz: rows) {
            Elements rowzz = rowz.select("td");
        }
        return null;
    }
}
