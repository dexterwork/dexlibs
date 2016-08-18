package tools.gps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Dexter on 2016/8/18.
 */
public class Gps {

    AppCompatActivity activity;

    public Gps(AppCompatActivity activity) {
        this.activity = activity;
    }

    /**
     * 判斷GPS是否開啟，GPS或者AGPS開啟一個就認為是開啟的
     */
    public boolean isGpsAll() {

        LocationManager locationManager
                = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        // 通過GPS衛星定位，定位級別可以精確到街（通過24顆衛星定位，在室外和空曠的地方定位準確、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通過WLAN或移動網路(3G/2G)確定的位置（也稱作AGPS，輔助GPS定位。主要用於在室內或遮蓋物（建築群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return gps || network;
    }

    public boolean isGps() {
        LocationManager locationManager
                = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 跳至設定GPS頁面
     */
    public void toSetGpsPage() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivity(intent);
    }


    public void stopCheck() {
        gpsCheckerListener = null;
        if (gpsStatusReceiver != null) {
            activity.unregisterReceiver(gpsStatusReceiver);
            gpsStatusReceiver = null;
        }
    }

    boolean gpsStatus;

    private class GpsStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(LocationManager.PROVIDERS_CHANGED_ACTION)) {
                gpsStatus = isGps();
                sendStatus();
            }
        }
    }

    GpsStatusReceiver gpsStatusReceiver;

    public void ready(GpsCheckerListener gpsCheckerListener) {
        this.gpsCheckerListener = gpsCheckerListener;
        IntentFilter filter = new IntentFilter();
        filter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
        activity.registerReceiver(gpsStatusReceiver = new GpsStatusReceiver(), filter);

        gpsStatus = isGps();
        sendStatus();
    }

    private void sendStatus() {
        if (gpsStatus) gpsCheckerListener.onGpsOn();
        else gpsCheckerListener.onGpsOff();
    }

    GpsCheckerListener gpsCheckerListener;

}
