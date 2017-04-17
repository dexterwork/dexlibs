package com.skyking.aline.basic;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by SkykingAndroid on 2017/4/17.
 */

public  class BasicPagerAdapter extends PagerAdapter {

    protected BasicActivity activity;

    protected List<View> viewList;

    public BasicPagerAdapter(BasicActivity activity,List<View> viewList) {
        this.activity = activity;
        this.viewList=viewList;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
