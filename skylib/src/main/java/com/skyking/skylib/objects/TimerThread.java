package com.skyking.skylib.objects;

import com.skyking.skylib.basic.BasicThread;

/**
 * Created by SkykingAndroid on 2017/4/27.
 */

public class TimerThread extends BasicThread {

    private int delay;
    private final int DELAY;
    private final int SLEEP_TIME = 100;

    public TimerThread(int DELAY,TimerThreadListener timerThreadListener) {
        this.DELAY = DELAY;
        this.timerThreadListener = timerThreadListener;
    }

    @Override
    public void run() {
        while (delay >= 0) {
            toSleep(SLEEP_TIME);
            delay -= SLEEP_TIME;
        }
        timerThreadListener.onTimeOut();
    }


    public void reset() {
        delay = DELAY;
    }


    private TimerThreadListener timerThreadListener;


    public interface TimerThreadListener {
        void onTimeOut();
    }
}
