package com.skyking.skylib.dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.skyking.skylib.R;

import com.skyking.skylib.basic.BasicActivity;

/**
 * Created by SkykingAndroid on 2017/2/22.
 */

public abstract class MessageDialog  {
    BasicActivity activity;

    public MessageDialog(BasicActivity activity) {
        this.activity = activity;
    }

    public void show(String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton(activity.getString(R.string.know), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                done();
            }
        });
        builder.show();
    }
    protected abstract void done();
}
