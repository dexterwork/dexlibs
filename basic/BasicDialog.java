package com.twgood.skytv_test.basic;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

/**
 * Created by Dexter on 2016/10/5.
 */

public abstract class BasicDialog extends Dialog {

    public BasicDialog(Context context) {
        super(context);
        init();
        setContentView(getCustomLayoutResId());
    }


    @Override
    public void show() {
        super.show();
        initLayout();
    }

    protected abstract void initLayout();

    protected abstract int getCustomLayoutResId();


    private void init() {
        setCanceledOnTouchOutside(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

}
