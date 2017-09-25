package com.skyking.twgmod.object;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by SkykingAndroid on 2017-09-25.
 */

public class ImageWidth169 extends ImageView {
    public ImageWidth169(Context context) {

        super(context);
    }

    public ImageWidth169(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageWidth169(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, Math.round(size / 16f * 9f));
    }


}
