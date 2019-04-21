package net.ddns.tetraowl.vertpln;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import io.sentry.Sentry;
import io.sentry.android.AndroidSentryClientFactory;
import net.ddns.tetraowl.vertpln.scene_managing.SceneController;
import net.ddns.tetraowl.vertpln.scenes.SceneStart;
import net.ddns.tetraowl.vertpln.service.BackgroundService;

public class MainActivity extends AppCompatActivity {

    private SceneController sceneController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Sentry.init("https://2d395deee5a64f1dbdd566e79deef27b@sentry.io/1401235", new AndroidSentryClientFactory(this.getApplicationContext()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewGroup root = findViewById(R.id.rootElement);
        this.sceneController = new SceneController(root, this);
        this.sceneController.changeTo(new SceneStart(),R.transition.normal);
        startService(new Intent(this, BackgroundService.class));
    }

    @Override
    public void onBackPressed() {
        if (this.sceneController != null) {
            this.sceneController.handleBackButton();
        }
    }
}
