package com.skyking.skylib.entitys;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SkykingAndroid on 2016/9/21.
 */

public class ChannelEntity extends BasicEntity{



    public List<Data> data;

    public class Data {
        public List<Group> Group=new ArrayList<>();
    }

    @Override
    public boolean hasData() {
        if(data==null)return false;
        if(data.size()==0)return false;
        if(data.get(0).Group==null)return false;
        if(data.get(0).Group.size()==0)return false;
        return true;
    }
}
