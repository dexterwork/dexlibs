package com.kingspirit.android.objects;

import android.content.Context;
import android.text.TextUtils;

import com.kingspirit.android.tools.MLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 檢查日期是否在到期的前幾天到幾天
 * Created by SkykingAndroid on 2016/8/15.
 */
public class EndDateChecker {

    Context context;
    Calendar calendar;
    SimpleDateFormat format;
    Date dNow, dBefore, dAfter;

    List<String> endDateList;


    private final int END_BEFORE_DAY = 7;
    private final int END_AFTER_DAY = 2;


    public EndDateChecker(Context context, List<String> endDateList) {
        this.context = context;
        this.endDateList = endDateList;
        calendar = Calendar.getInstance();
        format = new SimpleDateFormat(Cons.DATE_FORMAT);
        dNow = calendar.getTime();

        Calendar aft = Calendar.getInstance();
        aft.add(Calendar.DAY_OF_MONTH, END_AFTER_DAY);
        dAfter = aft.getTime();
        Calendar bef = Calendar.getInstance();
        bef.add(Calendar.DAY_OF_MONTH, END_BEFORE_DAY);
        dBefore = bef.getTime();
    }

    public List<String> check() {
        List<String> nList = new ArrayList<>();
        if (endDateList == null) return nList;
        if (endDateList.size() == 0) return nList;

        for (String endDate : endDateList) {
            if (com(endDate)) nList.add(endDate);//日期在 END_BEFORE_DAY 與 END_AFTER_DAY 之間的, 將會返回作通知處理
        }

        return nList;
    }


    /**
     * if the date before END_BEFORE_DAY and after END_AFTER_DAY return true.
     *
     * @param strDate
     * @return
     */
    private boolean com(String strDate) {
        if (TextUtils.isEmpty(strDate)) return false;
        Date d = null;
        try {
            d = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (d == null) return false;


        if (d.getTime() >= dAfter.getTime() && d.getTime() < dBefore.getTime()) {
            MLog.d(this, "dexter " + format.format(d) + " in after " + END_AFTER_DAY + " days and before " + END_BEFORE_DAY + " days.");
            return true;
        }

        return false;
    }

}
