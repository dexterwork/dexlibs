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
        handler.sendEmptyMessage(0);
    }

    private void init() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (!new Tools().checkInternet(activity)) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                    builder.setMessage(activity.getString(R.string.check_internet));
                    builder.setCancelable(false);
                    builder.setPositiveButton(activity.getString(R.string.know), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            handler.sendEmptyMessage(0);
                        }
                    });
                    builder.show();
                    return;
                }
                HttpGetPost hgp = new HttpGetPost(activity, bundle.getUrl(activity), entity, HttpGetPost.HttpType.POST) ;
//                hgp.setProgressDialog(getProgress(from));
                try {
                    get((T) hgp.execute().get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        };
    }

//    private ProgressDialog getProgress(String from) {
//        ProgressDialog dialog = new ProgressDialog(activity);
//        dialog.setCancelable(false);
//        String msg = activity.getString(R.string.pls_wait_);
//        dialog.setMessage(msg);
//        return dialog;
//    }

    public abstract void get(T t);

}
