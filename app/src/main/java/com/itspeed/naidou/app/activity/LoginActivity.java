package com.itspeed.naidou.app.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.util.UIHelper;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.PreferenceHelper;
import org.kymjs.kjframe.utils.SystemTool;

/**
 * Created by jafir on 10/15/15.
 */
public class LoginActivity extends KJActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    @BindView(id = R.id.login_username)
    private EditText editName;
    @BindView(id = R.id.login_password)
    private EditText editPassword;
    @BindView(id = R.id.login_in, click = true)
    private ImageView login;
    @BindView(id = R.id.login_scan, click = true)
    private TextView scan;
    @BindView(id = R.id.login_findback, click = true)
    private TextView findback;
    @BindView(id = R.id.login_sign, click = true)
    private ImageView sign;


    private String name;
    private String password;

    @Override
    public void setRootView() {
        setContentView(R.layout.aty_login);
        if (!SystemTool.checkNet(aty)) {
            Toast.makeText(aty, "您的网络没有连接", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initData() {
        super.initData();
        
    }

    @Override
    protected void onStart() {
        super.onStart();
        name = PreferenceHelper.readString(aty, TAG, "login_account");
        password = PreferenceHelper.readString(aty, TAG, "login_password");
        KJLoger.debug("账号" + name + "密码" + password);
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)){
            editName.setText(name);
            editPassword.setText(password);
        }
    }

    @Override
    public void widgetClick(View v) {

        switch (v.getId()) {
            case R.id.login_findback:
                break;
            case R.id.login_scan:
                UIHelper.showMain(aty);
               aty.finish();
                break;
            case R.id.login_sign:
                UIHelper.showRegister(aty);
                break;
            case R.id.login_in:
                if (isRight()) {
                    UIHelper.showMain(aty);
                   aty.finish();
                }

                break;
        }
    }

    private boolean isRight() {
         name = editName.getText().toString().trim();
         password = editPassword.getText().toString().trim();
        if (name.equals("") || name == null || password == null || password.equals("")) {
            Toast.makeText(aty, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            PreferenceHelper.write(aty, TAG, "login_account", name);
            PreferenceHelper.write(aty, TAG, "login_password", password);
            return true;
        }
    }
}
