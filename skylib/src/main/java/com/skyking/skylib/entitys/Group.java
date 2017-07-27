package com.skyking.skylib.entitys;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by SkykingAndroid on 2016/10/12.
 */

public class Group implements Serializable{

    public int id;
    public String name;
    public int islock;//1=成人台
    public List<Channel> channels=new ArrayList<>();

    public TreeMap<String, Channel> getChannelMap() {
        TreeMap<String, Channel> map = new TreeMap<>();
        if (channels == null) return map;
        for (Channel c : channels) {
            if (c == null) continue;
            if (TextUtils.isEmpty(c.num)) continue;
            try {
                if (!map.containsKey(c.num)) map.put(c.num, c);
            } catch (Exception e) {
            }
        }
        return map;
    }

    public List<Channel> getChannelListBySort() {
        TreeMap<String, Channel> map = getChannelMap();
        return new ArrayList<>(map.values());
    }

    public void set(){
        channels=getChannelListBySort();
    }
}
