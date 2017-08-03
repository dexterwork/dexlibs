package com.skyking.skylib.connect;

import android.app.AlertDialog;
import android.content.Context;
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

    Context context;


    public Http(Context context, BasicBundle bundle, Class<T> entity) {
        this.context = context;
        this.bundle = bundle;
        this.entity = entity;
        init();
    }


    public void exec() {
        if (!NetworkStateReceiver.isConnectedInternet(context)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setMessage(context.getString(R.string.check_internet));
            builder.setPositiveButton(context.getString(R.string.know), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    exec();
                }
            });
            builder.show();
            return;
        }
        handler.sendEmptyMessage(ACT_REQUEST);
    }

    final int ACT_REQUEST = 10;
    final int ACT_RESPONSE = 11;
    Object object;

    private void init() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int what = msg.what;
                switch (what) {

                    case ACT_RESPONSE:
                        try {
                            get((T) object);
                        }catch (Exception e){
                            onError();
                        }
                        break;
                    case ACT_REQUEST:

                        new OkHttp( bundle.getUrl(TAG), entity  ){
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

//                        new HttpGetPost(bundle.getUrl(TAG), entity) {
//                            @Override
//                            protected void onPostExecute(Object o) {
//                                if (o == null) {
//                                    onError();
//                                } else {
//                                    object = o;
//                                    handler.sendEmptyMessage(ACT_RESPONSE);
//                                }
//                            }
//                        }.execute();
                        break;
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
