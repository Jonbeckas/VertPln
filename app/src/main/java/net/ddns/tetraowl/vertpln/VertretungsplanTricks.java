package net.ddns.tetraowl.vertpln;

import android.util.Log;
import android.webkit.WebView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.List;

public class VertretungsplanTricks {

    MainActivity mainActivity;

    public VertretungsplanTricks(MainActivity mainActivity) {
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
        Document html = Jsoup.parse("file://"+new File(this.mainActivity.getCacheDir(),"today").getAbsolutePath()+".mht");
        Elements table = html.select("table");
        Elements rows = table.get(1).select("tr");
        for (Element rowz: rows) {
            Element rowzz = rowz;

        }
        return null;
    }
}
