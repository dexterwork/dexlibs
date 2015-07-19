package studio.dexter.tools;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * Created by Dexter on 2015/7/18.
 */
public class ImageTools {
    //獲取大圖, 直接調用 nativeDecodeAsset(), 防止 crash
    public static Bitmap getBigBitmapRes(Activity activity, int res) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //獲取資源圖片
        InputStream is = activity.getResources().openRawResource(res);
        return BitmapFactory.decodeStream(is, null, opt);
    }
}
