package com.awant.lion.countdown;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 倒數計時器
 * Created by dexter on 2015/8/19.
 */
public class CountdownTimer {
    public static final int SEC = 0;
    public static final int MINUTE = 1;
    public static final int HOUR = 2;
    public static final int DAY = 3;
    private static final int UNIT = 1000;

    public interface TimeListener {
        void onTimeOut();//時間到通知

        void onTimeChange(long sec);//變動秒數
    }

    private TimeListener listener;

    private Timer timer;
    private TimerTask task;


    private long delay;

    private long timeSec;

    public CountdownTimer() {
        setDelay(1);
    }

    public void setDelay(long delaySec) {
        this.delay = delaySec * UNIT;
    }


    public void startTimer(long time, TimeListener timeListener, int timeType) {
        switch (timeType) {
            case SEC:
                startTimer(time, timeListener);
                break;
            case MINUTE:
                startTimer(time * 60, timeListener);
                break;
            case HOUR:
                startTimer(time * 60 * 60, timeListener);
                break;
            case DAY:
                startTimer(time * 60 * 60 * 24, timeListener);
                break;
        }
    }

    /**
     * default time type is SEC.
     *
     * @param sec
     * @param timeListener
     */
    public void startTimer(long sec, TimeListener timeListener) {
        this.listener = timeListener;
        timeSec = sec * UNIT;
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                timeSec -= delay;
                if (timeSec < 0) timeSec = 0;
                listener.onTimeChange(timeSec / UNIT);
                if (timeSec == 0) {
                    stop();
                    listener.onTimeOut();
                }
            }
        };
        timer.schedule(task, delay, delay);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public HashMap<Integer, String> getFormatString(long timeSec) {
        long minute = timeSec / 60;
        long sec = timeSec % 60;
        long hour = minute / 60;
        long day = hour / 24;
        if (minute > 59) minute %= 60;
        if (hour > 23) hour %= 24;

        HashMap<Integer, String> map = new HashMap<Integer, String>();
        map.put(SEC, len(sec));
        map.put(MINUTE, len(minute));
        map.put(HOUR, len(hour));
        map.put(DAY, len(day));
        return map;
    }

    public String getNormalString(long timeSec, int maxType) {
        long minute = timeSec / 60;
        long sec = timeSec % 60;
        long hour = minute / 60;
        long day = hour / 24;
        if (minute > 59) minute %= 60;
        if (hour > 23) hour %= 24;

        String strTime = len(sec);
        switch (maxType) {
            case MINUTE:
                strTime = len(minute) + ":" + strTime;
                break;
            case HOUR:
                strTime = len(hour) + ":" + len(minute) + ":" + strTime;
                break;
            case DAY:
                strTime = String.valueOf(day) + ":" + len(hour) + ":" + len(minute) + ":" + strTime;
                break;
        }
        return strTime;
    }

    private String len(long time) {
        String str = String.valueOf(time);
        return (str.length() < 2) ? "0" + str : str;
    }


}
