package com.itspeed.naidou.app.activity;

import com.itspeed.naidou.R;

/**
 * Created by jafir on 15/9/27.
 */
public class SettingActivity extends  TitleBarActivity{


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void initData() {
        super.initData();
        setTitleBarType(TitleBarType.Titlebar2);
        mImgBack.setImageResource(R.drawable.selector_common_back);
        mTvTitle.setText("设置");
        mImgMenu.setImageBitmap(null);
    }



}
