package com.skyking.skylib.connect;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.skyking.skylib.R;
import com.skyking.skylib.basic.BasicActivity;
import com.skyking.skylib.basic.BasicBundle;
import com.skyking.skylib.tools.Tools;

import java.util.concurrent.ExecutionException;


/**
 * Created by SkykingAndroid on 2016/7/29.
 */
public abstract class Http<T> {

    final String TAG = "Http";

    Handler handler;
    Class<T> entity;
    BasicBundle bundle;

    BasicActivity activity;


    public Http(BasicActivity activity, BasicBundle bundle, Class<T> entity) {
        this.activity = activity;
        this.bundle = bundle;
        this.entity = entity;
        init();
    }


    public void exec() {
        if(!NetworkStateReceiver.isConnectedInternet(activity)){
            AlertDialog.Builder builder=new AlertDialog.Builder(activity);
            builder.setCancelable(false);
            builder.setMessage(activity.getString(R.string.check_internet));
            builder.setPositiveButton(activity.getString(R.string.know), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    exec();
                }
            });
            builder.show();
            return;
        }
        handler.sendEmptyMessage(0);
    }

    private void init() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                HttpGetPost hgp = new HttpGetPost(activity, bundle.getUrl("Http"), entity);
//                HttpGetPost hgp = new HttpGetPost(activity, bundle.getUrl("Http"), entity, HttpGetPost.HttpType.POST);
//                hgp.setProgressDialog(getProgress(from));
                try {
                    get((T) hgp.execute().get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    onError();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    onError();
                }

            }
        };
    }

//    public void cancel() {
//        MLog.w(TAG,"HttpGetPost be cancel");
//        hgp.cancel(true);
//    }

    public abstract void onError();

//    private ProgressDialog getProgress(String from) {
//        ProgressDialog dialog = new ProgressDialog(activity);
//        dialog.setCancelable(false);
//        String msg = activity.getString(R.string.pls_wait_);
//        dialog.setMessage(msg);
//        return dialog;
//    }

    public abstract void get(T t);

}
