package com.skyking.skylib.objects;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by SkykingAndroid on 2017/2/10.
 */

public class SquareImage extends ImageView {

    public SquareImage(Context context) {
        super(context);
    }

    public SquareImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);

    }
}
