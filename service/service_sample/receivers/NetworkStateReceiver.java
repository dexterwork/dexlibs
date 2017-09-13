package com.skyking.play_module.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by SkykingAndroid on 2016/10/4.
 */

public abstract class NetworkStateReceiver extends BroadcastReceiver {


    public static final String RECEIVER_NAME = "android.net.conn.CONNECTIVITY_CHANGE";



    public void register(Context context) {
        context.registerReceiver(this, new IntentFilter(RECEIVER_NAME));
    }


    @Override
    public void onReceive(Context context, Intent intent) {
//        MLog.d("NetworkStateReceiver","Network connectivity change");
        if (intent.getExtras() != null) {
            NetworkInfo ni = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
            onNetworkChange(ni, ni != null && ni.getState() == NetworkInfo.State.CONNECTED);


//            if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
////                MLog.i("NetworkStateReceiver", "Network " + ni.getTypeName() + " connected");
//
//            }
        }
        if (intent.getExtras().getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
//            MLog.d("NetworkStateReceiver", "There's no network connectivity");
            onNetworkChange(null,false);
        }
    }


    public abstract void onNetworkChange(NetworkInfo networkInfo, boolean isConnect);

    public static boolean isConnectedInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}