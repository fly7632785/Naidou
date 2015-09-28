package com.itspeed.naidou.app.activity;

import android.os.Bundle;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBarType(TitleBarType.Titlebar2);
        mImgBack.setImageResource(R.drawable.selector_common_back);
        mTvTitle.setText("设置");
        mImgMenu.setImageBitmap(null);
    }

    @Override
    public void initData() {
        super.initData();

    }


    @Override
    protected void onBackClick() {
        super.onBackClick();
        this.finish();
    }
}
