package com.skyking.testapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by Dexter on 2017-08-25.
 */

public abstract class NetworkStateReceiver2 extends BroadcastReceiver {
    //    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
//<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>

    public NetworkStateReceiver2 register(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        context.registerReceiver(this, filter);
        return this;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;

        String action = intent.getAction();
        Log.i(getClass().getSimpleName(), "master action=" + action);
        switch (action) {
            case WifiManager.RSSI_CHANGED_ACTION:
                onStrengthChanged(getStrength(context));
                break;
            case WifiManager.NETWORK_STATE_CHANGED_ACTION:
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                onNetworkStateChanged(info.getState().equals(NetworkInfo.State.CONNECTED));
                break;
            case WifiManager.WIFI_STATE_CHANGED_ACTION:
                //WIFI开关
                int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);
                onWifiStateChanged(wifistate == WifiManager.WIFI_STATE_ENABLED);
                break;
        }
    }

    public abstract void onNetworkStateChanged(boolean isConnect);

    public void onWifiStateChanged(boolean isConnect) {
    }

    /**
     * Wifi 訊號強度 0~5
     *
     * @param strength
     */
    public void onStrengthChanged(int strength) {
    }

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
	public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCountry(Context context){
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telManager.getNetworkCountryIso();
    }

    public static void telManager(Context context) {
//        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

// 手機號碼
//        String lineNumber = telManager.getLine1Number();
//
//// 手機 IMEI
//        String imei = telManager.getDeviceId();
//
//// 手機 IMSI
//        String imsi = telManager.getSubscriberId();

// 手機漫遊狀態
//        String roamingStatus = telManager.isNetworkRoaming() ? "漫遊中" : "非漫遊";

// 電信網路國別
//        String country = telManager.getNetworkCountryIso();

// 電信公司代號
//        String operator = telManager.getNetworkOperator();

// 電信公司名稱
//        String operatorName = telManager.getNetworkOperatorName();

// 行動網路類型
//        String[] networkTypeArray = {"UNKNOWN", "GPRS", "EDGE", "UMTS", "CDMA", "EVDO 0", "EVDO A", "1xRTT", "HSDPA", "HSUPA", "HSPA"};
//        String networkType = networkTypeArray[telManager.getNetworkType()];

// 行動通訊類型
//        String[] phoneTypeArray = {"NONE", "GSM", "CDMA"};
//        String phoneType = phoneTypeArray[telManager.getPhoneType()];
    }
}
