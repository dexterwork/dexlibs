package com.skyking.televant.sliding_layout;

import android.graphics.Color;
import android.view.View;

import com.skyking.televant.R;
import com.skyking.televant.basic.BasicFragment;


/**
 * Created by SkykingAndroid on 2016/9/19.
 */
public abstract class SlidingTabFragment extends BasicFragment {

    private String title = "";

    // default colors
    private int indicatorColor = Color.BLUE;
    private int dividerColor = Color.GRAY;


    @Override
    protected void initView(View view) {
        setDividerColor(Color.TRANSPARENT);
        setIndicatorColor(getResources().getColor(R.color.sliding_indicator));
        initLayout(view);
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getIndicatorColor() {
        return indicatorColor;
    }
    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
    }
    public int getDividerColor() {
        return dividerColor;
    }
    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
    }

    public abstract void initLayout(View view);
}
