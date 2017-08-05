package dexterliu.studio.imoney.basic.basic_layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by Dexter on 2017/8/5.
 */

public abstract class BasicRelativeLayout<T> extends RelativeLayout implements BasicLayoutInterface<T>{
    public BasicRelativeLayout(Context context) {
        super(context);
        init();
    }

    public BasicRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BasicRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
