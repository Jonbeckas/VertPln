package net.ddns.tetraowl.vertpln.scenes;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import com.toddway.shelf.Shelf;
import net.ddns.tetraowl.vertpln.MainActivity;
import net.ddns.tetraowl.vertpln.MoodleTricks;
import net.ddns.tetraowl.vertpln.R;
import net.ddns.tetraowl.vertpln.VertretungsplanTricks;
import net.ddns.tetraowl.vertpln.scene_managing.SceneClass;
import net.ddns.tetraowl.vertpln.service.BackgroundService;

import java.io.File;

public class SceneSettings extends SceneClass {
    MainActivity mainActivity;
    TextView username;
    TextView password;
    TextView clazz;
    MoodleTricks moodle;
    VertretungsplanTricks vtricks;
    Settings settings;
    Switch darkmode;
    @Override
    public int getLayoutId() {
        return R.layout.settings;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public boolean handleBackButtonPress() {
        save();
        super.getController().changeTo(new SceneStart(),R.transition.normal);
        return true;
    }
    public void save() {
        this.moodle.setPassword(this.password.getText().toString());
        this.moodle.setUsername(this.username.getText().toString());
        this.vtricks.setClass(this.clazz.getText().toString());
        this.mainActivity.stopService(new Intent(mainActivity, BackgroundService.class));
        this.mainActivity.startService(new Intent(mainActivity, BackgroundService.class));
        this.settings.setDarkmode(this.darkmode.isChecked());
        saveSettings(this.settings,this.mainActivity);
        if (this.settings.isDarkmode()) this.mainActivity.setTheme(R.style.AppThemeDark);
        if (!this.settings.isDarkmode()) this.mainActivity.setTheme(R.style.AppTheme);
    }

    public static Settings getSettings(Context context) {
        Shelf shelf = new Shelf(new File(context.getFilesDir(),"settings"));
        return shelf.item("settings").get(Settings.class);
    }
    public static void saveSettings(Settings settings, Context context) {
        Shelf shelf = new Shelf(new File(context.getFilesDir(),"settings"));
        shelf.item("settings").put(settings);
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
        TextView licence = this.mainActivity.findViewById(R.id.licences);
        licence.setOnClickListener(this::oLicence);
        this.darkmode = this.mainActivity.findViewById(R.id.darkmode);
        try {
            this.settings = getSettings(this.mainActivity);
        } catch (NullPointerException n) {
            this.settings = new Settings();
        }
        this.darkmode.setChecked(this.settings.darkmode);
    }

    private void oLicence(View view) {
        save();
        super.getController().changeTo(new SceneLicence(),R.transition.normal);
    }

    private void about(View view) {
        save();
        super.getController().changeTo(new SceneAbout(),R.transition.normal);
    }

    private void back(View view) {
        handleBackButtonPress();
    }

    public static class Settings {
        private boolean darkmode = true;

        public boolean isDarkmode() {
            return darkmode;
        }

        public void setDarkmode(boolean darkmode) {
            this.darkmode = darkmode;
        }
    }

}
