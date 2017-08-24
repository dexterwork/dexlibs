package dexter.studio.dexlib.connect2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import dexter.studio.dexlib.bundles.BasicBundle;


/**
 * Created by SkykingAndroid on 2016/7/29.
 */
public abstract class Http<T> {

    final String TAG = "Http";

    Handler handler;
    Class<T> entity;
    String url;

    Context context;
    BasicBundle bundle;

    public Http(Context context, String url, BasicBundle bundle, Class<T> entity) {
        this.context = context;
        this.url = url;
        this.bundle=bundle;
        this.entity = entity;
        init();
    }


    public void exec() {
        if (!NetworkStateReceiver.isConnectedInternet(context)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setMessage("請檢查您的網路");
            builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
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
    T object;

    private void init() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int what = msg.what;
                switch (what) {

                    case ACT_RESPONSE:
                        try {
                            get( object);
                        } catch (Exception e) {
                            onError();
                        }
                        break;
                    case ACT_REQUEST:

                        new OkHttp<T>(url,bundle, entity) {
                            @Override
                            protected void onPostExecute(T o) {
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
