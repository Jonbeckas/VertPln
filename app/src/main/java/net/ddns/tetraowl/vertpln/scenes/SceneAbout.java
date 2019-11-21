package net.ddns.tetraowl.vertpln.scenes;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import net.ddns.tetraowl.vertpln.BuildConfig;
import net.ddns.tetraowl.vertpln.ClipboardUtils;
import net.ddns.tetraowl.vertpln.MainActivity;
import net.ddns.tetraowl.vertpln.R;
import net.ddns.tetraowl.vertpln.scene_managing.SceneClass;

public class SceneAbout extends SceneClass {

    private MainActivity mainActivity;

    @Override
    public int getLayoutId() {
        return R.layout.about;
    }

    @Override
    public void onCreate() {
        this.mainActivity = super.getController().getActivity();
        TextView build =this.mainActivity.findViewById(R.id.build);
        build.setText(BuildConfig.BUILD_TIME);
        LinearLayout buildframe = this.mainActivity.findViewById(R.id.buildframe);
        buildframe.setOnClickListener(view->{
            ClipboardUtils.copyToClipboard(super.getController().getActivity(),"Buildnummer",build.getText());
            Toast.makeText(this.mainActivity,"In die Zwischenablage kopiert",Toast.LENGTH_LONG).show();
        });
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
