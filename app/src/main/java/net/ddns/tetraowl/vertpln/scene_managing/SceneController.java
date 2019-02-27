package net.ddns.tetraowl.vertpln.scene_managing;

import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.ViewGroup;
import net.ddns.tetraowl.vertpln.MainActivity;
import net.ddns.tetraowl.vertpln.design_tools.CloseQuestion;


public class SceneController {

    private ViewGroup root;
    private MainActivity activity;

    private SceneClass activeSceneClass;

    public SceneController(ViewGroup root, MainActivity activity) {
        this.root = root;
        this.activity = activity;
    }

    ViewGroup getRoot() {
        return root;
    }

    public MainActivity getActivity() {
        return activity;
    }

    public SceneClass getActiveSceneClass() {
        return activeSceneClass;
    }


    public void changeTo(SceneClass sceneClass, int transitionId) {
        Scene scene = Scene.getSceneForLayout(this.root, sceneClass.getLayoutId(), this.activity);
        if (transitionId != 0) {
            Transition transition = TransitionInflater.from(this.activity).inflateTransition(transitionId);
            TransitionManager.go(scene, transition);
        } else {
            TransitionManager.go(scene);
        }
        if (sceneClass.hasBeenCreated())
        {
            sceneClass.onResume(this);
        }
        else {
            sceneClass.onCreate(this);
        }
        if (this.activeSceneClass!=null) {
            this.activeSceneClass.setActive(false);
        }
        this.activeSceneClass = sceneClass;
    }

    public void handleBackButton(){
        if (this.activeSceneClass != null && !this.activeSceneClass.handleBackButtonPress()) {
            new CloseQuestion(this.activity);
        }
    }



}
