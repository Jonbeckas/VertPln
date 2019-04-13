package net.ddns.tetraowl.vertpln.scenes;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import io.sentry.Sentry;
import net.ddns.tetraowl.vertpln.*;
import net.ddns.tetraowl.vertpln.scene_managing.SceneClass;

import java.util.ArrayList;
import java.util.List;

public class SceneStart extends SceneClass {
    MainActivity mainActivity;
    SwipeRefreshLayout swipe;
    @Override
    public int getLayoutId() {
        return R.layout.start;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public boolean handleBackButtonPress() {
        return false;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onBoth() {
        this.mainActivity = super.getController().getActivity();
        View today = this.mainActivity.findViewById(R.id.today);
        today.setOnClickListener(this::todayClick);
        View tomorrow = this.mainActivity.findViewById(R.id.tomorrow);
        tomorrow.setOnClickListener(this::tomorrowClick);
        ImageView settings = this.mainActivity.findViewById(R.id.iwSettings);
        settings.setOnClickListener(this::settingsClick);
        MoodleTricks moodle = new MoodleTricks(this.mainActivity);
        moodle.getMoodleCookie();
        this.swipe = this.mainActivity.findViewById(R.id.swipe);
        this.swipe.setOnRefreshListener(this::onRefresh);
        moodle.getMoodleSite(this.mainActivity.findViewById(R.id.html),"https://moodle.gym-voh.de/pluginfile.php/3952/mod_resource/content/4/schuelerheute.htm?embed=1",this::OnPageFinishes);
        List<VertObject> list;
        try {
            VertretungsplanTricks plan = new VertretungsplanTricks(this.mainActivity);
            list = plan.getHours();
        } catch (Exception e) {
            e.printStackTrace();
            Sentry.capture(e);
            VertObject vobject = new VertObject();
            vobject.setBemerkung("Es ist ein Fehler aufgetreten!");
            list = new ArrayList<VertObject>();
            list.add(vobject);
        }


        RecyclerView recyclerView = this.mainActivity.findViewById(R.id.recycle);


        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.mainActivity);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new DroppingView(list);
        recyclerView.setAdapter(mAdapter);
    }

    private void OnPageFinishes() {
        VertretungsplanTricks plan = new VertretungsplanTricks(this.mainActivity);
        plan.setOfflinePlanToday(this.mainActivity.findViewById(R.id.html));

    }

    private void onRefresh() {
        MoodleTricks moodle = new MoodleTricks(this.mainActivity);
        moodle.getMoodleSite(this.mainActivity.findViewById(R.id.html),"https://moodle.gym-voh.de/pluginfile.php/3952/mod_resource/content/4/schuelerheute.htm?embed=1",this::OnPageFinishes);
        List<VertObject> list;
        try {
            VertretungsplanTricks plan = new VertretungsplanTricks(this.mainActivity);
            list = plan.getHours();
        } catch (Exception e) {
            e.printStackTrace();
            Sentry.capture(e);
            VertObject vobject = new VertObject();
            vobject.setBemerkung("Es ist ein Fehler aufgetreten!");
            list = new ArrayList<VertObject>();
            list.add(vobject);
        }


        RecyclerView recyclerView = this.mainActivity.findViewById(R.id.recycle);


        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.mainActivity);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new DroppingView(list);
        recyclerView.setAdapter(mAdapter);
        this.swipe.setRefreshing(false);
    }

    private void settingsClick(View view) {
        super.getController().changeTo(new SceneSettings(),R.transition.normal);
    }

    private void tomorrowClick(View view) {
        super.getController().changeTo(new SceneTomorrow(),R.transition.normal);
    }

    private void todayClick(View view) {
        super.getController().changeTo(new SceneToday(),R.transition.normal);
    }
}
