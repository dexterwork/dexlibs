package tools.gps;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import tools.MLog;


/**
 * Created by Dexter on 2016/8/18.
 */
public class Gps implements LocationListener {

    AppCompatActivity activity;
    LocationManager locationManager;

    public Gps(AppCompatActivity activity) {
        this.activity = activity;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * 判斷GPS是否開啟，GPS或者AGPS開啟一個就認為是開啟的
     */
    public boolean isGpsAll() {
        // 通過GPS衛星定位，定位級別可以精確到街（通過24顆衛星定位，在室外和空曠的地方定位準確、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通過WLAN或移動網路(3G/2G)確定的位置（也稱作AGPS，輔助GPS定位。主要用於在室內或遮蓋物（建築群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return gps || network;
    }

    public boolean isGps() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 跳至設定GPS頁面
     */
    public void toSetGpsPage() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivity(intent);
    }


    public void onPause() {
        gpsCheckerListener = null;
//        if (gpsStatusReceiver != null) {
//            activity.unregisterReceiver(gpsStatusReceiver);
//            gpsStatusReceiver = null;
//        }
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);   //離開頁面時停止更新
    }

    boolean gpsStatus;
    String bestProvider = LocationManager.GPS_PROVIDER;

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

//    GpsStatusReceiver gpsStatusReceiver;

    public void ready(GpsCheckerListener gpsCheckerListener) {
        this.gpsCheckerListener = gpsCheckerListener;
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
//        activity.registerReceiver(gpsStatusReceiver = new GpsStatusReceiver(), filter);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(bestProvider, 1000, 1, this);
        gpsStatus = isGps();
        sendStatus();
    }

    private void sendStatus() {
        if (gpsStatus) gpsCheckerListener.onGpsOn();
        else gpsCheckerListener.onGpsOff();
    }

    GpsCheckerListener gpsCheckerListener;

    public void getLocation() {
        Criteria criteria = new Criteria();  //資訊提供者選取標準
        bestProvider = locationManager.getBestProvider(criteria, true);    //選擇精準度最高的提供者
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(activity, "請至設定頁開啟定位所需權限", Toast.LENGTH_LONG).show();
            return;
        }
        Location location = locationManager.getLastKnownLocation(bestProvider);
        getLocation(location);
    }

    private void getLocation(Location location) {
        if (location != null) {
            Double longitude = location.getLongitude();   //取得經度
            Double latitude = location.getLatitude();     //取得緯度
            MLog.i(this, "dexter longitude=" + longitude + "/latitude=" + latitude);
            gpsCheckerListener.sendLocation(longitude, latitude);
        } else {
            Toast.makeText(activity, "無法定位座標", Toast.LENGTH_LONG).show();
            gpsCheckerListener.noLocation();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        MLog.w(this, "dexter onProviderDisabled provider=" + provider);
        gpsCheckerListener.onGpsOff();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        MLog.w(this, "dexter onStatusChanged provider=" + provider + "/status=" + status);
        //Toast.makeText(activity, "onStatusChanged provider=" + provider + "/status=" + status, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        MLog.w(this, "dexter onProviderEnabled provider=" + provider);
        gpsCheckerListener.onGpsOn();
    }

    @Override
    public void onLocationChanged(Location location) {
        getLocation(location);
    }
}
