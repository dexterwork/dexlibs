package com.skyking.aline;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.skyking.play_module.BasicRemoteView;
import com.skyking.play_module.ChData;
import com.skyking.play_module.ControlReceiver;
import com.skyking.play_module.MLog;
import com.skyking.play_module.PlayService;

/**
 * Created by SkykingAndroid on 2017-08-28.
 */

public class NotifyView extends BasicRemoteView {
	
	 // new this
	 //PlayService.remoteView = new NotifyView(activity.getApplicationContext());
	
    final String TAG=NotifyView.class.getSimpleName();

    public NotifyView(Context context) {
        super(context,  R.layout.remote_layout, R.drawable.app_icon, 100);
    }
    ControlReceiver controlReceiver;

    @Override
    protected void setAction() {
        setAction(R.id.btnPlay, PlayService.ACT_NOTIFICATION_PLAY);
        setAction(R.id.btnMute, PlayService.ACT_NOTIFICATION_MUTE);
        setStartActivity(MainActivity.class);

      if(controlReceiver==null)  controlReceiver = new ControlReceiver() {
            @Override
            protected void onMute(boolean isMute) {
                MLog.d(TAG, "isMute " + isMute);
                ChData chData = PlayService.chData;
                if (!chData.channelType.equals("RADIO")) return;

                setImageViewResource(R.id.btnMute, isMute ? R.drawable.n_mute : R.drawable.n_unmute);
                getManager().notify(ID, notification);
            }

            @Override
            protected void onPrepare() {
                ChData chData = PlayService.chData;
                if (chData == null) return;
                if (!chData.channelType.equals("RADIO")) return;
                setViewVisibility(R.id.btnPlay, View.GONE);
                setViewVisibility(R.id.progressBar, View.VISIBLE);
                getManager().notify(ID, notification);
            }

            @Override
            protected void onPlay(boolean isPlay, ChData chData) {
                MLog.d(TAG, "onPlay " + isPlay);
                if (chData == null) return;
                if (!chData.channelType.equals("RADIO")) return;

                setViewVisibility(R.id.btnPlay, View.VISIBLE);
                setViewVisibility(R.id.progressBar, View.GONE);

                setImageViewResource(R.id.btnPlay, isPlay ? R.drawable.n_pause : R.drawable.n_play);
                getManager().notify(ID, notification);
                if (isPlay) setChannel(chData);
            }

            @Override
            protected void cancelNotification() {
                MLog.d(TAG, "cancelNotification");
                dismiss();
            }

            @Override
            protected void setChannel(ChData chData) {
                MLog.d(TAG, "setChannel");
                if (chData == null) return;
                if (!chData.channelType.equals("RADIO")) return;
                String name = TextUtils.isEmpty(chData.name) ? "" : chData.name;
                setTextViewText(R.id.tvName, name);
                getManager().notify(ID, notification);
            }
        }.register(context);
    }
}
