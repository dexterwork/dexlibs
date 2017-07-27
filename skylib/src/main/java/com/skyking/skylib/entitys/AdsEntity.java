package com.skyking.skylib.entitys;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by SkykingAndroid on 2016/10/26.
 */

public class AdsEntity extends BasicEntity {

    public List<Data> data;

    public class Data {
        public List<ImgBundle> index;
        public List<ImgBundle> banner;
        public List<ImgBundle> ad;

        public String version;//TV版本號
        public String zenbover;//Zenbo 版本號

    }


    public class ImgBundle {
        public String img;
        public String url;
        public Bitmap bitmap;
    }

    @Override
    public boolean hasData() {
        if (data == null) return false;
        if (data.size() == 0) return false;
        Data data = this.data.get(0);
        if (data.index == null) return false;
        if (data.banner == null) return false;
        if (data.ad == null) return false;
        return true;
    }
}
