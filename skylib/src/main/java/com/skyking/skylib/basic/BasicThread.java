package com.skyking.skylib.basic;

/**
 * Created by SkykingAndroid on 2017/2/9.
 */

public class BasicThread extends Thread {

    public boolean flag;

    public BasicThread() {
        flag = true;
    }

    public void toSleep(int mSec) {
        try {
            sleep(mSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void toWait() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void close() {
        flag = false;
        this.notify();
    }
}
