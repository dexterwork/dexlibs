package com.skyking.skylib.entitys;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SkykingAndroid on 2016/10/26.
 */

public class LoginEntity extends BasicEntity {

    public List<Data> data;

    public class Data implements Serializable {
        public String account;
        public String project;
        public String start;
        public String end;
        public String key;

        public String password;//從輸入界面來
    }

    @Override
    public boolean hasData() {
        if(data==null)return false;
        if(data.size()==0)return false;
        return true;
    }

    public Data getNewLoginData() {
        return new Data();
    }
}
