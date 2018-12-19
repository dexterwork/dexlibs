package com.twgood.android.fcm;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.sky.twgpub.MLog;
import com.twgood.android.tools.SPman;

/**
 * Created by SkykingAndroid on 2018-03-09.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String token = FirebaseInstanceId.getInstance().getToken();
        MLog.d(MyFirebaseInstanceIDService.class, "FCM Refreshed token: " + token);
        SPman.setFcmToken(getApplicationContext(),token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(token);
    }

}
