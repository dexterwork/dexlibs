package com.skyking.play_module.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by SkykingAndroid on 2017-07-21.
 */

public abstract class BufferReceiver extends BroadcastReceiver {
    public static String KEY_BUFFER = "buffer";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        int buffer = intent.getIntExtra(KEY_BUFFER, 0);
        onBufferUpdate(buffer);
    }

    protected abstract void onBufferUpdate(int buffer);
}
