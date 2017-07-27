package com.skyking.skylib.entitys;

import java.util.List;

/**
 * Created by SkykingAndroid on 2016/10/26.
 */

public class NoticeEntity extends BasicEntity {


    public List<Data> data;

    public class Data {
        public String title;
        public String content;
        public String date;
    }

    @Override
    public boolean hasData() {
        if (data == null) return false;
        if (data.size() == 0) return false;
        return true;
    }
}
