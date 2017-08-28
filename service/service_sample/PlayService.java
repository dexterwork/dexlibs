package com.skyking.play_module;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.VideoView;

import com.skyking.play_module.receivers.BufferReceiver;
import com.skyking.play_module.receivers.NetworkStateReceiver;
import com.skyking.play_module.receivers.PlayServiceReceiver;

import java.io.IOException;
import java.util.Timer;

/**
 * Created by SkykingAndroid on 2017-07-21.
 */

public class PlayService extends Service {

    public static final String ACT_INIT = "ACT_INIT";
    public static final String ACT_STOP_SERVICE = "ACT_STOP_SERVICE";
    public static final String ACT_START_PLAY = "ACT_START_PLAY";
    public static final String ACT_STOP_PLAY = "ACT_STOP_PLAY";
    public static final String ACT_MUTE_TRUE = "ACT_MUTE_TRUE";
    public static final String ACT_MUTE_FALSE = "ACT_MUTE_FALSE";

    public static final String ACT_NOTIFICATION_PLAY = "ACT_NOTIFICATION_PLAY";
    public static final String ACT_NOTIFICATION_MUTE = "ACT_NOTIFICATION_MUTE";

    public static BasicRemoteView remoteView;


    static P2PManager p2PManager;
    final String TAG = PlayService.class.getSimpleName();

    //    public static ChannelEntity.Channel channel;
    public static ChData chData;
    NetworkStateReceiver networkStateReceiver;

    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener;
    AudioManager audioManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return super.onStartCommand(intent, flags, startId);

        String act = intent.getAction();
        if (TextUtils.isEmpty(act)) return super.onStartCommand(intent, flags, startId);
        MLog.i(TAG, "action " + act);

        switch (act) {
            case ACT_INIT:
                startService(new Intent(getApplicationContext(), PlayService.class));
                p2PManager = new P2PManager(getApplicationContext()) {
                    @Override
                    public void onCoreInited(boolean success) {
                        MLog.v(TAG, "onCoreInited " + success);
                        Intent initIntent = new Intent(PlayServiceReceiver.RECEIVER_NAME);
                        initIntent.putExtra(PlayServiceReceiver.KEY_ACTION, PlayServiceReceiver.ACT_CORE_INIT);
                        initIntent.putExtra(PlayServiceReceiver.KEY_CORE_INIT, success);
                        sendBroadcast(initIntent);
                    }

                    @Override
                    public void onPlayerPrepared() {
                        Intent initIntent = new Intent(PlayServiceReceiver.RECEIVER_NAME);
                        initIntent.putExtra(PlayServiceReceiver.KEY_ACTION, PlayServiceReceiver.ACT_PLAYER_PREPARED);
                        sendBroadcast(initIntent);

                        if (remoteView != null && chData.channelType.equals("RADIO")) {
                            remoteView.startNotification(true);
                            startForeground(BasicRemoteView.ID, remoteView.notification);
                        }

                        if (chData != null && chData.channelType.equals("RADIO")) {
                            Intent control = new Intent(ControlReceiver.RECEIVER_NAME);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(ControlReceiver.KEY_ACTION, ControlReceiver.ControlAction.PLAY);
                            bundle.putBoolean(ControlReceiver.KEY_PLAY, true);
                            bundle.putSerializable(ControlReceiver.KEY_SET_CHANNEL, chData);
                            control.putExtras(bundle);
                            sendBroadcast(control);
                        }

                    }
                };
                p2PManager.startCore();

                networkStateReceiver = new NetworkStateReceiver() {
                    @Override
                    public void onNetworkChange(NetworkInfo networkInfo, boolean isConnect) {
                        if (!isConnect && isPlaying()) {
                            p2PManager.stopPlay();
                            stopForeground(false);
                            stopPlayM3U8();
                        } else if (isConnect && !isPlaying()) {
                            play();
                        }

                        Intent i = new Intent(PlayServiceReceiver.RECEIVER_NAME);
                        i.putExtra(PlayServiceReceiver.KEY_ACTION, PlayServiceReceiver.ACT_NETWORK_STATUS_CHANGED);
                        i.putExtra(PlayServiceReceiver.KEY_NETWORK_STATUS, isConnect);
                        sendBroadcast(i);
                    }
                };
                networkStateReceiver.register(getApplicationContext());


                break;
            case ACT_STOP_SERVICE:
                if (p2PManager != null) {
                    p2PManager.stopPlay();
                    p2PManager.stopCore();
                }
                stopPlayM3U8();
                stopForeground(false);
                stopService(new Intent(getApplicationContext(), PlayService.class));
                break;
            case ACT_START_PLAY:
                play();
                break;
            case ACT_STOP_PLAY:
                stop();
                break;
            case ACT_MUTE_TRUE:
                if (p2PManager != null) p2PManager.mute(true);
                mute(true);
                break;
            case ACT_MUTE_FALSE:
                if (p2PManager != null) p2PManager.mute(false);
                mute(false);
                break;
            case ACT_NOTIFICATION_PLAY:
                if (isPlaying()) {
                    stop();
                } else {
                    play();
                }
                break;
            case ACT_NOTIFICATION_MUTE:
                isMute = !isMute;
                if (p2PManager != null) p2PManager.mute(isMute);
                mute(isMute);
                break;
        }


        return super.onStartCommand(intent, flags, startId);
    }

    //監聽其它任何發出聲音的
    private void requestFocus() {
        if (audioManager == null) audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (onAudioFocusChangeListener == null) {
            MLog.d(TAG, "new onAudioFocusChangeListener");
            onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    MLog.w(TAG, "AudioManager focusChange=" + focusChange);
                    switch (focusChange) {
                        case AudioManager.AUDIOFOCUS_GAIN:
                            MLog.w(TAG, "AudioManager.AUDIOFOCUS_GAIN");
                            try {
                                if (isPlaying()) mediaPlayer.setVolume(1, 1);
                            } catch (IllegalStateException e) {
                            }

                            break;
                        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                            try {
                                if (isPlaying()) mediaPlayer.setVolume(0, 0);
                            } catch (IllegalStateException e) {
                            }
                            break;
                        case AudioManager.AUDIOFOCUS_LOSS:
                        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                            stop();
                            break;
                    }
                }
            };
        }
        audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
//        int result = audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
//        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    private void stop() {
        if (p2PManager != null) p2PManager.stopPlay();
        stopPlayM3U8();

        Intent control = new Intent(ControlReceiver.RECEIVER_NAME);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ControlReceiver.KEY_ACTION, ControlReceiver.ControlAction.PLAY);
        bundle.putBoolean(ControlReceiver.KEY_PLAY, false);
        bundle.putSerializable(ControlReceiver.KEY_SET_CHANNEL, chData);
        control.putExtras(bundle);
        sendBroadcast(control);

        MLog.d(TAG, "to stop.......................");

        stopForeground(false);
        if (remoteView != null) remoteView.setOnGoing(false);

    }

    private void play() {
        p2PManager.stopPlay();
        P2PManager.chData = null;
        stopPlayM3U8();

        if (chData == null) return;
        if (TextUtils.isEmpty(chData.streamType)) return;
        if (TextUtils.isEmpty(chData.url)) return;

        // : 2017-07-21 stop play first, m3u8 and P2P

        MLog.i(TAG, "channel type=" + chData.channelType);

        if (remoteView != null) {
            if (chData.channelType.equals("RADIO") && remoteView != null) {
                remoteView.startNotification(true);
                startForeground(BasicRemoteView.ID, remoteView.notification);

                Intent intent = new Intent(ControlReceiver.RECEIVER_NAME);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ControlReceiver.KEY_ACTION, ControlReceiver.ControlAction.PREPARE);
                intent.putExtras(bundle);
                sendBroadcast(intent);

            } else {
                stopForeground(true);
//                remoteView.dismiss();
            }
        }
        requestFocus();

//        if (!chData.channelType.equals("RADIO")) {
//            stopForeground(false);
//            if (remoteView != null) remoteView.dismiss();
//        }

        switch (chData.streamType) {
            case "M3U8":
                playM3U8(chData.url, chData.channelType);
                break;
            case "P2P":
                P2PManager.chData = chData;
                p2PManager.startPlay();
                break;
        }
    }

    static MediaPlayer mediaPlayer;
    public static VideoView videoView;

    Timer timer;

    private void playM3U8(String url, String channelType) {
        switch (channelType) {
            case "VIDEO":

                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer = mp;

                        Intent initIntent = new Intent(PlayServiceReceiver.RECEIVER_NAME);
                        initIntent.putExtra(PlayServiceReceiver.KEY_ACTION, PlayServiceReceiver.ACT_PLAYER_PREPARED);
                        sendBroadcast(initIntent);

                        Intent intent = new Intent(BufferReceiver.class.getSimpleName());
                        intent.putExtra(BufferReceiver.KEY_BUFFER, 100);
                        sendBroadcast(intent);

                        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                            @Override
                            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                                MLog.d(TAG, "m3u8 buffer=" + percent);
                                Intent intent = new Intent(BufferReceiver.class.getSimpleName());
                                intent.putExtra(BufferReceiver.KEY_BUFFER, percent);
                                sendBroadcast(intent);
                            }
                        });


                        Intent control = new Intent(ControlReceiver.RECEIVER_NAME);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(ControlReceiver.KEY_ACTION, ControlReceiver.ControlAction.PLAY);
                        bundle.putBoolean(ControlReceiver.KEY_PLAY, true);
                        bundle.putSerializable(ControlReceiver.KEY_SET_CHANNEL, chData);
                        control.putExtras(bundle);
                        sendBroadcast(control);

                        mute(isMute);
                        videoView.start();
                        stopForeground(false);
                        if (remoteView != null) remoteView.dismiss();
                    }
                });
                videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        return false;
                    }
                });
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                        @Override
                        public boolean onInfo(MediaPlayer mp, int what, int extra) {
                            MLog.d(TAG, "on m3u8 info --> what=" + what + "/extra=" + extra);
                            return false;
                        }
                    });
                }
                videoView.setVideoURI(Uri.parse(url));

                // TODO: 2017-07-25  video buffer
//                timer = new Timer();
//                TimerTask task=new TimerTask() {
//                    @Override
//                    public void run() {
//                        int buffer=videoView.getBufferPercentage();
//                        MLog.d(TAG,"timer task buffer... "+buffer);
//                        Intent intent = new Intent(BufferReceiver.class.getSimpleName());
//                        intent.putExtra(BufferReceiver.KEY_BUFFER, buffer);
//                        getApplicationContext().sendBroadcast(intent);
//                    }
//                };
//                timer.schedule(task,1000,1000);


                break;
            case "RADIO":
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer = mp;
                        mute(isMute);
                        mediaPlayer.start();
                        Intent initIntent = new Intent(PlayServiceReceiver.RECEIVER_NAME);
                        initIntent.putExtra(PlayServiceReceiver.KEY_ACTION, PlayServiceReceiver.ACT_PLAYER_PREPARED);
                        getApplicationContext().sendBroadcast(initIntent);

                        Intent intent = new Intent(BufferReceiver.class.getSimpleName());
                        intent.putExtra(BufferReceiver.KEY_BUFFER, 100);
                        getApplicationContext().sendBroadcast(intent);

                        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                            @Override
                            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                                MLog.d(TAG, "m3u8 buffer=" + percent);
                            }
                        });

                        Intent control = new Intent(ControlReceiver.RECEIVER_NAME);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(ControlReceiver.KEY_ACTION, ControlReceiver.ControlAction.PLAY);
                        bundle.putBoolean(ControlReceiver.KEY_PLAY, true);
                        bundle.putSerializable(ControlReceiver.KEY_SET_CHANNEL, chData);
                        control.putExtras(bundle);
                        sendBroadcast(control);

                        if (remoteView != null && chData.channelType.equals("RADIO")) {
                            remoteView.startNotification(true);
                            startForeground(BasicRemoteView.ID, remoteView.notification);
                        }


                    }
                });
                mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        return false;
                    }
                });
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                        @Override
                        public boolean onInfo(MediaPlayer mp, int what, int extra) {
                            MLog.d(TAG, "on m3u8 info --> " + what);
                            return false;
                        }
                    });
                }
                try {
//                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(url));
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    public static boolean isMute;

    private void mute(boolean isMute) {

        if (mediaPlayer != null) {


            float volume = isMute ? 0f : 1f;
            try {
                mediaPlayer.setVolume(volume, volume);
                this.isMute = isMute;
            } catch (IllegalStateException e) {
            } catch (Exception e) {
            }
            Intent control = new Intent(ControlReceiver.RECEIVER_NAME);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ControlReceiver.KEY_ACTION, ControlReceiver.ControlAction.MUTE);
            bundle.putBoolean(ControlReceiver.KEY_MUTE, isMute);
            control.putExtras(bundle);
            sendBroadcast(control);
        }
    }

    private void stopPlayM3U8() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (videoView != null) {
            videoView.stopPlayback();
        }
//        else if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//            mediaPlayer.stop();
//            mediaPlayer.release();
//        }
        try {
            mediaPlayer.stop();
            mediaPlayer.release();
        } catch (IllegalStateException e) {
        } catch (Exception e) {
        }

        Intent intent = new Intent(BufferReceiver.class.getSimpleName());
        intent.putExtra(BufferReceiver.KEY_BUFFER, 0);
        getApplicationContext().sendBroadcast(intent);

    }

    public static boolean isPlaying() {
        if (p2PManager != null && p2PManager.isPlaying()) return true;
        if (videoView != null && videoView.isPlaying()) return true;
        try {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) return true;
        } catch (IllegalStateException e) {
        }
        return false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    private void notification() {
//        if (notificationMusicControl == null)
//            notificationMusicControl = new BasicNotificationControl(getApplicationContext()).notification();
//        startForeground(BasicNotificationControl.ID, notificationMusicControl.notification);
//    }
}
