package com.twgood.skytv_test.basic;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

/**
 * Created by Dexter on 2016/10/5.
 */

public abstract class BasicDialog extends Dialog {
    protected int dialogId;
    protected Activity activity;

    public BasicDialog(Activity activity) {
        super(activity);
        this.activity = activity;
        init();
        setContentView(getCustomLayoutResId());
    }

    public BasicDialog(Activity activity, int dialogId) {
        this(activity);
        this.dialogId = dialogId;
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

    public int getDialogId() {
        return dialogId;
    }
}
