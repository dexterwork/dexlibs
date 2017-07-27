package com.skyking.skylib.basic;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.skyking.skylib.R;
import com.skyking.skylib.entitys.Channel;
import com.skyking.skylib.listener.OnBackPressedListener;
import com.skyking.skylib.listener.TabClickListener;
import com.skyking.skylib.objects.Cons;

/**
 * Created by SkykingAndroid on 2017/2/9.
 */

public class BasicActivity extends AppCompatActivity {

    public Handler mHandler;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
    }

    View decorView;
    final int HIDE_NAVIGATION_BAR_FLAG =
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

//    final int SHOW_nAVIGATION_BAR_FLAG=View.SystEM_UI_FLAG_NA

//            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//            | View.SYSTEM_UI_FLAG_FULLSCREEN
//            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

//    final int HIDE_NAVIGATION_BAR_FLAG = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//            | View.SYSTEM_UI_FLAG_FULLSCREEN
//            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;


    int systemUiVisibility;

    public void hideNavigationBar() {

        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion < Build.VERSION_CODES.KITKAT) return;
        if (decorView == null) {
            decorView = getWindow().getDecorView();
            systemUiVisibility = decorView.getSystemUiVisibility();
        }

        decorView.setSystemUiVisibility(HIDE_NAVIGATION_BAR_FLAG);
//        decorView.setOnSystemUiVisibilityChangeListener
//                (new View.OnSystemUiVisibilityChangeListener() {
//                    @Override
//                    public void onSystemUiVisibilityChange(int visibility) {
//                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
//                            decorView.setSystemUiVisibility(HIDE_NAVIGATION_BAR_FLAG);
//                        } else {
//                        }
//                    }
//                });
    }

    public void showNavigationBar() {
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion < Build.VERSION_CODES.KITKAT) return;
        if (decorView == null) {
            decorView = getWindow().getDecorView();
            systemUiVisibility = decorView.getSystemUiVisibility();
        }
        decorView.setSystemUiVisibility(systemUiVisibility);
    }


    private void init() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                int action = bundle.getInt(Cons.KEY_ACTION);
                switch (action) {
                    case Cons.ACTION_PROGRESS_DISMISS:
                        if (progressDialog != null && progressDialog.isShowing())
                            progressDialog.dismiss();
                        break;
                    case Cons.ACTION_PROGRESS_SHOW:
                        if (progressDialog != null && progressDialog.isShowing())
                            progressDialog.dismiss();
                        progressDialog = new ProgressDialog(BasicActivity.this);
                        progressDialog.setMessage(bundle.getString(Cons.KEY_VALUE1));
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        break;
                    case Cons.ACTION_TOAST:
                        Toast.makeText(BasicActivity.this, bundle.getString(Cons.KEY_VALUE1), Toast.LENGTH_SHORT).show();
                        break;
                    case Cons.ACTION_MESSAGE:
                        AlertDialog.Builder builder = new AlertDialog.Builder(BasicActivity.this);
                        builder.setCancelable(false);
                        builder.setMessage(bundle.getString(Cons.KEY_VALUE1));
                        builder.setPositiveButton(getString(R.string.know), null);
                        builder.show();
                        break;
                }
            }
        };
    }

    public void showProgress(boolean show) {
        if (show) next(Cons.ACTION_PROGRESS_SHOW, getString(R.string.pls_wait_));
        else next(Cons.ACTION_PROGRESS_DISMISS);
    }

    public void message(String message) {
        if (TextUtils.isEmpty(message)) return;
        next(Cons.ACTION_MESSAGE, message);
    }

    public void toast(String message) {
        next(Cons.ACTION_TOAST, message);
    }

    private void next(int action, String value) {
        Bundle bundle = new Bundle();
        bundle.putInt(Cons.KEY_ACTION, action);
        bundle.putString(Cons.KEY_VALUE1, value);
        Message message = new Message();
        message.setData(bundle);
        mHandler.sendMessage(message);
    }

    private void next(int action) {
        Bundle bundle = new Bundle();
        bundle.putInt(Cons.KEY_ACTION, action);
        Message message = new Message();
        message.setData(bundle);
        mHandler.sendMessage(message);
    }

    public void clickChannel(Channel channel) {
        if (clickChannelListener != null) clickChannelListener.onClickChannel(channel);
    }

    protected ClickChannelListener clickChannelListener;

    public interface ClickChannelListener {
        void onClickChannel(Channel channel);
    }

    public void replaceFragment(int containerId, BasicFragment fragment) {

        FragmentManager fm = getSupportFragmentManager();

        BasicFragment oFra = (BasicFragment) fm.findFragmentById(containerId);
        if (oFra != null) {
            String oName = oFra.getClass().getSimpleName();
            String nName = fragment.getClass().getSimpleName();
            if (oName.equals(nName)) return;
        }

        //不要一直按返回時都在首頁上游走
        int count = fm.getBackStackEntryCount();
        while (count >= 1) {
            fm.popBackStack();
            count--;
        }

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(containerId, fragment);
        ft.addToBackStack(fragment.getClass().getSimpleName());
        ft.commit();
    }

    public void closeNavigation() {
    }


    //    public PageBarItemListener pageBarItemListener;
    public OnBackPressedListener onBackPressedListener;

//    public OnConfigurationChangedListener onConfigurationChangedListener;

    public TabClickListener tabClickListener;

    public void onFragmentResume(String name) {
    }

    public void showMessageToExit(String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton(getString(R.string.know), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                System.exit(0);
            }
        });
        builder.show();
    }

    /**
     * 取得系統螢幕是否可翻轉(最外部設定, 重力感應)
     *
     * @return
     */
    protected boolean isSystemScreenRotationAuto() {
        return android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.ACCELEROMETER_ROTATION, 0) == 1;
    }

}
