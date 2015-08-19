package com.awant.lion.countdown;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 倒數計時器
 * Created by dexter on 2015/8/19.
 */
public class CountdownTimer {

    public interface TimeListener {
        void timeout();//時間到通知

        void onChange(long sec);//變動秒數
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
        this.delay = delaySec * 1000;
    }

    public void startTimer(long sec, TimeListener timeListener) {
        this.listener = timeListener;
        timeSec = sec * 1000;
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                timeSec -= delay;
                if (timeSec < 0) timeSec = 0;
                listener.onChange(timeSec / 1000);
                if (timeSec == 0) {
                    stop();
                    listener.timeout();
                }
            }
        };
        timer.schedule(task, delay, delay);
    }

    private void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
