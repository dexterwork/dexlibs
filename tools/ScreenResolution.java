package com.awant.lion.object;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by b on 23-Feb-15.
 */
public class ScreenResolution {

    int width;
    int height;

    public ScreenResolution(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        this.width = metrics.widthPixels;
        this.height = metrics.heightPixels;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
