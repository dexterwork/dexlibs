package com.skyking.play_module.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;

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


    //    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
//<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>



    public NetworkStateReceiver register(Context context) {
        IntentFilter filter = new IntentFilter(RECEIVER_NAME);
        filter.addAction(WifiManager.RSSI_CHANGED_ACTION);
//        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        context.registerReceiver(this, filter);
        return this;
    }


    /**
     * Wifi 訊號強度 0~4
     *
     * @param strength
     */
    public abstract void onStrengthChanged(int strength);

    public int getStrength(Context context) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        if (info.getBSSID() != null) {
            int strength = WifiManager.calculateSignalLevel(info.getRssi(), 5);
            // 链接速度
//			int speed = info.getLinkSpeed();
//			// 链接速度单位
//			String units = WifiInfo.LINK_SPEED_UNITS;
//			// Wifi源名称
//			String ssid = info.getSSID();
            return strength;
        }
        return 0;
    }


    public static int getWifiLevel(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int numberOfLevels = 5;

        //return 0 ~ 4
        return WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);
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
        if (intent == null) return;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        boolean isConnect = networkInfo != null && networkInfo.isConnected();
        if (!isConnect) {
            onDisconnect();
        } else {

            String action = intent.getAction();
            Log.i(getClass().getSimpleName(), "network action=" + (TextUtils.isEmpty(action)? "":action));
            boolean isWifi = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            switch (action) {
                case WifiManager.RSSI_CHANGED_ACTION:
                    if (isWifi) onStrengthChanged(getStrength(context));
                    break;
//            case WifiManager.NETWORK_STATE_CHANGED_ACTION:
//                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
//                boolean isConnect = info.getState().equals(NetworkInfo.State.CONNECTED);
//                if (isConnect) onNetworkStateChanged(info,info.getType() == ConnectivityManager.TYPE_WIFI);
//                else onDisconnect();
//                break;
//            case WifiManager.WIFI_STATE_CHANGED_ACTION:
//                //WIFI开关
//                int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);
//                onWifiStateChanged(wifistate == WifiManager.WIFI_STATE_ENABLED);
//                break;
                default:
                    onConnected(networkInfo, isWifi);
            }

        }


    }


    public abstract void onConnected(NetworkInfo networkInfo, boolean isWifi);

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
