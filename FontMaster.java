package smartvest.abus.com.smartvest.system;

import android.app.Activity;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Dex on 2015/8/21.
 */
public class FontMaster {

    private Activity activity;
    private Typeface tfLatoLight, tfLatoRegular, tfLatoLightItalic, tfLatoBold, tfLatoSemibold;

    public FontMaster(Activity activity) {
        this.activity = activity;
        init();
    }

    public enum FontType {
        LATO_LIGHT, LATO_REGULAR, LATO_LIGHT_ITALIC, LATO_BOLD, LATO_SEMIBOLD
    }

    private void init() {
        tfLatoLight = Typeface.createFromAsset(activity.getAssets(), "fonts/Lato_Light.ttf");
        tfLatoRegular = Typeface.createFromAsset(activity.getAssets(), "fonts/Lato_Regular.ttf");
        tfLatoLightItalic = Typeface.createFromAsset(activity.getAssets(), "fonts/Lato_LightItalic.ttf");
        tfLatoBold = Typeface.createFromAsset(activity.getAssets(), "fonts/Lato_Bold.ttf");
        tfLatoSemibold = Typeface.createFromAsset(activity.getAssets(), "fonts/Lato_Semibold.ttf");
    }

    public void setFont(TextView tv, FontType type) {
        tv.setTypeface(getTypeface(type));
    }

    public void setFont(EditText et, FontType type) {
        et.setTypeface(getTypeface(type));
    }

    public void setFont(Button btn, FontType type) {
        btn.setTypeface(getTypeface(type));
    }

    private Typeface getTypeface(FontType type) {
        switch (type) {
            case LATO_LIGHT:
                return tfLatoLight;
            case LATO_REGULAR:
                return tfLatoRegular;
            case LATO_LIGHT_ITALIC:
                return tfLatoLightItalic;
            case LATO_BOLD:
                return tfLatoBold;
            case LATO_SEMIBOLD:
                return tfLatoSemibold;
            default:
                return tfLatoLight;
        }
    }


}
