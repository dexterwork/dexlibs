package com.skyking.skylib.member;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.skyking.skylib.R;
import com.skyking.skylib.basic.BasicActivity;
import com.skyking.skylib.bundles.LoginBundle;
import com.skyking.skylib.connect.Http;
import com.skyking.skylib.entitys.LoginEntity;
import com.skyking.skylib.objects.Verify;
import com.skyking.skylib.tools.MLog;

/**
 * Created by SkykingAndroid on 2017/2/15.
 */

public abstract class Login {
    final String TAG = Login.class.getSimpleName();

    BasicActivity activity;

    public Login(BasicActivity activity) {
        this.activity = activity;
    }

    LoginBundle loginBundle;
    LoginEntity loginEntity;

    public void login(LoginBundle loginBundle) {

        this.loginBundle = loginBundle;
        MLog.w(TAG, "login:" + new Gson().toJson(loginBundle));
        new Http<LoginEntity>(activity, loginBundle, LoginEntity.class) {
            @Override
            public void onError() {
                MLog.w(TAG,"the fucking LoginEntity ERROR");
            }

            @Override
            public void get(LoginEntity loginEntity) {
                activity.showProgress(false);
                if (loginEntity == null) {
                    activity.toast(activity.getString(R.string.msg_login_fail));
                    return;
                }
                if (!loginEntity.hasData()) {
                    activity.toast(activity.getString(R.string.msg_login_fail));
                    return;
                }
                if (!TextUtils.isEmpty(loginEntity.errorMessage)) {
                    activity.message(loginEntity.errorMessage);
                    return;
                }
//                LoginManager.save(Login.this.loginBundle, loginEntity);
                Login.this.loginEntity = loginEntity;
                MLog.i(TAG, "login:" + new Gson().toJson(loginEntity));
                toVerify();

            }
        }.exec();
    }

    private void toVerify() {
        new Verify(activity,loginBundle, loginEntity) {
            @Override
            public void done() {
                afterLogin(loginBundle.getAccount(), loginEntity.data.get(0).key);
            }

            @Override
            public void noVerifyAfterLogout() {
                otherLogin();
            }

            @Override
            public void error(String status) {
                otherLogin();
            }
        }.toVerify();

    }

    protected abstract void afterLogin(String account, String key);

    protected abstract void otherLogin();


}
