package dexterliu.studio.dexlibs.basic;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by Dexter on 2016/10/5.
 */

public abstract class BasicDialogBuilder extends AlertDialog.Builder {
    protected AlertDialog dialog;

    public BasicDialogBuilder(Context context) {
        super(context);
        setView(getCustomLayoutResId());
    }

    @Override
    public AlertDialog show() {
        setButton();
        dialog = super.show();
        initLayout();
        return dialog;
    }

    protected abstract void initLayout();

    protected abstract int getCustomLayoutResId();

    /**
     * setNegativeButton, setPositiveButton...
     */
    protected void setButton() {
    }

}
