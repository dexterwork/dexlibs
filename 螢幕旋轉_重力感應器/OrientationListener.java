package com.twgood.android.obj;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.os.Handler;
import android.view.OrientationEventListener;

/**
 * Created by SkykingAndroid on 2017-06-16.
 */

public class OrientationListener extends OrientationEventListener {


    public OrientationListener(Context context) {
        super(context, SensorManager.SENSOR_DELAY_NORMAL);
        this.context = context;

        if (canDetectOrientation()) {
            enable();
        } else if (isSystemScreenRotationAuto()) {
            ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        }
    }

    protected Context context;

    @Override
    public void onOrientationChanged(int orientation) {
//        orientation=重力角度
        //如果重力感應關閉則返回
        if (!isSystemScreenRotationAuto()) return;

        onRotationChanged(orientation);


//        //開啟直橫感應-視需要使用
        if (((Activity) context).getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_USER) {
            if (handler != null) return;
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //開啟直橫感應
                    ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
                    handler = null;

                }
            }, 500);
        }

    }

    Handler handler;

    /**
     * 取得系統螢幕是否可翻轉(最外部設定, 重力感應)
     *
     * @return
     */
    public boolean isSystemScreenRotationAuto() {
        return android.provider.Settings.System.getInt(context.getContentResolver(), android.provider.Settings.System.ACCELEROMETER_ROTATION, 0) == 1;
    }


    protected void onRotationChanged(int orientation) {
        //     orientation=重力角度
    }
}
