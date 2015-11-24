package com.itspeed.naidou.app.activity;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.itspeed.naidou.R;
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;
import com.itspeed.naidou.app.util.CryptoUtil;
import com.itspeed.naidou.app.util.UIHelper;
import com.itspeed.naidou.model.bean.JsonBean.Entity;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.PreferenceHelper;
import org.kymjs.kjframe.utils.SystemTool;

/**
 * Created by jafir on 10/15/15.
 * 用户注册界面，需要用手机注册，发送验证码，验证
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
    private ProgressDialog dialog;


    @Override
    public void setRootView() {
        setContentView(R.layout.aty_register);
        if (!SystemTool.checkNet(aty)) {
            Toast.makeText(aty, "您的网络没有连接", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initData() {
        super.initData();
        dialog =new ProgressDialog(aty);
        dialog.setMessage("正在注册...");
        dialog.setCanceledOnTouchOutside(false);

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
            NaidouApi.register(phone,password,verify, new HttpCallBack() {

                @Override
                public void onPreStart() {
                    super.onPreStart();
                    dialog.show();
                }

                @Override
                public void onSuccess(String t) {
                    super.onSuccess(t);
                    KJLoger.debug("注册：" + t);
                    Entity entity = Response.getEntity(t);
                    if (entity.is_success()) {
                        ViewInject.toast("注册成功");
                        writeToSP();
                        UIHelper.showLogin(aty);
                        aty.finish();
                    }else {
                        ViewInject.toast("注册失败");
                    }
                    dialog.dismiss();
                }
            });
        }else {
            ViewInject.toast("密码不一致");
        }

    }

    /**
     * 注册成功后写入本地数据库
     */
    private void writeToSP() {
        PreferenceHelper.clean(aty, LoginActivity.TAG);
        PreferenceHelper.write(aty, LoginActivity.TAG, "login_account", phone);
        //加密密码
        password = CryptoUtil.encrypto(password);
        PreferenceHelper.write(aty, LoginActivity.TAG, "login_password", password);
    }


    @Override
    protected void onDestroy() {
        setContentView(R.layout.view_null);
        phone = null;
        password = null;
        confirmPassword = null;
        verify =null;
        dialog = null;

        back = null;
        getVerify = null;
        signup = null;
        editConfirmPassword = null;
        editPassword = null;
        editPhone = null;
        editVerify = null;

        super.onDestroy();
    }
}
