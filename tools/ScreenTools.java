package dexterliu.studio.dexlibs.tools;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Update by Dexter on 2016/9/5.
 */
public class ScreenTools {

    private Activity activity;

    public ScreenTools(Activity activity) {
        this.activity = activity;
        initSize();
    }

    /**
     * 解鎖讓螢幕可以翻轉
     */
    public void unlockOrientation() {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    /**
     * 讓螢幕不可以翻轉，並且保持直式螢幕
     */
    public void lockOrientationPortrait() {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 回傳螢幕目前是否為水平橫式
     *
     * @return
     */
    public boolean isLandscape() {
        return activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }


    /**
     * 取得系統螢幕是否可翻轉(最外部設定)
     *
     * @param context
     * @return
     */
    protected boolean isSystemScreenRotationAuto(Context context) {
        return android.provider.Settings.System.getInt(context.getContentResolver(), android.provider.Settings.System.ACCELEROMETER_ROTATION, 0) == 1;
    }

    private int screenHeight;
    private int screenWidth;


    private void initSize() {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        this.screenHeight = metrics.heightPixels;
        this.screenWidth = metrics.widthPixels;
    }

    /**
     * 取得螢幕寬(像素)
     *
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 取得螢幕高(像素)
     *
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 取得螢幕高(像素)
     *
     * @return
     */
    public int getScreenHeight() {
        return screenHeight;
    }

    /**
     * 取得螢幕寬(像素)
     *
     * @return
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * 回傳裝置是否為平板
     *
     * @return
     */
    public boolean isTablet() {
        return (activity.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
