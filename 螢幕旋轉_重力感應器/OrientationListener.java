package com.skyking.televant.play_tv;

import android.content.Context;
import android.hardware.SensorManager;
import android.view.OrientationEventListener;

/**
 * Created by SkykingAndroid on 2017-06-16.
 */

public abstract class OrientationListener extends OrientationEventListener {
    public OrientationListener(Context context) {
        super(context, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onOrientationChanged(int orientation) {
        onRotationChanged(orientation);
    }

    protected abstract void onRotationChanged(int orientation);


}
