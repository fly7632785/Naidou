package com.itspeed.naidou.app.fragment.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.itspeed.naidou.R;
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;
import com.itspeed.naidou.app.AppContext;
import com.itspeed.naidou.app.activity.LoginActivity;
import com.itspeed.naidou.app.activity.SimpleBackActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.fragment.TitleBarSupportFragment;
import com.itspeed.naidou.app.util.CryptoUtil;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.PreferenceHelper;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**
 * Created by jafir on 16/3/7.
 */
public class FindBackPwdFragment extends TitleBarSupportFragment {
    private SimpleBackActivity aty;
    private View layout;

    @BindView(id = R.id.modifypwd_oldpwd_ll)
    private LinearLayout mOldPwdLayout;
    @BindView(id = R.id.modifypwd_oldpwd_line)
    private View mOldPwdLine;

    @BindView(id = R.id.modifypwd_confirm, click = true)
    private ImageView mConfirm;
    @BindView(id = R.id.modifypwd_oldpwd)
    private EditText mOldPwd;
    @BindView(id = R.id.modifypwd_newpwd)
    private EditText mNewPwd;
    @BindView(id = R.id.modifypwd_confirmnew)
    private EditText mConfirNewPwd;

    private String phone;

    //密码格式要求的正则表达式
    private String reg = "^[\\@A-Za-z0-9\\!\\#\\$\\%\\^\\&\\*\\.\\~]{6,22}$";
    private EventHandler handler;

    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layout = View.inflate(aty, R.layout.frag_modifypwd, null);
        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aty = (SimpleBackActivity) getActivity();
        onChange();
    }

    @Override
    protected void initData() {
        super.initData();
        //因为这里是公用的修改密码的布局，这里不需要这俩个view则隐藏
        mOldPwdLayout.setVisibility(View.GONE);
        mOldPwdLine.setVisibility(View.GONE);
        initSms();
    }

    private  void initSms(){

        //init smsSDK
        SMSSDK.initSDK(aty, AppContext.ShareSDKAppKey, AppContext.ShareSDKAppSecret);
        //打开注册页面
        RegisterPage registerPage = new RegisterPage();
        handler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    phone = (String) phoneMap.get("phone");
                    ViewInject.toast("请修改密码");
//                    String country = (String) phoneMap.get("country");
                }
            }
        };
        registerPage.setRegisterCallback(handler);
        registerPage.setBackCallback(new RegisterPage.BackCallback() {
            @Override
            public void back() {
                ViewInject.toast("返回");
                aty.finish();
            }
        });
        registerPage.show(aty);
    }

    @Override
    public void onChange() {
        super.onChange();
        setTitleType(TitleBarActivity.TitleBarType.Titlebar2);
        setBackImage(R.drawable.selector_title_back);
        setTitle("找回密码");
        setMenuImage(null);
    }

    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.modifypwd_confirm:

                modify();

                break;
        }
    }

    private void modify() {
        String old = mOldPwd.getText().toString();
        final String newPwd = mNewPwd.getText().toString();
        String confirm = mConfirNewPwd.getText().toString();


        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(newPwd);
        if(!matcher.matches()){
            ViewInject.toast("密码格式不正确");
            return;
        }
        NaidouApi.setNewPwd(phone,newPwd, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                if (Response.getSuccess(t)) {
                    KJLoger.debug("modifyPwd:" + t);
                    ViewInject.toast("修改成功");
                    writeToSP(newPwd);
                    aty.finish();
                }
            }
        });

    }

    /**
     * 写入本地保存
     */
    private void writeToSP(String password) {
        //加密密码
        password = CryptoUtil.encrypto(password);
        PreferenceHelper.write(aty, LoginActivity.TAG, "login_password", password);
    }

    @Override
    public void onBackClick() {
        super.onBackClick();
        aty.finish();
    }

    @Override
    public void onDestroy() {
        aty = null;
        layout = null;
        mConfirm = null;
        SMSSDK.unregisterEventHandler(handler);
        super.onDestroy();
    }
}
