package com.itspeed.naidou.app.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.fragment.PublishCookbookFragment;
import com.itspeed.naidou.app.util.LayoutUtils;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

/**
 * Created by jafir on 15/10/8.
 * 发布菜谱的activity，这是一个单独的activity，我们把它独立出来，因为它特别的复杂
 * 里面装载一个viewpager来进行菜谱步骤的展示和数据修改
 */
public class PublishActivity extends KJActivity {

    private PublishCookbookFragment publishCookbookFragment;
    @BindView(id = R.id.publish_view)
    private RelativeLayout mView;

    @Override
    public void setRootView() {
        int mode = WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
        getWindow().setSoftInputMode(mode);
        setContentView(R.layout.aty_publish);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);




        //设置状态栏 包括颜色改变
        LayoutUtils.init1(aty, mView, R.color.topic_red);

        publishCookbookFragment = new PublishCookbookFragment();
        changeFragment(R.id.publish_frame, publishCookbookFragment);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            publishCookbookFragment.backToHome();
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        setContentView(R.layout.view_null);
        publishCookbookFragment = null;
        mView = null;
        super.onDestroy();
    }
}
