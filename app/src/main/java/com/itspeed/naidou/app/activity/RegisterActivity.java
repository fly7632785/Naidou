package com.itspeed.naidou.app.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.util.UIHelper;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.PreferenceHelper;
import org.kymjs.kjframe.utils.SystemTool;

/**
 * Created by jafir on 10/15/15.
 */
public class RegisterActivity extends KJActivity {


    @BindView(id = R.id.sign_back, click = true)
    private ImageView back;
    @BindView(id = R.id.sign_get_verify, click = true)
    private ImageView getVerify;
    @BindView(id = R.id.sign_up, click = true)
    private ImageView signup;
    @BindView(id = R.id.sign_phone)
    private EditText editPhone;
    @BindView(id = R.id.sign_password)
    private EditText editPassword;
    @BindView(id = R.id.sign_confirm_password)
    private EditText editConfirmPassword;
    @BindView(id = R.id.sign_verify)
    private EditText editVerify;

    private String phone;
    private String password;
    private String confirmPassword;
    private String verify;


    @Override
    public void setRootView() {
        setContentView(R.layout.aty_register);
        if (!SystemTool.checkNet(aty)) {
            Toast.makeText(aty, "您的网络没有连接", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()){
            case R.id.sign_back:
                aty.finish();
                break;
            case R.id.sign_up:
                register();
                break;
            case R.id.sign_get_verify:

                break;
        }
    }


    private void register(){
        phone = editPhone.getText().toString().trim();
        password = editPassword.getText().toString().trim();
        confirmPassword = editConfirmPassword.getText().toString().trim();
        verify = editVerify.getText().toString().trim();

        if(password.equals(confirmPassword)) {
            PreferenceHelper.clean(aty, LoginActivity.TAG);
            PreferenceHelper.write(aty, LoginActivity.TAG, "login_account", phone);
            PreferenceHelper.write(aty, LoginActivity.TAG, "login_password", password);
            UIHelper.showLogin(aty);
            aty.finish();
        }else {
            ViewInject.toast("密码不一致");
        }

    }

}
