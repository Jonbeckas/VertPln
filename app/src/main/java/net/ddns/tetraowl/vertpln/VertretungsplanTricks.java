package net.ddns.tetraowl.vertpln;

import android.util.Log;
import android.webkit.WebView;

import com.toddway.shelf.Shelf;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



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

    public void setClass(String clazz) {
        Shelf shelf = new Shelf(new File(this.mainActivity.getFilesDir(),"config"));
        shelf.item("class").put(clazz);
    }

    public String getClazz() {
        Shelf shelf = new Shelf(new File(this.mainActivity.getFilesDir(),"config"));
        return shelf.item("class").get(String.class);
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
       List<VertObject> objects = new ArrayList<VertObject>();

        Document html = Jsoup.parse(text);
        Elements table = html.select("table");
        Elements rows = table.get(2).select("tr");
        for (Element rowz: rows) {
            Elements rowzz = rowz.select("td");
            if (rowzz.size() != 0) {
                System.out.println(rowzz);
                List<String> array = rowzz.eachText();
                final Pattern pattern = Pattern.compile(getClazz(), Pattern.MULTILINE);
                final Matcher matcher = pattern.matcher(array.get(1));
                if (matcher.matches()) {
                    VertObject vobject = new VertObject();
                    vobject.setStunde(array.get(0));
                    vobject.setLehrer(array.get(2));
                    vobject.setWer(array.get(3));
                    vobject.setRaum(array.get(4));
                    vobject.setFach(array.get(5));
                    try {
                        vobject.setBemerkung(array.get(6) + "\n" + array.get(7) + "\n" + array.get(8));
                    } catch(IndexOutOfBoundsException e) {
                        //
                    }
                    objects.add(vobject);
                }

            }
        }
        return objects;
    }
}
