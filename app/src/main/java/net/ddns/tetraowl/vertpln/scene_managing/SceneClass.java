package net.ddns.tetraowl.vertpln.scene_managing;

import android.transition.Scene;

public abstract class SceneClass {

    public abstract int getLayoutId();
    public abstract void onCreate();
    public abstract boolean handleBackButtonPress();
    public abstract void onResume();
    public abstract void onBoth();

    private boolean hasBeenCreated = false;

    private boolean isActive =false;
    private Scene scene;
    private SceneController controller;

    Scene getScene() {
        return this.scene;
    }

    protected SceneController getController() {
        return this.controller;
    }

    //Ment to be used from the outside classes
    public void onCreate(SceneController controller) {
        this.controller = controller;
        onCreate();
        onBoth();
        this.isActive = true;
        this.hasBeenCreated = true;
    }

    public void onResume(SceneController controller)
    {
        this.controller = controller;
        onResume();
        onBoth();
        this.isActive = true;
    }


    public boolean hasBeenCreated() {
        return this.hasBeenCreated;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

}
