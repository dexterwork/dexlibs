package com.skyking.play_module;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

/**
 * 在操作播放相關控制後發送此receiver, 讓有註冊此receiver的地方處理相關動作
 * Created by SkykingAndroid on 2017-07-18.
 */

public class ControlReceiver extends BroadcastReceiver {
    public static final String RECEIVER_NAME = ControlReceiver.class.getSimpleName();

    public static final String KEY_ACTION = "action";
    public static final String KEY_MUTE = "mute";
    public static final String KEY_BUFFER = "buffer";
    public static final String KEY_SET_CHANNEL = "channel";
    public static final String KEY_PLAY = "play";


    public enum ControlAction {
        PREPARE, PLAY, MUTE, CANCEL_NOTIFICATION, BUFFER, SET_CHANNEL
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        Bundle bundle = intent.getExtras();
        if (bundle == null) return;

        ControlAction action = (ControlAction) bundle.getSerializable(KEY_ACTION);
        if (action == null) return;

        switch (action) {
            case PLAY:
                onPlay(bundle.getBoolean(KEY_PLAY), (ChData) bundle.getSerializable(KEY_SET_CHANNEL));
                break;
            case PREPARE:
                onPrepare();
                break;
            case MUTE:
                onMute(bundle.getBoolean(KEY_MUTE));
                break;
            case CANCEL_NOTIFICATION:
                cancelNotification();
                break;
            case BUFFER:
                buffer(bundle.getInt(KEY_BUFFER));
                break;
            case SET_CHANNEL:
                setChannel((ChData) bundle.getSerializable(KEY_SET_CHANNEL));
                break;
        }

    }

    /**
     * 按下 play 後在準備 source 之前可以先做的事情
     */
    protected void onPrepare() {
    }

    /**
     * 設定頻道資訊相關, 不含播放動作
     * @param chData
     */
    protected void setChannel(ChData chData) {
    }

    protected void buffer(int buffer) {
    }

    protected void onMute(boolean isMute) {
    }


    /**
     * 在 onPrepared 之後準備要播放前或播放後
     * @param isPlay
     * @param chData
     */
    protected void onPlay(boolean isPlay, ChData chData) {
    }


    protected void cancelNotification() {
    }


    public ControlReceiver register(Context context) {
        context.registerReceiver(this, new IntentFilter(RECEIVER_NAME));
        return this;
    }
}
