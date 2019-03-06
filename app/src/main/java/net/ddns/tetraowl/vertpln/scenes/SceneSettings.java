package net.ddns.tetraowl.vertpln.scenes;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import net.ddns.tetraowl.vertpln.MainActivity;
import net.ddns.tetraowl.vertpln.MoodleTricks;
import net.ddns.tetraowl.vertpln.R;
import net.ddns.tetraowl.vertpln.scene_managing.SceneClass;

public class SceneSettings extends SceneClass {
    MainActivity mainActivity;
    TextView username;
    TextView password;
    MoodleTricks moodle;
    @Override
    public int getLayoutId() {
        return R.layout.settings;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public boolean handleBackButtonPress() {
        this.moodle.setPassword(password.getText().toString());
        this.moodle.setUsername(username.getText().toString());
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

    }

    private void about(View view) {
        super.getController().changeTo(new SceneAbout(),R.transition.normal);
    }

    private void back(View view) {
        handleBackButtonPress();
    }

}
