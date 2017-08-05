package dexterliu.studio.imoney.basic.basic_layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Dexter on 2017/8/5.
 */

public abstract class BasicListView<T> extends ListView implements BasicLayoutInterface<T> {
    public BasicListView(Context context) {
        super(context);
    }

    public BasicListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasicListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected abstract void init();

    @Override
    public T setView(ViewGroup parent) {
        parent.addView(this);
        return (T) this;
    }

    @Override
    public void setMatchParent() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);
    }
}
