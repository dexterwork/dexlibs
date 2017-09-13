package com.skyking.play_module.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by SkykingAndroid on 2016/10/4.
 */

public abstract class NetworkStateReceiver extends BroadcastReceiver {


    public static final String RECEIVER_NAME = "android.net.conn.CONNECTIVITY_CHANGE";
//    public static final String RECEIVER_NAME = "android.net.wifi.WIFI_STATE_CHANGED";


    public NetworkStateReceiver register(Context context) {
        context.registerReceiver(this, new IntentFilter(RECEIVER_NAME));
        return this;
    }

    //    <uses-permission android:name="android.permission.INTERNET" />
//<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    public static String getWifiIPAddress(Context context) {
        WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ip);
    }

    public static String getMobileIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }

    public static String getIp(Context context) {
        if (isWifi(context)) return getWifiIPAddress(context);
        else return getMobileIPAddress();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
//        MLog.d("NetworkStateReceiver","Network connectivity change");

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        boolean isConnect = networkInfo != null && networkInfo.isConnected();
        if (!isConnect) {
            onDisconnect();
        } else {
            onNetworkChange(networkInfo, networkInfo.getType() == ConnectivityManager.TYPE_WIFI);
        }


//        if (intent.getExtras() != null) {
//            NetworkInfo ni = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
//            onNetworkChange(ni, ni != null && ni.getState() == NetworkInfo.State.CONNECTED);
//
//
////            if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
//////                MLog.i("NetworkStateReceiver", "Network " + ni.getTypeName() + " connected");
////
////            }
//        }
//        if (intent.getExtras().getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
////            MLog.d("NetworkStateReceiver", "There's no network connectivity");
//            onNetworkChange(null,false);
//        }
    }


    public abstract void onNetworkChange(NetworkInfo networkInfo, boolean isWifi);

    public abstract void onDisconnect();

    public static boolean isConnectedInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected() && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }
}
