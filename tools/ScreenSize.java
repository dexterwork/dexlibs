package smartvest.abus.com.smartvest.tools;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * Created by dexter on 2015/1/29.
 */
public class ScreenSize {
    private int screenHeight;
    private int screenWidth;
    private Activity activity;

    public ScreenSize(Activity activity) {
        this.activity = activity;
        setSize();
    }

    private void setSize() {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        this.screenHeight = metrics.heightPixels;
        this.screenWidth = metrics.widthPixels;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
