package com.skyking.skylib.permission_v3;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dexter on 2017/8/13.
 */

public abstract class PermissionReceiver extends BroadcastReceiver {
    public static final String RECEIVER_NAME = PermissionReceiver.class.getSimpleName();
    public static final String KEY = "KEY";

    private void register(Context context) {
        context.registerReceiver(this, new IntentFilter(RECEIVER_NAME));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        boolean pass = intent.getBooleanExtra(KEY, false);
        context.unregisterReceiver(this);
        if (pass) onPass();
        else onDeny();
    }


    public void check(Activity activity, String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onPass();
            return;
        }
        int per = ContextCompat.checkSelfPermission(activity, permission);
        if (per == PackageManager.PERMISSION_GRANTED) {
            onPass();
            return;
        }
        register(activity);
        ActivityCompat.requestPermissions(activity, new String[]{permission}, 0);
    }
	
	public static boolean checkSelf(Activity activity, String permission){
        int per = ContextCompat.checkSelfPermission(activity, permission);
        return per == PackageManager.PERMISSION_GRANTED;
    }
	

//    //in activity
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        Intent intent = new Intent(PermissionReceiver.RECEIVER_NAME);
//        if (grantResults.length > 0
//                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            intent.putExtra(PermissionReceiver.KEY, true);
//        } else {
//            intent.putExtra(PermissionReceiver.KEY, false);
//        }
//        sendBroadcast(intent);
//    }
	
    public abstract void onPass();

    public abstract void onDeny();
}
