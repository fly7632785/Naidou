package com.itspeed.naidou.app.activity;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itspeed.naidou.R;
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;
import com.itspeed.naidou.app.AppContext;
import com.itspeed.naidou.app.util.CryptoUtil;
import com.itspeed.naidou.app.util.ReleaseResource;
import com.itspeed.naidou.app.util.UIHelper;
import com.itspeed.naidou.model.bean.User;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.KJActivityStack;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.PreferenceHelper;
import org.kymjs.kjframe.utils.SystemTool;

import java.util.Map;

/**
 * Created by jafir on 10/15/15.
 * 登录界面，重写onStart方法，目的在于打开界面的时候 有记住密码的功能
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
    @BindView(id = R.id.login_bg)
    private ImageView mLayoutBg;
    @BindView(id = R.id.login_account_bg)
    private ImageView mAccountBg;
    @BindView(id = R.id.login_password_bg)
    private ImageView mPasswordBg;


    private String name;
    private String password;
    private User user;

    private ProgressDialog dialog;

    @Override
    public void setRootView() {
        setContentView(R.layout.aty_login);
        //检测是否联网  提示用户
        if (!SystemTool.checkNet(aty)) {
            Toast.makeText(aty, "您的网络没有连接", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initData() {
        super.initData();
        dialog = new ProgressDialog(aty);
        dialog.setMessage("正在登录...");
        dialog.setCanceledOnTouchOutside(false);
    }


    /**
     * 每次显示页面都去 本地取出 账号密码
     * 从注册界面跳转返回的时候 直接可以获取刚注册的账号密码
     *
     */
    @Override
    protected void onStart() {
        super.onStart();
        name = PreferenceHelper.readString(aty, TAG, "login_account");
        password = PreferenceHelper.readString(aty, TAG, "login_password");
        //解密
        if (password != null) {
            password = CryptoUtil.decrypto(password);
        }

        KJLoger.debug("账号:" + name + "密码:" + password);
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
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
                AppContext.isVisitor = true;
                User user = new User();
                //设置默认 user信息
                user.setNickname("游客");
                user.setMotto("论从游客到会员的过程需要多大勇气");
                AppContext.userAvatarPath = "youkeAvatarPath";
                AppContext.user = user;
                AppContext.TOKEN = "dd3eee88b4bdcbf999d6458f10cabe2b56c4b561";
                UIHelper.showMain(aty);
                aty.finish();
                break;
            case R.id.login_sign:
                UIHelper.showRegister(aty);
                break;
            case R.id.login_in:
                login();
                break;
        }
    }

    /**
     * 检测账号密码是否合适
     *
     * @return
     */
    private boolean login() {
        name = editName.getText().toString().trim();
        password = editPassword.getText().toString().trim();

        if (name.equals("") || name == null || password == null || password.equals("")) {
            Toast.makeText(aty, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            NaidouApi.login(name, password, new HttpCallBack() {
                @Override
                public void onSuccess(Map<String, String> headers, byte[] t) {
                    super.onSuccess(headers, t);
                    String s = new String(t);
                    KJLoger.debug(s);
                    //提示登录情况
                    ViewInject.toast(Response.getMessage(s));
                    if (Response.getSuccess(s)) {

                        //设置为用户模式
                        AppContext.isVisitor = false;


                        //设置全局 token
                        writeToApplication(s);
                        //用户信息 账号密码 token写入 SP
                        writeToSP(s);
                        UIHelper.showMain(aty);
                        dialog.dismiss();
                        aty.finish();
                    }
                }

                @Override
                public void onPreStart() {
                    super.onPreStart();
                    dialog.show();
                }

                @Override
                public void onFailure(int errorNo, String strMsg) {
                    super.onFailure(errorNo, strMsg);
                    KJLoger.debug("错误：" + strMsg);
                    ViewInject.toast("错误：" + strMsg);
                    dialog.dismiss();
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    KJLoger.debug("onFinish");
                    dialog.dismiss();
                }
            });
            return false;
        }
    }

    /**
     * 把用户信息 本地化 以方便调用
     * 避免在进入界面之后再花时间去 网络请求新的用户信息 而产生时延视差
     * @param user
     */
    private void writeUserInfo2SP(User user) {
        /**
         * 这里做用户信息本地化
         */
        PreferenceHelper.write(aty, "userInfo", "avatarId",user.getAvatarId());
        PreferenceHelper.write(aty, "userInfo", "avatar", user.getAvatar());
        PreferenceHelper.write(aty, "userInfo", "nickName", user.getNickname());
        PreferenceHelper.write(aty, "userInfo", "email", user.getEmail());
        PreferenceHelper.write(aty, "userInfo", "motto", user.getMotto());
        PreferenceHelper.write(aty, "userInfo", "coins", user.getCoins());
        PreferenceHelper.write(aty, "userInfo", "follow", user.getFollowedCount());
        PreferenceHelper.write(aty, "userInfo", "uid", user.getUid());

        //下载 头像到手机
        new KJHttp().download(AppContext.userAvatarPath, AppContext.HOST+user.getAvatar(), new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("download:"+t);
                if(Response.getSuccess(t)){

                }
            }
        });
    }

    /**
     * 写入Application 全局使用
     *
     * @param t
     */
    private void writeToApplication(String t) {
        if (t != null) {
            AppContext.user = Response.getMyInfo(t);
            AppContext.TOKEN = Response.getApiKey(t);
        }
    }


    /**
     * 写入本地保存
     */
    private void writeToSP(String t) {
        PreferenceHelper.write(aty, TAG, "login_account", name);
        //加密密码
        password = CryptoUtil.encrypto(password);
        PreferenceHelper.write(aty, LoginActivity.TAG, "login_password", password);
        /**
         * 这里做token本地化
         */
        PreferenceHelper.write(aty, TAG, "apiKey", CryptoUtil.encrypto(AppContext.TOKEN));
        User user = Response.getMyInfo(t);
        writeUserInfo2SP(user);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        KJActivityStack.create().appExit(aty);
    }

    @Override
    protected void onDestroy() {
        setContentView(R.layout.view_null);
        ReleaseResource.recyclerImg(mAccountBg, mLayoutBg, mPasswordBg, login, sign);
        name = null;
        password = null;
        dialog = null;
        editName = null;
        editPassword = null;
        login = null;
        scan = null;
        findback = null;
        sign = null;
        System.gc();
        super.onDestroy();
    }
}
