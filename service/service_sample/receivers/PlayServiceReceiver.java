package com.skyking.play_module.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by SkykingAndroid on 2017-07-21.
 */

public  class PlayServiceReceiver extends BroadcastReceiver {

    public final static String RECEIVER_NAME = PlayServiceReceiver.class.getSimpleName();

    public static final String KEY_CORE_INIT = "KEY_CORE_INIT";
    public static final String KEY_ACTION = "KEY_ACTION";
    public final static String KEY_NETWORK_STATUS = "KEY_NETWORK_STATUS";

    public final static int ACT_NETWORK_STATUS_CHANGED = 1000;
    public static final int ACT_CORE_INIT = 100;
    public static final int ACT_PLAYER_PREPARED = 10;
    public static final int ACT_PLAY_ERROR = 9;


    public PlayServiceReceiver register(Context context) {
        context.registerReceiver(this, new IntentFilter(RECEIVER_NAME));
        return this;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int act = intent.getIntExtra(KEY_ACTION, 0);
        switch (act) {
            case ACT_NETWORK_STATUS_CHANGED:
                boolean status = intent.getBooleanExtra(KEY_NETWORK_STATUS, false);
                onNetworkStatusChanged(status);
                break;
            case ACT_CORE_INIT:
                boolean isInitCore = intent.getBooleanExtra(KEY_CORE_INIT, false);
                onCoreInit(isInitCore);
                break;
            case ACT_PLAYER_PREPARED:
                onPlayerPrepared();
                break;
            case ACT_PLAY_ERROR:
                onPlayError();
                break;
        }
    }

    public void onNetworkStatusChanged(boolean isConnect) {
    }

    public void onCoreInit(boolean success) {
    }

    public void onPlayerPrepared() {
    }

    public void onPlayError() {
    }
}
