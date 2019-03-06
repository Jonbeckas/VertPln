package net.ddns.tetraowl.vertpln.scenes;

import android.view.View;
import android.widget.ImageView;
import net.ddns.tetraowl.vertpln.MainActivity;
import net.ddns.tetraowl.vertpln.R;
import net.ddns.tetraowl.vertpln.scene_managing.SceneClass;

public class SceneAbout extends SceneClass {

    MainActivity mainActivity;

    @Override
    public int getLayoutId() {
        return R.layout.about;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public boolean handleBackButtonPress() {
        super.getController().changeTo(new SceneSettings(),R.transition.normal);
        return true;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onBoth() {
        this.mainActivity = super.getController().getActivity();
        ImageView back = this.mainActivity.findViewById(R.id.back);
        back.setOnClickListener(this::back);
    }

    private void back(View view) {
        handleBackButtonPress();
    }
}
