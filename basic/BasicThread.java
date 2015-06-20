package studio.dexter.basic;

/**
 * Created by dexter on 2015/6/14.
 */
public class BasicThread extends Thread {
    private boolean flag;

    public BasicThread() {
        this.flag = true;
        this.setName(getClass().getSimpleName());
    }

    public void toSleep(int time) {
        try {
            sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void toWait() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void toNotify() {
        this.notify();
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
