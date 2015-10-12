package com.itspeed.naidou.app.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.itspeed.naidou.app.AppContext;
import com.itspeed.naidou.app.activity.TitleBarActivity;

import org.kymjs.kjframe.ui.SupportFragment;

/**
 * Created by jafir on 15/7/3.
 * fragment 的titlebarfragment 基类
 */
public abstract class TitleBarSupportFragment extends SupportFragment {

    protected SupportFragment currentSupportFragment;

    /**
     * 封装一下方便一起返回
     *
     */
    public class ActionBarRes {
        public CharSequence title;
        public int backImageId;
        public Drawable backImageDrawable;
        public int menuImageId;
        public Drawable menuImageDrawable;
    }

    private final ActionBarRes actionBarRes = new ActionBarRes();
    protected TitleBarActivity outsideAty;
    protected AppContext app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getActivity() instanceof TitleBarActivity) {
            outsideAty = (TitleBarActivity) getActivity();
        }
        app = (AppContext) getActivity().getApplication();
        super.onCreate(savedInstanceState);
    }

    /**
     * 方便Fragment中设置ActionBar资源
     *
     * @param actionBarRes
     * @return
     */
    protected void setActionBarRes(ActionBarRes actionBarRes) {
    }

    /**
     * 当ActionBar上的返回键被按下时
     */
    public void onBackClick() {
    }

    /**
     * 当ActionBar上的菜单键被按下时
     */
    public void onMenuClick() {
    }


    /**
     * 当ActionBar上的标题被按下时
     */
    public void onTitleClick() {
    }


    /**
     * 当ActionBar上的segment被按下时
     */
    public void onSegmentClick(int index) {
    }

    /**
     * 设置标题
     *
     * @param text
     */
    protected void setTitle(CharSequence text) {
        if (outsideAty != null) {
            outsideAty.mTvTitle.setText(text);
        }
    }

    /**
     * 设置返回键图标
     */
    protected void setBackImage(int resId) {
        if (outsideAty != null) {
            outsideAty.mImgBack.setImageResource(resId);
        }
    }


    /**
     * 设置返回键图标
     */
    protected void setBackImage(Drawable drawable) {
        if (outsideAty != null) {
            outsideAty.mImgBack.setImageDrawable(drawable);
        }
    }

    /**
     * 设置菜单键图标
     */
    protected void setMenuImage(int resId) {
        if (outsideAty != null) {
            outsideAty.mImgMenu.setImageResource(resId);
        }
    }

    /**
     * 设置菜单键图标
     */
    protected void setMenuImage(Drawable drawable) {
        if (outsideAty != null) {
            outsideAty.mImgMenu.setImageDrawable(drawable);
        }
    }

    /**
     * 设置titleBar类型
     *
     * @param titleType
     */
    protected void setTitleType(TitleBarActivity.TitleBarType titleType) {
        if (outsideAty != null) {
            outsideAty.setTitleBarType(titleType);
        }
    }


    /**
     * @param resView
     * @param targetFragment
     */
    public void changeFragment(int resView, SupportFragment targetFragment) {
        if (!targetFragment.equals(this.currentSupportFragment)) {
            android.support.v4.app.FragmentTransaction transaction = outsideAty.getSupportFragmentManager().beginTransaction();
            if (!targetFragment.isAdded()) {
                transaction.add(resView, targetFragment, targetFragment.getClass().getName());
            }

            if (targetFragment.isHidden()) {
                transaction.show(targetFragment);
            }

            if (this.currentSupportFragment != null && this.currentSupportFragment.isVisible()) {
                transaction.hide(this.currentSupportFragment);
            }

            this.currentSupportFragment = targetFragment;
            transaction.commit();
        }
    }
}
