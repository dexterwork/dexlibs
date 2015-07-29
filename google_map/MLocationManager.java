package com.awant.lion.location_manager;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.awant.lion.tools.MLog;

/**
 * get location from Wifi/GPS
 */
public class MLocationManager implements LocationListener {
    Activity activity;
    private LocationManager locationMgr;

    public MLocationManager(Activity activity) {
        this.activity = activity;
        init();
    }

    private void init() {
        this.locationMgr = (LocationManager) activity.getSystemService(activity.LOCATION_SERVICE);
    }


    //call this method when activity after onResume.
    public void set() {
        // 取得位置提供者，不下條件，讓系統決定最適用者，true 表示生效的 provider
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW); // Chose your desired power consumption level.
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // Choose your accuracy requirement.
        criteria.setSpeedRequired(true); // Chose if speed for first location fix is required.
        criteria.setAltitudeRequired(false); // Choose if you use altitude.
        criteria.setBearingRequired(false); // Choose if you use bearing.
        criteria.setCostAllowed(false); // Choose if this provider can waste money :-)
        String provider = this.locationMgr.getBestProvider(criteria, true);
        if (provider == null) {
            MLog.w(this, "dex non location provider can to use.");
            return;
        }
        MLog.w(this, "dex 取得 provider - " + provider);


        // 註冊 listener，兩個 0 不適合在實際環境使用，太耗電
        this.locationMgr.requestLocationUpdates(provider, 0, 0, this);


        Location location = this.locationMgr.getLastKnownLocation(provider);
        if (location == null) {
            MLog.w(this, "dex 未取過 location");
            location = locationMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location == null) return;
        }
        MLog.w(this, "dex 取得上次的 location");
        this.onLocationChanged(location);
    }

    //call this method when activity onPause.
    public void onPause() {
        this.locationMgr.removeUpdates(this);
    }


    @Override
    public void onLocationChanged(Location location) {
        String msg = "經度: " + location.getLongitude() + ", 緯度: "
                + location.getLatitude();
        MLog.i(this, "dex get location : " + msg);
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


    @Override
    public void onProviderEnabled(String provider) {
    }


    @Override
    public void onProviderDisabled(String provider) {
    }

}
