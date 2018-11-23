package twgood.com.play_module.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.text.TextUtils;

import twgood.com.floating_view.MLog;

public class BatteryStatusReceiver extends BroadcastReceiver {


    public BatteryStatusReceiver register(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_BATTERY_OKAY);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        context.registerReceiver(this, filter);
        return this;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent == null) return;
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) return;
        switch (action) {
            case Intent.ACTION_BATTERY_CHANGED:
                int level = intent.getIntExtra("level", 0);
                int scale = intent.getIntExtra("scale", 0);

                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL;
                MLog.v(BatteryStatusReceiver.class, "debug6 isCharging: " + isCharging);
                MLog.v(BatteryStatusReceiver.class, "debug6 battery level: " + level + " / " + scale + " isCharging: " + isCharging);

                onLevel(level);
                isCharging(isCharging);

                break;
            case Intent.ACTION_BATTERY_LOW:
                MLog.w(BatteryStatusReceiver.class, "debug6 ACTION_BATTERY_LOW");
                onLow();
                break;
            case Intent.ACTION_BATTERY_OKAY:
                MLog.i(BatteryStatusReceiver.class, "debug6 ACTION_BATTERY_OKAY");
                onOk();
                break;
            case Intent.ACTION_POWER_CONNECTED:
                MLog.i(BatteryStatusReceiver.class, "debug6 ACTION_POWER_CONNECTED");
                break;
            case Intent.ACTION_POWER_DISCONNECTED:
                MLog.i(BatteryStatusReceiver.class, "debug6 ACTION_POWER_DISCONNECTED");
                break;
        }
    }

    /**
     * 低電量
     */
    protected void onLow() {
    }

    /**
     * 高於低電量
     */
    protected void onOk() {
    }

    /**
     * 當前電量
     * @param level
     */
    protected void onLevel(int level) {
    }

    /**
     * 當開始充電時
     */
    protected void onPowerConnected() {
    }

    /**
     * 當移除充電時
     */
    protected void onPowerDisconnected() {
    }

    /**
     * 是否正在充電中
     * @param isCharging
     */
    protected void isCharging(boolean isCharging) {
    }


}
