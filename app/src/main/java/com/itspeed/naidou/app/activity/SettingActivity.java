package com.itspeed.naidou.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.util.UIHelper;

import org.kymjs.kjframe.ui.BindView;

/**
 * Created by jafir on 15/9/27.
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

    @BindView(id = R.id.setting_cache)
    private TextView mCache;




    @Override
    public void setRootView() {
        setContentView(R.layout.activity_setting);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBarType(TitleBarType.Titlebar2);
        mImgBack.setImageResource(R.drawable.selector_title_back);
        mTvTitle.setText("设置");
        mImgMenu.setImageBitmap(null);
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);

        switch (v.getId()){
            case R.id.setting_editinfo:
                UIHelper.showEditInfo(aty);
                break;
            case R.id.setting_modifypassword:
                UIHelper.showModifyPwd(aty);
                break;
            case R.id.setting_feedback:
                UIHelper.showFeedBack(aty);
                break;
            case R.id.setting_clear:

                break;
            case R.id.setting_about:
                UIHelper.showAbout(aty);
                break;
        }
    }

    @Override
    protected void onBackClick() {
        super.onBackClick();
        this.finish();
    }
}
