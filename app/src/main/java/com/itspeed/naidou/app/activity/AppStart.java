package com.itspeed.naidou.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.AppContext;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.utils.DensityUtils;
import org.kymjs.kjframe.utils.PreferenceHelper;

/**
 * Created by jafir on 10/15/15.
 * 应用程序的开始和入口，有一个动画界面
 */
public class AppStart extends KJActivity{

    public static String TAG = "appstart";
    private KJHttp mKjh;
    private ImageView image;


    @Override
    public void setRootView() {
        image = new ImageView(aty);
        image.setImageResource(R.mipmap.start);
        Animation anim = AnimationUtils.loadAnimation(aty, R.anim.splash_start);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                jumpTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        image.setAnimation(anim);
        setContentView(image);
        AppContext.screenH = DensityUtils.getScreenH(aty);
        AppContext.screenW = DensityUtils.getScreenW(aty);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置成没有标题
        Window window = this.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 让屏幕高亮
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        mKjh = new KJHttp(config);
    }

    /**
     * 判断是不是第一次进入，如果是就到引导页
     */
    private void jumpTo() {
        boolean isFirst = PreferenceHelper.readBoolean(aty, TAG, "first_open",
                true);
        Intent jumpIntent = new Intent();
        if (!isFirst) {
//            isOnline();
            jumpIntent.setClass(aty, LoginActivity.class);
        } else {
//            PreferenceHelper.write(aty, TAG, "first_open", false);
            jumpIntent.setClass(aty, GuideActivity.class);
        }
        startActivity(jumpIntent);
        finish();
    }


    @Override
    protected void onDestroy() {
        setContentView(R.layout.view_null);
        image = null;
        mKjh = null;
        super.onDestroy();
    }
}
