package com.twgood.android.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.sky.twgpub.MLog;
import com.twgood.android.Cons;
import com.twgood.android.MainActivity;
import com.twgood.android.R;
import com.twgood.android.receiver.LoginReceiver;
import com.twgood.android.tools.SPman;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        MLog.d(MyFirebaseMessagingService.class, "FCM From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {

            String status = remoteMessage.getData().get("status");
            String title = remoteMessage.getData().get("title");
            MLog.v(MyFirebaseMessagingService.class, "FCM v2 [status] " + status + " [title] " + title);

            if (!TextUtils.isEmpty(status) && status.equals("9")) {
                MLog.w(MyFirebaseMessagingService.class, "FCM to logout from FCM.");

                ContentValues cv = SPman.getLogin(this);
                if (cv != null) {
                    String account = cv.getAsString("account");
                    if (TextUtils.isEmpty(account) || account.equals(getString(R.string.default_account)))
                        return;
                }

                SPman.setLogin(this, "single", title, 0, 0, "");
                SPman.setParamter(this, null);
                if (TextUtils.isEmpty(title)) title = "您已在其他裝置登入此帳號。";
                Intent intent = new Intent(LoginReceiver.class.getSimpleName());
                intent.putExtra(LoginReceiver.KEY_ACTION, LoginReceiver.ACT_SINGLE);
                intent.putExtra(LoginReceiver.KEY_STATUS, status);
                intent.putExtra(LoginReceiver.KEY_TITLE, title);
                sendBroadcast(intent);
                return;
            }


//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            String message = remoteMessage.getNotification().getBody();
            MLog.d(MyFirebaseMessagingService.class, "FCM Message Notification Body: " + message);


        }
        if (remoteMessage != null) sendNotification(remoteMessage);

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        Intent intent = new Intent(this, MainActivity.class);
        MLog.d(MyFirebaseMessagingService.class, "FCM sendNotification: " + remoteMessage.getData().get("channel_url") + " " + remoteMessage.getData().get("channel_title"));
        if (remoteMessage.getData().get("channel_url") != null)
            intent.putExtra("channel_url", remoteMessage.getData().get("channel_url").toString());
        if (remoteMessage.getData().get("channel_title") != null)
            intent.putExtra("channel_title", remoteMessage.getData().get("channel_title").toString());
        if (remoteMessage.getData().get("web_url") != null)
            intent.putExtra("web_url", remoteMessage.getData().get("web_url").toString());
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        MLog.d(MyFirebaseMessagingService.class, "FCM 0");
        String body = "";
        try {
            body = remoteMessage.getNotification().getBody();
        } catch (NullPointerException e) {
        } catch (Exception e) {
            MLog.e(MyFirebaseMessagingService.class, e.getMessage());
        }
        MLog.d(MyFirebaseMessagingService.class, "FCM body: " + body);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.twg_logo)
                .setPriority(NotificationCompat.PRIORITY_MAX)//優先級
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentTitle("台灣好")
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        boolean toWake = false;

        if (toWake) {
            PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
//            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP , "TAG");
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP , "TAG");
            wl.acquire(15000);
        }
    }
}
