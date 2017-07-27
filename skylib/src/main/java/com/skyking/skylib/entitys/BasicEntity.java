package com.skyking.skylib.entitys;

import java.io.Serializable;

/**
 * Created by SkykingAndroid on 2017/2/13.
 */

public  class BasicEntity implements Serializable {
    public String isconnection;
    public String errorMessage;
    public int status;

protected boolean hasData(){return false;}
}
