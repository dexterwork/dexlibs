package com.skyking.twgmod.object;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.skyking.play_module.MLog;

/**
 * Created by SkykingAndroid on 2017-09-22.
 */

public abstract class TimeUpdatedReceiver extends BroadcastReceiver {

    public TimeUpdatedReceiver register(Context context) {
        context.registerReceiver(this, new IntentFilter("android.intent.action.TIME_TICK"));
        return this;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;

        String action = intent.getAction();
        MLog.i(getClass().getSimpleName(),"action="+action);

        if(action.equals("android.intent.action.TIME_TICK"))onTimeUpdated();
    }

    protected abstract void onTimeUpdated();

}
