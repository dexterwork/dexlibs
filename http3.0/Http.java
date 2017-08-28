package com.skyking.aline.connect2;

import android.app.AlertDialog;
import android.content.Context;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;

import com.skyking.aline.R;
import com.skyking.play_module.receivers.NetworkStateReceiver;


/**
 * Created by SkykingAndroid on 2016/7/29.
 */
public abstract class Http<T> {


    Handler handler;
    Class<T> entity;

    Context context;
    String url;

    public Http(Context context, String url, Class<T> entity) {
        this.context = context;
        this.url = url;
        this.entity = entity;
        init();
    }

    AlertDialog networkDialog;
    NetworkStateReceiver networkStateReceiver;

    public void exec() {

        if(!NetworkStateReceiver.isConnectedInternet(context))return;

        handler.sendEmptyMessage(ACT_REQUEST);
    }

    final int ACT_REQUEST = 10;
    final int ACT_RESPONSE = 11;
    Object object;

    private void init() {
        if (networkStateReceiver == null) {
            networkStateReceiver = new NetworkStateReceiver() {
                @Override
                public void onNetworkChange(NetworkInfo networkInfo, boolean isConnect) {
                    if (!isConnect) {
                        if (networkDialog == null) {
                            networkDialog = new AlertDialog.Builder(context).create();
                            networkDialog.setCancelable(false);
                            networkDialog.setMessage(context.getString(R.string.check_internet));
                            networkDialog.show();
                        }
                    } else if (networkDialog != null && networkDialog.isShowing()) {
                        networkDialog.dismiss();
                        networkDialog = null;
                        exec();
                    }
                }
            }.register(context);
        }

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int what = msg.what;
                switch (what) {

                    case ACT_RESPONSE:
                        try {
                            get((T) object);
                        } catch (Exception e) {
                            onError();
                        }
                        break;
                    case ACT_REQUEST:

                        new OkHttp(url, entity) {
                            @Override
                            protected void onPostExecute(Object o) {
                                if (o == null) {
                                    onError();
                                } else {
                                    object = o;
                                    handler.sendEmptyMessage(ACT_RESPONSE);
                                }
                            }
                        }.execute();

                        break;
                }


            }
        };
    }


    public abstract void onError();


    public abstract void get(T t);

}
