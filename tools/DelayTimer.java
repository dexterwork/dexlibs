package com.awant.lion.objects;

import android.os.Handler;
import android.os.Message;

import com.awant.lion.basic.BasicThread;
import com.awant.lion.tools.MLog;

/**
 * Created by dexter on 2015/10/28.
 */
public class DelayTimer extends BasicThread {

    private  int delay = 200;

    public DelayTimer(IDelayTimer iDelayTimer) {
        this.iDelayTimer = iDelayTimer;

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (DelayTimer.this.iDelayTimer != null) {
                    try {
                        DelayTimer.this.iDelayTimer.delayEnd();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    @Override
    public void run() {
        MLog.v(this,"dexter DelayTimer --> START");
        toSleep(delay);
        mHandler.sendEmptyMessage(0);
    }


    IDelayTimer iDelayTimer;

    public interface IDelayTimer {
        void delayEnd();
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    Handler mHandler;
}
