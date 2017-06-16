package com.skyking.televant.play_tv;

import android.app.Activity;
import android.content.pm.ActivityInfo;

/**
 * Created by SkykingAndroid on 2017-06-16.
 */

public class OrientationSensorSample {
    Activity activity;

    public OrientationSensorSample( Activity act) {
        this.activity = act;
        orientationListener = new OrientationListener(act) {

            @Override
            protected void onRotationChanged(int orientation) {
                //只要enable這裡就會一直傳過來
                //如果重力感應關閉則返回
                if (!isSystemScreenRotationAuto()) return;
                //開啟直橫感應
                if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_USER)
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
            }
        };
        if (orientationListener.canDetectOrientation()) {
            orientationListener.enable();
        } else {
            if (isSystemScreenRotationAuto())
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        }
    }

    public void disable(){
        orientationListener.disable();
    }

    OrientationListener orientationListener;

    /**
     * 取得系統螢幕是否可翻轉(最外部設定, 重力感應)
     * @return
     */
    private boolean isSystemScreenRotationAuto() {
        return android.provider.Settings.System.getInt(activity.getContentResolver(), android.provider.Settings.System.ACCELEROMETER_ROTATION, 0) == 1;
    }

}
