package studio.dex.tools;

import android.view.View;
import android.widget.ImageView;

import java.util.TreeMap;

/** get SPRITE really size (WIDTH/HEIGHT) on "onCreate" or "onResume" life cycle.
 * Created by dexter on 2015/2/1.
 */
public class GetSizeWhenOnCreate {
    ImageView view;
    /**
     * get sprite width and height on onCreate lifecycle.
     * @return
     */
    private TreeMap<String,Integer> getSizeOnWhenCreateLifecycle(){
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);

        //this height and width are the view really size on "onCreate" or "onResume" life cycle.
        int height = view.getMeasuredHeight();
        int width = view.getMeasuredWidth();
        TreeMap<String, Integer> map = new TreeMap<String, Integer>();
        map.put("width", width);
        map.put("height", height);
        return map;
    }
}
