package com.gtsmarthome.tw.smarthome.core;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.gtsmarthome.tw.smarthome.tools.Setting;

/**
 * Created by Joy Chiang on 2015/1/17.
 */
public abstract class SuperVolleyConnect {

    public enum ConnectEnvironment {
        PRODUCTION, LOCAL_TEST
    }

    private static ConnectEnvironment DEFAULT_CONNECT_ENVIRONMENT = ConnectEnvironment.PRODUCTION;

    private static String RESTFUL_SERVICE_HOST_WWW = null;
    private static String RESTFUL_SERVICE_HOST_SSL = null;

    private static RequestQueue mRequestQueue;
    private static ConnectEnvironment mConnectEnvironment;

    public static void init(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        setConnectEnvironment(DEFAULT_CONNECT_ENVIRONMENT);
    }

    public static void setConnectEnvironment(ConnectEnvironment environment) {
        mConnectEnvironment = environment;
        createConnectHostUrl();
    }

    public static void cancelConnect(Object user) {
        mRequestQueue.cancelAll(user);
    }

    private static void createConnectHostUrl() {
        switch (mConnectEnvironment) {
            case LOCAL_TEST:
            case PRODUCTION:
            default:

                if (Setting.TEST_SERVER) {
                    RESTFUL_SERVICE_HOST_WWW = "https://elslinux.no-ip.net/";
                    RESTFUL_SERVICE_HOST_SSL = "https://elslinux.no-ip.net/";
                } else {
                    RESTFUL_SERVICE_HOST_WWW = "https://api.gtlifecloud.tw/";
                    RESTFUL_SERVICE_HOST_SSL = "https://api.gtlifecloud.tw/";
                }
        }
    }

    protected void performRequest(Request<?> request) {
        mRequestQueue.add(request);
    }

    public static RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    protected String getWwwUrl() {
        return RESTFUL_SERVICE_HOST_WWW;
    }

    protected String getSslUrl() {
        return RESTFUL_SERVICE_HOST_SSL;
    }

    public abstract void request(Object user);

    public static void clearData() {
        RESTFUL_SERVICE_HOST_WWW = null;
        RESTFUL_SERVICE_HOST_SSL = null;
        mRequestQueue = null;
        mConnectEnvironment = null;
    }

    public static boolean hasInternet(Context context) {
        NetworkInfo ni = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (ni != null && ni.isAvailable() && ni.isConnected() && ni.isConnectedOrConnecting()/* && !ni.isFailover()*/) {
            return true;
        }
        return false;
    }
}
