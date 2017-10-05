package com.skyking.twgmod.object;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by SkykingAndroid on 2017-09-26.
 */

public class VideoWidth169 extends VideoView {
    public VideoWidth169(Context context) {
        super(context);
    }

    public VideoWidth169(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoWidth169(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, Math.round(size / 16f * 9f));
    }
}
