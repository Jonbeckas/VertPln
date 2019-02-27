package net.ddns.tetraowl.vertpln.scenes;

import android.view.View;
import android.widget.ImageView;
import net.ddns.tetraowl.vertpln.MainActivity;
import net.ddns.tetraowl.vertpln.R;
import net.ddns.tetraowl.vertpln.scene_managing.SceneClass;

public class SceneStart extends SceneClass {
    MainActivity mainActivity;
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
