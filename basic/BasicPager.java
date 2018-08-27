package com.twgood.android.video;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class BasicPager extends ViewPager {
    public BasicPager(@NonNull Context context) {
        super(context);
    }


    public BasicPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    private boolean isPagingEnabled;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }


}
