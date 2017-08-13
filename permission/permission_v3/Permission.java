package dexterliu.studio.dexlibs.tools.permission;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import dexterliu.studio.dexlibs.basic.BasicActivity;
import dexterliu.studio.dexlibs.tools.MLog;

/**
 * Created by Dexter on 2017/8/13.
 */

public class Permission {

//Manifest.permission.WRITE_EXTERNAL_STORAGE

    public static boolean check(BasicActivity activity, String permission, PermissionReceiver receiver) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int per1 = ContextCompat.checkSelfPermission(activity, permission);
            if (per1 != PackageManager.PERMISSION_GRANTED) {
                activity.permissionReceiver = receiver;
                receiver.register(activity);
                ActivityCompat.requestPermissions(activity, new String[]{permission}, 0);
                return false;
            } else {
                return true;
            }
        }
        return true;
    }
}
