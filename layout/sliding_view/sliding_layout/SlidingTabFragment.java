package com.twgood.android.objects.sliding_layout;

import android.graphics.Color;

import com.twgood.android.basic.BasicFragment;

/**
 * Created by SkykingAndroid on 2016/9/19.
 */
public abstract class SlidingTabFragment extends BasicFragment{

    private String title = "";

    // default colors
    private int indicatorColor = Color.BLUE;
    private int dividerColor = Color.GRAY;


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
}
