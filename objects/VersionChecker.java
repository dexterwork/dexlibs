package objects;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import com.skyking.ifun_music.R;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import tools.Tools;

/**
 * Created by SkykingAndroid on 2017-09-07.
 */

public abstract class VersionChecker {

    Activity activity;
    String newVersion;


    public VersionChecker(Activity activity, String newVersion) {
        this.activity = activity;
        this.newVersion = newVersion;
        checkVersion();
    }

    protected abstract void done(boolean pass);

    private void checkVersion() {

        if (TextUtils.isEmpty(newVersion)) {
            done(true);
            return;
        }

        String appVersion = getVersionName();
        if(TextUtils.isEmpty(appVersion)){
            done(true);
            return;
        }

        if (appVersion.equals(newVersion)) {
            done(true);
            return;
        }

        TreeMap<String, String> map = new TreeMap<>();
        map.put(newVersion, newVersion);
        map.put(appVersion, appVersion);

        List<String> list = new ArrayList<>(map.keySet());
        String v = list.get(0);
        if (v.equals(newVersion)) {
            done(true);
            return;
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setMessage(R.string.new_app);//有新版本，至商店更新?
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                done(false);
            }
        });
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                toGooglePlay(activity);
                done(false);
            }
        });
        builder.show();
    }

    private void toGooglePlay(Activity activity) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + activity.getPackageName()));
            activity.startActivity(intent);
        } catch (Exception e) {
            String url = "https://play.google.com/store/apps/details?id=" + activity.getPackageName();
            new Tools().openUrlPage(activity, url);
        }
    }

    private String getVersionName() {
        try {
            return activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
