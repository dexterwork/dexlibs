package com.skyking.aline.basic;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dexter on 2015/6/14.
 */
public abstract class BasicFragment extends Fragment {
    protected BasicActivity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (BasicActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);
        initView(view);
        return view;
    }
    protected abstract void initView(View view);

    protected abstract int getLayoutRes();
}
