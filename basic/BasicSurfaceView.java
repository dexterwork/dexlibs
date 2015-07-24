package studio.dexter.basic;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Dexter on 2015/7/21.
 */
public abstract class BasicSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    protected Canvas canvas;
    protected SurfaceHolder holder;

    public BasicSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public BasicSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    public BasicSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.holder = holder;
        try {
            // 鎖定整個畫布，在內存要求比較高的情況下，建議參數不要為null
            canvas = holder.lockCanvas(null);
            synchronized (holder) {
                surfaceCreateDraw(canvas);
            }
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
                canvas = null;
            }
        }
    }

    protected abstract void surfaceCreateDraw(Canvas canvas);


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
