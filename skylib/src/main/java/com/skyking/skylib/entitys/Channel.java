package com.skyking.skylib.entitys;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SkykingAndroid on 2016/10/12.
 */

public class Channel implements Serializable{

    public int id;
    public String name;
    public String url;
    public String icon;
    public String num;//sort & channel number

    public int sort;//依 num 排序到 list 後的序位

    public List<Program> program;

    public List<AdInfo> adInfo;
}
