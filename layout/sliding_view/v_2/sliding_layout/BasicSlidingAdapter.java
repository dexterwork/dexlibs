package com.twgood.studio.objects.sliding_layout;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twgood.studio.basic.BasicActivity;

/**
 * Created by SkykingAndroid on 2016/9/19.
 */
public abstract class BasicSlidingAdapter extends PagerAdapter {

    protected BasicActivity activity;

    public BasicSlidingAdapter(BasicActivity activity) {
        this.activity = activity;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getTagTitle(position);
    }

    protected abstract CharSequence getTagTitle(int position);

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = LayoutInflater.from(activity).inflate(getLayoutResId(position), container, false);
        initView(v, position);
        container.addView(v);
        return v;
    }

    protected abstract void initView(View v, int position);


    protected abstract int getLayoutResId(int position);


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
