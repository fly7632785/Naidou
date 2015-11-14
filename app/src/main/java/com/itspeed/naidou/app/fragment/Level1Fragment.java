package com.itspeed.naidou.app.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.itspeed.naidou.R;
import com.itspeed.naidou.app.activity.MainActivity;
import com.itspeed.naidou.model.bean.Level2Cate;

import org.kymjs.kjframe.ui.SupportFragment;
import org.kymjs.kjframe.utils.KJLoger;

import java.util.List;

/**
 * Created by jafir on 15/9/24.
 * 一级菜单的fragment
 */
public class Level1Fragment extends SupportFragment {

    //indicator的字体大小
    protected static final float INDICATOR_FONT_SIZE = 11f;

    protected MainActivity aty;
    protected View layout;
    protected ViewPager viewPager;
    //indicator
    protected PagerSlidingTabStrip pagerSlidingTabStrip;

    protected String [] titles;
    protected List<Level2Fragment> fragments;

    /**
     * 2级菜单标题  用于动态加载
     */
    protected Level2Cate level2Cate;



    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        aty = (MainActivity) getActivity();
        layout = View.inflate(aty, R.layout.frag_chide_base,null);
        KJLoger.debug(this.getClass().getSimpleName()+"inflaterView");
        return  layout;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KJLoger.debug(this.getClass().getSimpleName()+"onCreate");
    }


    @Override
    public void onPause() {
        super.onPause();
        KJLoger.debug(this.getClass().getSimpleName() + "onPause");
    }


    @Override
    public void onStop() {
        super.onStop();
        KJLoger.debug(this.getClass().getSimpleName() + "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KJLoger.debug(this.getClass().getSimpleName() + "onDestroy");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        KJLoger.debug(this.getClass().getSimpleName()+"onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);

    }
}
