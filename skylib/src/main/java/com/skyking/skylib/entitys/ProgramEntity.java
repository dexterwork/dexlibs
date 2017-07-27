package com.skyking.skylib.entitys;

import java.util.List;

/**
 * Created by SkykingAndroid on 2017/2/15.
 */

public class ProgramEntity extends BasicEntity {
    public List<Data> data;

    public class Data {
        public String pname;
        public String stime;
        public String etime;
    }

    @Override
    public boolean hasData() {
        if (data == null) return false;
        if (data.size() == 0) return false;

        return true;
    }
}
