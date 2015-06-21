package com.awant.lion.location_manager;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.awant.lion.tools.MLog;
import com.awant.lion.tools.Settings;

import java.util.HashMap;
import java.util.List;

public class MLocation implements LocationListener {
  BasicLocationActivity activity;
    LocationManager locationManager;
    Location location;
    final int MIN_TIME = 1000;
    final int MIN_DISTANCE = 10;
    double latitude, longitude;
    String provider, changeStatus;
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";


    public MLocation(BasicLocationActivity activity) {
        this.activity = activity;
        init();
    }

    private void init() {
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);


//        provider = locationManager.getBestProvider(criteria, true);
        List<String> providerList;
        providerList = locationManager.getProviders(criteria, true);
        if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
            MLog.i(this, "dex provider list contains NETWORK.");
        } else {
//            turnOnGPS();
            provider = LocationManager.PASSIVE_PROVIDER;
            MLog.i(this, "dex provider list just PASSIVE.");
//            locationManager.requestLocationUpdates(provider, MIN_TIME, MIN_DISTANCE, this);
//            return;
        }
        provider = LocationManager.NETWORK_PROVIDER;

        MLog.i(this, "dex init provider=" + provider);
        locationManager.requestLocationUpdates(provider, MIN_TIME, MIN_DISTANCE, this);
    }

    public HashMap<String, Double> getLocation() {
        MLog.i(this, "dex getLocation......");
        if (Settings.DEVELOPE_MODE) checkProvider();
        changeStatus = "";
        if (location == null)
            location = locationManager.getLastKnownLocation(provider);
        if (location == null) {
            MLog.e(this, "dex location is null.(getLocation()) and will to turn on GPS.");
            turnOnGPS();
            return null;
        }
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        HashMap<String, Double> locationList = new HashMap<String, Double>();
        locationList.put(KEY_LATITUDE, latitude);
        locationList.put(KEY_LONGITUDE, longitude);
        return locationList;
    }

    public void turnOnGPS() {
        activity.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    private void checkProvider() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            MLog.v(this, "dex GPS_PROVIDER is true.");
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            MLog.v(this, "dex NETWORK_PROVIDER is true.");
        if (locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER))
            MLog.v(this, "dex PASSIVE_PROVIDER is true.");
    }

    public boolean hasProvider() {
        if (TextUtils.isEmpty(provider)) return false;
        return true;
    }

    public boolean hasLocation(Location location) {
        if (location == null) return false;
        return true;
    }

    public String getProvider() {
        return provider;
    }


    @Override
    public void onLocationChanged(Location location) {
        MLog.i(this, "dex onLocationChanged (has location=" + hasLocation(location) + ")");
        this.location = location;
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        String lat = String.valueOf(latitude);
        String lng = String.valueOf(longitude);
        MLog.d(this, "dex Lat:" + lat + "/Lng:" + lng + " (onLocationChanged)");
        this.changeStatus = "(onLocationChanged)";

        HashMap<String, Double> locationList = new HashMap<String, Double>();
        locationList.put(KEY_LATITUDE, latitude);
        locationList.put(KEY_LONGITUDE, longitude);
        activity.getLocationData(locationList);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        MLog.i(this, "dex onStatusChanged (provider=" + provider + "/status=" + status + ")");
        this.provider = provider;
    }

    @Override
    public void onProviderEnabled(String provider) {
        MLog.i(this, "dex onProviderEnabled (provider=" + provider + ")");
        this.provider = provider;
        locationManager.requestLocationUpdates(provider, 0, 0, this);
    }

    @Override
    public void onProviderDisabled(String provider) {
        MLog.i(this, "dex onProviderDisabled (provider=" + provider + ")");
        this.provider = null;
    }

    public String getChangeStatus() {
        return changeStatus;
    }

    public void close() {
        locationManager.removeUpdates(this);
    }
}
