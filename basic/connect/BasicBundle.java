package com.skyking.skylib.basic;

import android.content.Context;

import com.skyking.skylib.objects.Cons;
import com.skyking.skylib.tools.GetInfo;
import com.skyking.skylib.tools.MLog;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;


/**
 * Created by SkykingAndroid on 2016/7/27.
 */
public class BasicBundle {
    public String token;
    public String date;




    public BasicBundle() {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        date = f.format(Calendar.getInstance().getTime());
        token = new GetInfo().getAppMD5(date);
    }

    public String getUrl(Context context) {
        String sendUrl = Cons. HOST + getUrlValues();
//        String sendUrl = URLs.HOST + URLs.HOST_SUB_PATH + "?" + getUrlValues();
        MLog.v(this, "dexter send url=" + sendUrl);
        return sendUrl;
    }


    private HashMap<String, String> getUrlMap() {
        HashMap<String, String> bundleMap = new HashMap<>();
        for (Field field : this.getClass().getFields()) {
            field.setAccessible(true);
            Object obj = null;
            try {
                obj = field.get(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            String value = "";
            if (obj != null) value = obj.toString();
            if (field.getName().contains("$")) continue;
            bundleMap.put(field.getName(), value);
        }

        return bundleMap;
    }


    private String getUrlValues() {
        HashMap<String, String> map = getUrlMap();
        String url = "";
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            url += key + "=" + map.get(key);
            if (iterator.hasNext()) url += "&";
        }
        return url;
    }


}
