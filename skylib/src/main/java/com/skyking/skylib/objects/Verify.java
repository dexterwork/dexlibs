package com.skyking.skylib.objects;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.skyking.skylib.R;
import com.skyking.skylib.basic.BasicActivity;
import com.skyking.skylib.bundles.LoginBundle;
import com.skyking.skylib.bundles.VerifyBundle;
import com.skyking.skylib.connect.Http;
import com.skyking.skylib.dialogs.MessageDialog;
import com.skyking.skylib.entitys.LoginEntity;
import com.skyking.skylib.entitys.VerifyEntity;
import com.skyking.skylib.member.LoginManager;
import com.skyking.skylib.tools.GetInfo;
import com.skyking.skylib.tools.MLog;
import com.skyking.skylib.tools.Settings;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by SkykingAndroid on 2017/2/21.
 */

public abstract class Verify {
    final String TAG = Verify.class.getSimpleName();
    BasicActivity activity;
    LoginBundle loginBundle;
    LoginEntity loginEntity;

    Handler handler;



    public Verify(BasicActivity activity, LoginBundle loginBundle, LoginEntity loginEntity) {
        this.activity = activity;
        this.loginBundle = loginBundle;
        this.loginEntity = loginEntity;
        initHandler();
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                toVerify();
            }
        };
    }

    public void toVerify() {

        VerifyBundle bundle = new VerifyBundle(loginBundle.getAccount(), GetInfo.getDeviceId(activity));
        new Http<VerifyEntity>(activity, bundle, VerifyEntity.class) {
            @Override
            public void onError() {
                MLog.w(TAG,"the fucking verify ERROR");
            }

            @Override
            public void get(VerifyEntity verifyEntity) {
                MLog.w(TAG, "verify:" + new Gson().toJson(verifyEntity));
                if (verifyEntity == null) {
                    verifyFail(activity.getString(R.string.msg_verify_error));
                    return;
                }
                if (!TextUtils.isEmpty(verifyEntity.errorMessage)) {
                    activity.message(verifyEntity.errorMessage);
                    return;
                }
                if (!verifyEntity.hasData()) {
                    verifyFail(activity.getString(R.string.msg_verify_error));
                    return;
                }
                if (verifyEntity.data.get(0).status.equals(Code.Success)) {
                    SkyData.endDate = verifyEntity.data.get(0).end;
                    if (loginEntity != null) LoginManager.save(loginBundle, loginEntity);
                    done();
                } else if (verifyEntity.data.get(0).status.equals(Code.OTHER_LOGIN)) {//已從其它裝置登入

                    if(GetInfo.byDeviceId(activity)){
                        SkyData.endDate = verifyEntity.data.get(0).end;
                        if (loginEntity != null) LoginManager.save(loginBundle, loginEntity);
                        MLog.v(TAG,"verify="+new Gson().toJson(verifyEntity));
                        done();
                        return;
                    }

                    verifyFail(activity.getString(R.string.no_verify));
                } else {
                    cancelSchedule();
                    SkyData.endDate = "";
                    error(verifyEntity.data.get(0).status);
                }
            }
        }.exec();
    }

    Timer timer;

    public void schedule() {
        if (GetInfo.byDeviceId(activity)) return;
        Date date = Calendar.getInstance().getTime();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, date, 1000 * 60 * Settings.VERIFY_SCHEDULE_MIN);
    }

    public void cancelSchedule() {
        if (timer != null) timer.cancel();
        timer = null;
    }


    private void verifyFail(String message) {
        cancelSchedule();
        new MessageDialog(activity) {
            @Override
            protected void done() {
                SkyData.endDate = "";
                LoginManager.clear();
                noVerifyAfterLogout();
            }
        }.show(message);
    }

    protected abstract void noVerifyAfterLogout();

    public abstract void done();


    public abstract void error(String status);
}
