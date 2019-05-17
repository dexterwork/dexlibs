package com.twgood.base.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;


/**
 * 時間以每分鐘為單位, 更新時通知
 * Created by SkykingAndroid on 2017-09-22.
 */

public abstract class TimeUpdatedReceiver extends BroadcastReceiver {

    public TimeUpdatedReceiver register(Context context, String from) {
        context.registerReceiver(this, new IntentFilter(Intent.ACTION_TIME_TICK));
//        context.registerReceiver(this, new IntentFilter("android.intent.action.TIME_TICK"));
        return this;
    }

    public void unregister(Context context) {
        try {
            context.unregisterReceiver(this);
        } catch (IllegalArgumentException e) {
        } catch (RuntimeException e) {
        } catch (Exception e) {
        }
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;

        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) return;
        if (action.equals(Intent.ACTION_TIME_TICK)) {
			//每一分鐘來一次
           
            onTimeUpdated();
        }
    }

 

    protected abstract void onTimeUpdated();

}
