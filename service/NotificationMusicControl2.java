package objects;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.skyking.skykingradio.AudioService2;
import com.skyking.skykingradio.ControlReceiver;
import com.skyking.skykingradio.R;
import com.skyking.skykingradio.Settings;

import entitys.ChannelEntity;
import main.MainActivity;
import tools.GetInfo;
import tools.MLog;

/**
 * Created by SkykingAndroid on 2016/7/21.
 */
public class NotificationMusicControl2 {
    final String TAG = NotificationMusicControl2.class.getSimpleName();

    Context context;

    public static final int ID = 1;

    public NotificationMusicControl2(Context context) {
        this.context = context;
        ControlReceiver cr = new ControlReceiver() {

            @Override
            protected void onMute(boolean isMute) {
                remote.setImageViewResource(R.id.btn_mute, isMute ? Res.MUTE : Res.UN_MUTE);
                mNotificationManager.notify(ID, mBuilder.build());
            }


            @Override
            protected void onPlay(boolean isPlay) {
                MLog.d("NotificationMusicControl2", "isPlay=" + isPlay);
                remote.setImageViewResource(R.id.btn_play, isPlay ? Res.PAUSE : Res.PLAY);
                if (isPlay) {
                    ChannelEntity.Channels channel = AudioService2.channelBundle;
                    if (channel != null) remote.setTextViewText(R.id.tv_title, channel.name);
                }

                mBuilder.setOngoing(isPlay);
                notification = mBuilder.build();
                mNotificationManager.notify(ID, notification);
//                if (!isPlay) mNotificationManager.cancel(ID);//若為停止則消失
            }

            @Override
            protected void cancelNotification() {
                mNotificationManager.cancel(ID);
            }
        };
        context.registerReceiver(cr, new IntentFilter(ControlReceiver.TAG));
    }

    RemoteViews remote;
    NotificationManager mNotificationManager;
    public NotificationCompat.Builder mBuilder;
    public Notification notification;

    /**
     * 起動通知列播放控制面板, 並返回實體
     *
     * @return
     */
    public NotificationMusicControl2 notification() {
        if (!Settings.SHOW_NOTIFICATION) return null;
        try {
            //模擬器可能不支援, 所以返回
            MLog.i(TAG, "device id=" + GetInfo.getDeviceId(context));
            if (Long.parseLong(GetInfo.getDeviceId(context)) == 0) return null;
        } catch (NumberFormatException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            int icon = R.drawable.app_icon;
//                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        icon = R.drawable.smallicon_;
//                    }
            mBuilder = new NotificationCompat.Builder(context).setSmallIcon(icon);

            remote = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
            mBuilder.setContent(remote);


            Intent playIntent = new Intent(context, AudioService2.class);
            playIntent.setAction(String.valueOf(AudioService2.ACT_PLAY_CLICK_NOTIFICATION));
            PendingIntent pplayIntent = PendingIntent.getService(context, 0, playIntent, 0);
            remote.setOnClickPendingIntent(R.id.btn_play, pplayIntent);


            Intent muteIntent = new Intent(context, AudioService2.class);
            muteIntent.setAction(String.valueOf(AudioService2.ACT_MUTE));
            PendingIntent mIntent = PendingIntent.getService(context, 0, muteIntent, 0);
            remote.setOnClickPendingIntent(R.id.btn_mute, mIntent);

//                    Intent cancelIntent = new Intent(context, AudioService2.class);
//                    cancelIntent.setAction(String.valueOf(AudioService2.ACT_CANCEL_CLICK_NOTIFICATION));
//                    PendingIntent cancelPending = PendingIntent.getService(context, 0, cancelIntent, 0);
//                    remote.setOnClickPendingIntent(R.id.btn_cancel, cancelPending);


// Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(context, MainActivity.class);
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntent(resultIntent);
//                    stackBuilder.addNextIntentWithParentStack(resultIntent);
//                    PendingIntent resultPendingIntent =
//                            stackBuilder.getPendingIntent(
//                                    0,
//                                    PendingIntent.FLAG_UPDATE_CURRENT
//                            );
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pIntent);

            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mBuilder.setOngoing(Settings.REMOVE_NOTIFICATION);
            notification = mBuilder.build();
            mNotificationManager.notify(ID, notification);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }


}
