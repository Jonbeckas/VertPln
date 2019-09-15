package net.ddns.tetraowl.vertpln.design_tools;


import android.Manifest;
import android.app.DownloadManager;
import android.content.*;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import net.ddns.tetraowl.vertpln.MainActivity;
import net.ddns.tetraowl.vertpln.R;
import updater.UpdaterSettings;

import java.io.File;
import java.net.URL;

public class Updater {
    MainActivity mainActivity;
    URL url;
    public Updater(MainActivity mainActivity) {
        System.out.println("init updater");
        this.mainActivity = mainActivity;
        UpdaterSettings setting = new UpdaterSettings("jonbeckas","VertPln",this::onError,this::onSucces,false);
        new updater.Updater(setting);
    }

    private boolean isBigger(String version, String onversion) {
        String[] splitVers = version.split("\\.");
        String[] splitonVers = onversion.split("\\.");
        if (Integer.parseInt(splitonVers[0])<Integer.parseInt(splitVers[0])) {
            System.out.println("NEU: "+Integer.parseInt(splitonVers[0])+"ALT: "+Integer.parseInt(splitVers[0]));
            return false;
        } else if(Integer.parseInt(splitonVers[0])>Integer.parseInt(splitVers[0])) {
            return true;
        } else {
            if (Integer.parseInt(splitonVers[1])<Integer.parseInt(splitVers[1])) {
                System.out.println("NEU: "+Integer.parseInt(splitonVers[1])+"ALT: "+Integer.parseInt(splitVers[1]));
                return false;
            } else if(Integer.parseInt(splitonVers[1])>Integer.parseInt(splitVers[1])) {
                return true;
            } else {
                if (Integer.parseInt(splitonVers[2])<=Integer.parseInt(splitVers[2])) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    private void onSucces(String s, URL url) {
        this.mainActivity.runOnUiThread(() -> {
            if (isBigger(mainActivity.getString(R.string.vern),s)) {
                Toast.makeText(mainActivity, url.toString(), Toast.LENGTH_LONG).show();
                System.out.println(url.toString());
                Updater.this.url = url;
                AlertDialog.Builder build = new AlertDialog.Builder(Updater.this.mainActivity);
                build.setTitle("Update verfügbar");
                build.setMessage("Willst du auf eine neuere Table Version updaten?");
                build.setPositiveButton("Ja",Updater.this::positive);
                build.setNegativeButton("Nein",Updater.this::negative);
                AlertDialog message = build.create();
                message.show();
            }
        });

    }

    private void negative(DialogInterface dialogInterface, int i) { }

    private void positive(DialogInterface dialogInterface, int i) {
        new Download(this.url,this.mainActivity);
        //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(this.url.toString()));
        //mainActivity.startActivity(browserIntent);
    }

    private void onError(String s, Exception e) {
        e.printStackTrace();
    }

    private class Download {
        URL url;
        MainActivity main;
        public Download(URL url, MainActivity main) {
            this.url = url;
            this.main = main;
            if (Build.VERSION.SDK_INT >= 23) {
                if (this.main.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                } else {
                    ActivityCompat.requestPermissions(this.main, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }
            try {
                doDownload();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        private void doDownload() {
            //set downloadmanager
            String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
            Uri uri = Uri.parse("file://"+destination+"/tabler.apk");
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url.toString()));
            request.setDescription("download update");
            request.setTitle("Tabler");
            //set destination
            request.setDestinationUri(uri);

            // get download service and enqueue file
            final DownloadManager manager = (DownloadManager) this.main.getSystemService(Context.DOWNLOAD_SERVICE);
            final long downloadId = manager.enqueue(request);

            //set BroadcastReceiver to install app when .apk is downloaded
            BroadcastReceiver onComplete = new BroadcastReceiver() {
                public void onReceive(Context ctxt, Intent intent) {
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri apkURI = FileProvider.getUriForFile(
                                ctxt,
                                ctxt.getApplicationContext()
                                        .getPackageName() + ".provider", new File(uri.getPath()));
                        install.setDataAndType(apkURI, "application/vnd.android.package-archive");
                        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } else {
                        install.setDataAndType(uri, "application/vnd.android.package-archive");
                    }
                    Updater.this.mainActivity.startActivity(install);

                    Updater.this.mainActivity.unregisterReceiver(this);
                }
            };
            //register receiver for when .apk download is compete
            Updater.this.mainActivity.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        }
    }
}
