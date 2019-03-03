package net.ddns.tetraowl.vertpln.scenes;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import net.ddns.tetraowl.vertpln.MainActivity;
import net.ddns.tetraowl.vertpln.MoodleTricks;
import net.ddns.tetraowl.vertpln.R;
import net.ddns.tetraowl.vertpln.scene_managing.SceneClass;

public class SceneSettings extends SceneClass {
    MainActivity mainActivity;
    @Override
    public int getLayoutId() {
        return R.layout.settings;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public boolean handleBackButtonPress() {
        SharedPreferences pref = this.mainActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.apply();
        super.getController().changeTo(new SceneStart(),R.transition.normal);
        return true;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onBoth() {
        this.mainActivity = super.getController().getActivity();
        Button buttonGet = this.mainActivity.findViewById(R.id.getID);
        buttonGet.setOnClickListener(this::getID);
        SharedPreferences pref = this.mainActivity.getPreferences(Context.MODE_PRIVATE);
        String sessionId = pref.getString("sessionId",null);
    }

    private void getID(View view) {
        MoodleTricks moodle = new MoodleTricks(this.mainActivity);
        moodle.getMoodleCookie();
    }
}
