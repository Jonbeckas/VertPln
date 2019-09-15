package net.ddns.tetraowl.vertpln;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import com.toddway.shelf.Shelf;
import io.sentry.Sentry;
import io.sentry.android.AndroidSentryClientFactory;
import net.ddns.tetraowl.vertpln.design_tools.Updater;
import net.ddns.tetraowl.vertpln.scene_managing.SceneController;
import net.ddns.tetraowl.vertpln.scenes.SceneSettings;
import net.ddns.tetraowl.vertpln.scenes.SceneStart;
import net.ddns.tetraowl.vertpln.service.BackgroundService;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SceneController sceneController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.loading);
        BackgroundService backgroundService = new BackgroundService();
        Intent intent2 = new Intent(this,backgroundService.getClass());
        if (!isService(backgroundService.getClass())) {
            this.startService(intent2);
        }
        Sentry.init("https://2d395deee5a64f1dbdd566e79deef27b@sentry.io/1401235", new AndroidSentryClientFactory(this.getApplicationContext()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewGroup root = findViewById(R.id.rootElement);
        this.sceneController = new SceneController(root, this);
        new Thread(()->new Updater(this)).start();
        if (new MoodleTricks(this).getPassword() == null|| new MoodleTricks(this).getUsername()==null||new MoodleTricks(this).getPassword().equals("")||new MoodleTricks(this).getUsername().equals("")) {
            this.sceneController.changeTo(new SceneSettings(),R.transition.normal);
        } else {
            this.sceneController.changeTo(new SceneStart(),R.transition.normal);
        }
    }


    private boolean isService(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        if (this.sceneController != null) {
            this.sceneController.handleBackButton();
        }
    }
}
