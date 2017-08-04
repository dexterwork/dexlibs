package com.skyking.televant.object;

import android.view.MotionEvent;
import android.view.View;

import com.skyking.skylib.tools.ScreenTools;

/**
 * Created by SkykingAndroid on 2017-08-04.
 */

public class ScrollSoftTouch implements View.OnTouchListener {


    private float touchY;

    private boolean lock;

    private View container;
    private View topView;

    private float screenHeight;

    private float topViewHeight;

    public ScrollSoftTouch(View container, View topView) {
        this.container = container;
        this.topView = topView;
        screenHeight = ScreenTools.getScreenHeight(container.getContext());
    }

    public void setTopViewHeight(float topViewHeight) {
        this.topViewHeight = topViewHeight;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (topViewHeight == 0) topViewHeight = topView.getHeight();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                touchY = event.getY();
                break;
            case MotionEvent.ACTION_DOWN:
                touchY = event.getY();
                lock = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!lock && (touchY - event.getY() > 6 || event.getY() - touchY > 6))
                    lock = true;
                float containerY = container.getY();
                float mY = event.getY();
                float move = mY - touchY;
                float lastY = containerY + move;
                float adY = topView.getY();

                if (lastY >= topViewHeight) {
                    container.setY(topViewHeight);
                    topView.setAlpha(1);
                    containerHeight();
                    break;
                }

                if (lastY > adY) {
                    //alpha
                    topView.setAlpha(lastY / topView.getHeight());

                    container.setY(lastY);
                    containerHeight();
                    break;
                } else {
                    topView.setAlpha(0);
                    container.setY(adY);
                    containerHeight();
                    break;
                }

        }

        return false;
    }

    protected void containerHeight() {
        float height = screenHeight - container.getY();
        container.setMinimumHeight(Math.round(height));
    }

    public boolean isLock() {
        return lock;
    }
}
