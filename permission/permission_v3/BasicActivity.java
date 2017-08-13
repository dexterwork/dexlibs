package dexterliu.studio.dexlibs.basic;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import dexterliu.studio.dexlibs.tools.permission.PermissionReceiver;

/**
 * Created by Dexter on 2017/7/28.
 */

public class BasicActivity extends AppCompatActivity {



    public PermissionReceiver permissionReceiver;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissionReceiver == null) return;
        Intent intent = new Intent(PermissionReceiver.RECEIVER_NAME);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            intent.putExtra(PermissionReceiver.KEY, true);
        } else {
            intent.putExtra(PermissionReceiver.KEY, false);
        }
        sendBroadcast(intent);
    }
}
