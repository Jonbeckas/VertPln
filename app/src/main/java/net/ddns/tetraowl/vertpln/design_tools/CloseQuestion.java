package net.ddns.tetraowl.vertpln.design_tools;

import android.app.AlertDialog;
import android.content.DialogInterface;
import net.ddns.tetraowl.vertpln.MainActivity;

public class CloseQuestion {
    MainActivity mainActivity;
    public CloseQuestion(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        AlertDialog.Builder build = new AlertDialog.Builder(this.mainActivity);
        build.setTitle("Sorry");
        build.setMessage("Willst du die App wirklich verlassen?");
        build.setPositiveButton("Yes",this::positive);
        build.setNegativeButton("No",this::negative);
        AlertDialog message = build.create();
        message.show();
    }

    private void positive(DialogInterface dialogInterface, int i){
        System.exit(0);
    }

    private void negative(DialogInterface dialogInterface, int i) {
    }
}
