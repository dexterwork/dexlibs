package com.skyking.skylib.objects;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.skyking.skylib.R;
import com.skyking.skylib.basic.BasicDialog;
import com.skyking.skylib.tools.MLog;
import com.skyking.skylib.tools.Tools;

/**
 * Created by SkykingAndroid on 2017/2/16.
 */

public abstract class PasswordDialog extends BasicDialog {

    EditText etPassword;

    String password;

    public PasswordDialog(Context context, String password) {
        super(context);
        this.password = password;
    }

    @Override
    protected void initLayout() {
        etPassword = (EditText) findViewById(R.id.password);
        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = etPassword.getText().toString();
                if(TextUtils.isEmpty(input))return;
                input=new Tools().toMD5(input);
                MLog.d("PasswordDialog","input="+input);
                if(input.equals(password)){
                    next();
                }else{
                    passwordError();
                }
                dismiss();
            }
        });
    }

    @Override
    protected int getCustomLayoutResId() {
        return R.layout.dialog_password;
    }

    public abstract void next();
    public abstract void passwordError();
}
