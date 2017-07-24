package com.skyking.skylib.basic;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by SkykingAndroid on 2016/7/18.
 */
public abstract class BasicFragment extends Fragment {

    protected BasicActivity activity;
    View mainView;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (BasicActivity) activity;
        initAfterAttachActivity();
    }

    protected void initAfterAttachActivity() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(getLayoutRes(), container, false);
        initView(mainView);
        return mainView;
    }


    protected abstract void initView(View view);

    protected abstract int getLayoutRes();

}
