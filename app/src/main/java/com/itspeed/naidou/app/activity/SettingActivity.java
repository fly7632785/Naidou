package com.itspeed.naidou.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itspeed.naidou.R;
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;
import com.itspeed.naidou.app.util.DataCleanManager;
import com.itspeed.naidou.app.util.RightsManager;
import com.itspeed.naidou.app.util.UIHelper;
import com.itspeed.naidou.app.util.UpdateManager;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.KJActivityStack;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;

/**
 * Created by jafir on 15/9/27.
 * 设置界面的activity
 * 装载多个fragment进行切换
 * 包括编辑资料、密码修改、清理缓存、检查更新、意见反馈、关于我们
 */
public class SettingActivity extends  TitleBarActivity{

    @BindView(id = R.id.setting_editinfo ,click = true)
    private RelativeLayout mRlEdit;
    @BindView(id = R.id.setting_modifypassword,click = true)
    private RelativeLayout mRlPassword;
    @BindView(id = R.id.setting_feedback,click = true)
    private RelativeLayout mRlFeedback;
    @BindView(id = R.id.setting_about,click = true)
    private RelativeLayout mRlAbout;
    @BindView(id = R.id.setting_clear,click = true)
    private RelativeLayout mRlClear;
    @BindView(id =R.id.setting_update,click = true)
    private RelativeLayout mRlUpdate;
    @BindView(id = R.id. setting_login_out,click = true)
    private ImageView logout;
    @BindView(id = R.id.setting_cache)
    private TextView mCache;




    @Override
    public void setRootView() {
        setContentView(R.layout.aty_setting);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBarType(TitleBarType.Titlebar2);
        mImgBack.setImageResource(R.drawable.selector_title_back);
        mTvTitle.setText("设置");
        mImgMenu.setImageBitmap(null);

//        KJLoger.debug("SettingActivity" + "onCreate");

    }

    @Override
    public void initData() {
        super.initData();
        try {
            mCache.setText(DataCleanManager.getTotalCacheSize(SettingActivity.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        KJLoger.debug("SettingActivity" + "onCreate");
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);

        switch (v.getId()){
            case R.id.setting_editinfo:
                if(RightsManager.isVisitor(aty)) {
                    return;
                }
                UIHelper.showEditInfo(aty);
                break;
            case R.id.setting_modifypassword:
                if(RightsManager.isVisitor(aty)) {
                    return;
                }
                UIHelper.showModifyPwd(aty);
                break;
            case R.id.setting_feedback:
                UIHelper.showFeedBack(aty);
                break;
            case R.id.setting_clear:

                DataCleanManager.clearAllCache(this);
                try {
                    mCache.setText(DataCleanManager.getTotalCacheSize(SettingActivity.this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, "清理成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_about:
                UIHelper.showAbout(aty);
                break;

            case  R.id.setting_update:
                UpdateManager manager = new UpdateManager(aty);
                manager.start();
                break;
            case R.id.setting_login_out:
                if(RightsManager.isVisitor(aty)) {
                    return;
                }
                NaidouApi.logout(new HttpCallBack() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        KJLoger.debug("logout:" + t);
                        if (Response.getSuccess(t)) {
                            ViewInject.toast("注销成功");
                            KJActivityStack.create().finishAllActivity();
                            Intent intent = new Intent(aty, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            aty.startActivity(intent);
                            aty.finish();
                        }
                    }
                });
                    break;
        }
    }

    @Override
    protected void onBackClick() {
        super.onBackClick();
        this.finish();
    }

    @Override
    protected void onDestroy() {
        setContentView(R.layout.view_null);
        mTvRight = null;
        mTvTitle = null;
        mCache = null;
        mRlAbout = null;
        mRlClear = null;
        mRlEdit = null;
        mRlFeedback = null;
        mRlAbout = null;
        mRlUpdate= null;
        mRlPassword = null;
        mImgBack  = null;
        mImgMenu = null;
        mRlTitleBar = null;
        System.gc();
        super.onDestroy();
    }


}

