package com.skyking.skylib.dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.skyking.skylib.R;
import com.skyking.skylib.basic.BasicActivity;
import com.skyking.skylib.tools.Tools;

/**
 * Created by SkykingAndroid on 2017/2/23.
 */

public abstract class UpdateVersionDialog {

    BasicActivity activity;
    String newVersion;

    public UpdateVersionDialog(BasicActivity activity, String newVersion) {
        this.activity = activity;
        this.newVersion = newVersion;
    }

    /**
     * 自動判斷是否需要更新
     */
    public void show() {
        if (TextUtils.isEmpty(newVersion)) return;
        String tVer = Tools.getVersionName(activity);
        if(newVersion.equals(tVer))return;

        String message = activity.getString(R.string.new_version);
        message += "\n\n目前版本：" + tVer;
        message += "\n新版本：" + newVersion;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton(activity.getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onCloseClick();
            }
        });
        builder.show();
    }

    public abstract void onCloseClick();
}
