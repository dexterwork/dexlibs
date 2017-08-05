package dexterliu.studio.imoney.basic.basic_layout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Dexter on 2017/8/5.
 */

public abstract class BasicLinearLayout<T> extends LinearLayout implements BasicLayoutInterface<T> {
    public BasicLinearLayout(Context context) {
        super(context);
        init();
    }

    public BasicLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BasicLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected abstract void init();

    @Override
    public T setView(ViewGroup parent) {
        parent.addView(this);
        return (T)this;
    }

    @Override
    public void setMatchParent() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);
    }
}
