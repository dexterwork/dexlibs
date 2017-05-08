package com.skyking.tvcoremodule;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Created by SkykingAndroid on 2016/7/27.
 */
public abstract class MPhoneStateListener extends PhoneStateListener {

    Context context;

    public MPhoneStateListener(Context context) {
        this.context = context;
        TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (mgr != null) {
            mgr.listen(this, PhoneStateListener.LISTEN_CALL_STATE);
//            mgr.listen(this, PhoneStateListener.LISTEN_NONE);
        }
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {

        switch (state){
            case TelephonyManager.CALL_STATE_RINGING://來電
                onPhoneRinging();
                break;
            case TelephonyManager.CALL_STATE_IDLE://待機
                onPhoneIdle();
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK://通話中

                break;
        }
        super.onCallStateChanged(state, incomingNumber);
    }

    public abstract void onPhoneRinging();
    public abstract void onPhoneIdle();
}
