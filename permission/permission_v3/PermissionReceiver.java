package dexterliu.studio.dexlibs.tools.permission;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by Dexter on 2017/8/13.
 */

public abstract class PermissionReceiver extends BroadcastReceiver {
    public static final String RECEIVER_NAME = PermissionReceiver.class.getSimpleName();
    public static final String KEY = "KEY";

    public void register(Context context) {
        context.registerReceiver(this, new IntentFilter(RECEIVER_NAME));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        boolean pass = intent.getBooleanExtra(KEY, false);
        if (pass) onPass();
        else onDeny();
        context.unregisterReceiver(this);
    }

    public abstract void onPass();

    public abstract void onDeny();
}
