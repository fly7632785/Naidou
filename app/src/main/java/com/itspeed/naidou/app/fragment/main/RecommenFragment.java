package com.itspeed.naidou.app.fragment.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.activity.MainActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.fragment.TitleBarSupportFragment;
import com.itspeed.naidou.app.fragment.recommend.RecommendChideFragment;

import org.kymjs.kjframe.ui.SupportFragment;
import org.kymjs.kjframe.ui.ViewInject;

/**
 * Created by jafir on 15/9/1.
 * 推荐 的fragment
 * 里面是用了一个开源框架 recyclerViewpager
 * 因为有切换的动画，重写了onscroll 和 onlayoutchange
 */
public class RecommenFragment extends TitleBarSupportFragment {

    MainActivity aty;
    private View layout;
    private static Handler handler;
    private RecommendChideFragment mChide;
//    private RecommendLiaodeFragment mLiaode;

    @Override
    public void onChange() {
        super.onChange();
        setTitleType(TitleBarActivity.TitleBarType.Titlebar2);
        setTitle("推荐");
        setBackImage(null);
        setMenuImage(null);
    }



    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        aty = (MainActivity) getActivity();
        onChange();
        layout = View.inflate(aty, R.layout.frag_recommend, null);
        return layout;
    }

    /**
     * 给外部调用 刷新数据
     */
    public void updata(){
        if(mChide == null){
            return;
        }
        mChide.update();
    }

    @Override
    protected void initData() {
        super.initData();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 1) {
                    ViewInject.toast("功能开发中...");
//                    changeFragment(R.id.recommend_fl,mLiaode,false);
                }else if(msg.what == 2){
                    changeFragment(R.id.recommend_fl, mChide,true);
                }
            }
        };

        mChide = new RecommendChideFragment(handler);
//        mLiaode = new RecommendLiaodeFragment(handler);
        super.changeFragment(R.id.recommend_fl, mChide);
    }

    /**
     * @param resView
     * @param targetFragment
     */
    public void changeFragment(int resView, SupportFragment targetFragment,boolean ischide) {
        if (!targetFragment.equals(this.currentSupportFragment)) {

            android.support.v4.app.FragmentTransaction transaction = outsideAty.getSupportFragmentManager().beginTransaction();
            if(ischide) {
                transaction.setCustomAnimations(R.anim.slide_in_top,R.anim.slide_out_bottom);
            }else {
                transaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);

            }
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
