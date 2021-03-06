package net.ddns.tetraowl.vertpln;

import android.content.Context;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class VertretungsplanTricks {

    Context mainActivity;

    public VertretungsplanTricks(Context mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setOfflinePlanToday(String body) {
        Shelf shelf = new Shelf(new File(this.mainActivity.getCacheDir(),"sites"));
        shelf.item("today").put(body);
    }

    public void setOfflinePlanTomorrow(String body) {
        Shelf shelf = new Shelf(new File(this.mainActivity.getCacheDir(),"sites"));
        shelf.item("tomorrow").put(body);
    }

    public String getOfflinePlanTomorrow() {
        try {
            Shelf shelf = new Shelf(new File(this.mainActivity.getCacheDir(),"sites"));
            return  shelf.item("tomorrow").get(String.class);
        } catch (Exception e) {
            return "";
        }
    }

    public String getOfflinePlanToday() {
        try {
            Shelf shelf = new Shelf(new File(this.mainActivity.getCacheDir(),"sites"));
            return  shelf.item("today").get(String.class);
        } catch (Exception e) {
            return "";
        }

    }

    public void setClass(String clazz) {
        try{
            Shelf shelf = new Shelf(new File(this.mainActivity.getFilesDir(),"config"));
            shelf.item("class").put(clazz);
        }catch (Exception e) {
            //
        }

    }

    public String getClazz() {
        Shelf shelf = new Shelf(new File(this.mainActivity.getFilesDir(),"config"));
        return shelf.item("class").get(String.class);
    }
    public void saveObjects(List<VertObject> objects) {
        Shelf shelf = new Shelf(new File(this.mainActivity.getFilesDir(),"config"));
        shelf.item("hours").put(objects);

    }
    public List<VertObject> getObjects() {
        Shelf shelf = new Shelf(new File(this.mainActivity.getFilesDir(),"config"));
        return Arrays.asList(shelf.item("hours").get(VertObject[].class));
    }


   public List<VertObject> getHours(String s) throws IndexOutOfBoundsException {
        String text = s;
        Utils utils = new Utils();
       List<VertObject> objects = new ArrayList<VertObject>();
        Document html = Jsoup.parse(text,"UTF-8");
        Elements dates = html.getElementsByClass("mon_title");
        Element date = dates.get(0);
        VertObject dateObj = new VertObject();
        dateObj.setDate(date.text());
        objects.add(dateObj);
        Elements table = html.select("table");
        Elements rows = table.get(2).select("tr");
        for (Element rowz: rows) {
            Elements rowzz = rowz.select("td");
            if (rowzz.size() != 0) {
                List<String> array = rowzz.eachText();
                final Pattern pattern = Pattern.compile(getClazz(), Pattern.MULTILINE);
                final Matcher matcher = pattern.matcher(array.get(1));
                if (matcher.matches()) {
                    VertObject vobject = new VertObject();
                    vobject.setStunde(replaceIt(array.get(0)));
                    vobject.setLehrer(replaceIt(array.get(2)));
                    vobject.setWer(replaceIt(array.get(3)));
                    vobject.setRaum(replaceIt(array.get(4)));
                    vobject.setFach(replaceIt(array.get(5)));
                    try {
                        vobject.setBemerkung(replaceIt(array.get(6)) + "\n" + replaceIt(array.get(7)) + "\n" + replaceIt(array.get(8)));
                    } catch(IndexOutOfBoundsException e) {
                        //
                    }
                    objects.add(vobject);
                }

            }
        }
        saveObjects(objects);
        return objects;
    }
    private String replaceIt(String repl) {
       return repl.replaceAll("=C4","Ä").replaceAll("=","").replaceAll("<.*?>","");
    }
}
