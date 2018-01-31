package dexter.studio.rsl.object;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;

import dexter.studio.rsl.R;

/**
 * Created by Dexter on 2018/1/31.
 */

public class ImageLoader {


    public static void load(Context context, ImageView imageView) {
        Glide.with(context).load(context.getString(R.string.bkg_url))
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }
}
