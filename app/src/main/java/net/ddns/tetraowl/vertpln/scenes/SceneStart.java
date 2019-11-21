package net.ddns.tetraowl.vertpln.scenes;

import android.os.CountDownTimer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import io.sentry.Sentry;
import net.ddns.tetraowl.vertpln.*;
import net.ddns.tetraowl.vertpln.scene_managing.SceneClass;

import java.util.ArrayList;
import java.util.List;

public class SceneStart extends SceneClass {
    private FrameLayout load;
    MainActivity mainActivity;
    SwipeRefreshLayout swipe;
    CountDownTimer cd;
    @Override
    public int getLayoutId() {
        return R.layout.start;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public boolean handleBackButtonPress() {
        this.cd.cancel();
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
        this.swipe = this.mainActivity.findViewById(R.id.swipe);
        this.load = mainActivity.findViewById(R.id.load);
        this.swipe.setOnRefreshListener(this::onRefresh);
        check();
    }

    private void check() {
        List<VertObject> list;
        try {
            VertretungsplanTricks plan = new VertretungsplanTricks(SceneStart.this.mainActivity);
            list = plan.getHours(plan.getOfflinePlanToday());
        } catch (Exception e) {
            e.printStackTrace();
            VertObject vobject = new VertObject();
            vobject.setLoading(true);
            list = new ArrayList<VertObject>();
            list.add(vobject);
            this.cd = new CountDownTimer(5000,1000) {

                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {

                    check();
                }
            };
            this.cd.start();
        }
        try {
            RecyclerView recyclerView = SceneStart.this.mainActivity.findViewById(R.id.recycle);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SceneStart.this.mainActivity);
            recyclerView.setLayoutManager(layoutManager);
            RecyclerView.Adapter mAdapter = new DroppingView(list);
            recyclerView.setAdapter(mAdapter);
            SceneStart.this.load.setVisibility(View.GONE);
        } catch (NullPointerException e){
            //
        }
    }

    private void onRefresh() {
        MoodleTricks moodle = new MoodleTricks(this.mainActivity);
        this.mainActivity.getBackgroundService().getNewestPlan(true);
        //moodle.getMoodleSite(this.mainActivity.findViewById(R.id.html),"https://moodle.gym-voh.de/pluginfile.php/3952/mod_resource/content/4/schuelerheute.htm?embed=1",this::OnPageFinishes);
        check();
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
