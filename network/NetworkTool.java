package com.awant.lion.tools;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

/**
 * Created by 旺比優05 on 2015/7/29.
 */
public class NetworkTool {

    Context context;
    NetworkInfo networkInfo;
    public static int checkTime = 5000;
    NChecker nChecker;


    public NetworkTool(Context context) {
        this.context = context;
    }

    private boolean reset() {
        ConnectivityManager CM = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (CM != null) networkInfo = CM.getActiveNetworkInfo();
        return networkInfo != null ? true : false;
    }


    public interface NetworkCheckerListener {
        void onNotNetwork();

        void onNetwork();
    }

    public void startNetworkChecker(NetworkCheckerListener networkCheckerListener) {
        nChecker = new NChecker(networkCheckerListener);
        nChecker.start();
    }

    public void stopNetworkChecker() {
        if (nChecker != null && nChecker.isAlive()) nChecker.flag = false;
    }

    class NChecker extends Thread {
        NetworkCheckerListener listener;
        boolean flag;

        public NChecker(NetworkCheckerListener listener) {
            this.listener = listener;
            flag = true;
        }

        @Override
        public void run() {
            while (flag) {
                try {
                    sleep(checkTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!isSystemRunning()) return;
                if (checkNetworkConnected()) {
                    listener.onNetwork();
                } else {
                    listener.onNotNetwork();
                }
            }
        }
    }

    public boolean isSystemRunning() {
        ActivityManager mgr = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = mgr.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : list) {
            if (info.processName.equals(context.getPackageName())) return true;
        }
        return false;
    }

    //偵測是否有連到網路上
    public boolean checkNetworkConnected() {
        return isConnected() && isAvailable();
    }

    /**
     * 網路目前是否在漫遊中
     *
     * @return
     */
    public boolean isRoaming() {
        if (!reset()) return false;
        return networkInfo.isRoaming();
    }

    /**
     * 網路目前是否有問題
     *
     * @return
     */
    public boolean isFailover() {
        if (!reset()) return false;
        return networkInfo.isFailover();
    }

    /**
     * 網路是否已連接 或 連線中
     *
     * @return
     */
    public boolean isConnectedOrConnecting() {
        if (!reset()) return false;
        return networkInfo.isConnectedOrConnecting();
    }

    /**
     * 網路是否已連接
     *
     * @return
     */
    public boolean isConnected() {
        if (!reset()) return false;
        return networkInfo.isConnected();
    }

    /**
     * 目前網路是否可使用
     *
     * @return
     */
    public boolean isAvailable() {
        if (!reset()) return false;
        return networkInfo.isAvailable();
    }

    /**
     * 目前連線方式
     *
     * @return
     */
    public String getType() {
        if (!reset()) return null;
        return networkInfo.getTypeName();
    }

    /**
     * 目前連線狀態
     *
     * @return
     */
    public String getState() {
        if (!reset()) return null;
        return networkInfo.getState().toString();
    }
	
	 public boolean isConnectedInternet() {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }


}
