package com.itspeed.naidou.app.fragment.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;
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

/**
 * Created by jafir on 15/9/28.
 * 设置里面 修改密码  fragment
 */
public class ModifyPwdFragment extends TitleBarSupportFragment {

    private SimpleBackActivity aty;
    private View layout;

    @BindView(id = R.id.modifypwd_confirm, click = true)
    private ImageView mConfirm;
    @BindView(id = R.id.modifypwd_oldpwd)
    private EditText mOldPwd;
    @BindView(id = R.id.modifypwd_newpwd)
    private EditText mNewPwd;
    @BindView(id = R.id.modifypwd_confirmnew)
    private EditText mConfirNewPwd;

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
    public void onChange() {
        super.onChange();
        setTitleType(TitleBarActivity.TitleBarType.Titlebar2);
        setBackImage(R.drawable.selector_title_back);
        setTitle("修改密码");
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

        if (old.equals("") || newPwd.equals("") || confirm.equals("")) {
            ViewInject.toast("不能为空");
            return;
        }
        NaidouApi.modifyPwd(old, newPwd, confirm, new HttpCallBack() {
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
        super.onDestroy();
    }
}
