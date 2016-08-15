package com.skyking.skykingradio;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import basic.BasicAppActivity;
import constants.Cons;
import entitys.ChannelEntity;
import entitys.LoginEntity;
import entitys.VerifyEntity;
import main.DataManager;
import member.Login;
import objects.Info;
import objects.ShowDialog;
import objects.VerifyAccount;
import permission.PermissionsActivity;
import permission.PermissionsChecker;
import tools.Tools;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends BasicAppActivity {
    final String TAG = "SplashActivity";

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionsChecker checker = new PermissionsChecker(this);
            if (checker.lacksPermissions(PERMISSIONS)) {
                startPermissionsActivity();
            } else {
                start();
            }
        } else {
            start();
        }
    }

    private static final int REQUEST_CODE = 0;
    static final String[] PERMISSIONS = new String[]{
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        } else {
			// TODO: 2016/8/15   to start app (to splash or main activity)
        }
    }

	
}
