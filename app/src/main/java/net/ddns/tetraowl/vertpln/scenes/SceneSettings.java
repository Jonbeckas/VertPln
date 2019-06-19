package net.ddns.tetraowl.vertpln.scenes;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import net.ddns.tetraowl.vertpln.MainActivity;
import net.ddns.tetraowl.vertpln.MoodleTricks;
import net.ddns.tetraowl.vertpln.R;
import net.ddns.tetraowl.vertpln.VertretungsplanTricks;
import net.ddns.tetraowl.vertpln.scene_managing.SceneClass;

public class SceneSettings extends SceneClass {
    MainActivity mainActivity;
    TextView username;
    TextView password;
    TextView clazz;
    MoodleTricks moodle;
    VertretungsplanTricks vtricks;
    @Override
    public int getLayoutId() {
        return R.layout.settings;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public boolean handleBackButtonPress() {
        this.moodle.setPassword(this.password.getText().toString());
        this.moodle.setUsername(this.username.getText().toString());
        this.vtricks.setClass(this.clazz.getText().toString());
        super.getController().changeTo(new SceneStart(),R.transition.normal);
        return true;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onBoth() {
        this.mainActivity = super.getController().getActivity();
        this.password = this.mainActivity.findViewById(R.id.password);
        this.username = this.mainActivity.findViewById(R.id.username);
        ImageView back = this.mainActivity.findViewById(R.id.back);
        back.setOnClickListener(this::back);
        this.moodle = new MoodleTricks(this.mainActivity);
        this.password.setText(moodle.getPassword());
        this.username.setText(moodle.getUsername());
        TextView about = this.mainActivity.findViewById(R.id.about);
        about.setOnClickListener(this::about);
        this.vtricks = new VertretungsplanTricks(this.mainActivity);
        this.clazz = this.mainActivity.findViewById(R.id.klasse);
        this.clazz.setText(vtricks.getClazz());
    }

    private void about(View view) {
        handleBackButtonPress();
        super.getController().changeTo(new SceneAbout(),R.transition.normal);
    }

    private void back(View view) {
        handleBackButtonPress();
    }

}
