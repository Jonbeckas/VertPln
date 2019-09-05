package net.ddns.tetraowl.vertpln.scenes;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import net.ddns.tetraowl.vertpln.LicenseAdapter;
import net.ddns.tetraowl.vertpln.MainActivity;
import net.ddns.tetraowl.vertpln.R;
import net.ddns.tetraowl.vertpln.scene_managing.SceneClass;

public class SceneLicence extends SceneClass {
    private MainActivity mainActivity;

    @Override
    public int getLayoutId() {
        return R.layout.licences;
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
        this.mainActivity.findViewById(R.id.back).setOnClickListener(this::onback);
        RecyclerView recyclerView = this.mainActivity.findViewById(R.id.licences);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.mainActivity);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new LicenseAdapter(this.mainActivity);
        recyclerView.setAdapter(mAdapter);
    }

    private void onback(View view) {
        handleBackButtonPress();
    }
}
