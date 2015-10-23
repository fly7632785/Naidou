package com.itspeed.naidou.app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.fragment.ChideFragment;
import com.itspeed.naidou.app.fragment.GuangdeFragment;
import com.itspeed.naidou.app.fragment.LiaodeFragment;
import com.itspeed.naidou.app.fragment.RecommenFragment;
import com.itspeed.naidou.app.fragment.TitleBarSupportFragment;
import com.itspeed.naidou.app.fragment.WodeFragment;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.SupportFragment;
import org.kymjs.kjframe.utils.DensityUtils;

/**
 * 应用的主界面，包括5个模块  吃的、聊的、推荐、逛的、我的
 * 一个activity 用hide show 方式保存 5个fragment
 */
public class MainActivity extends TitleBarActivity {
    /**
     * tab
     */
    //tab图片宽高
    private static final float IMG_WIDTH = 23;
    private static final float IMG_HEIGHT = 23;

    //tab
    @BindView(id = R.id.tab_menu_chide)
    private TextView mTvChide;
    @BindView(id = R.id.tab_menu_liaode)
    private TextView mTvLiaode;
    @BindView(id = R.id.tab_menu_guangde)
    private TextView mTvGuangde;
    @BindView(id = R.id.tab_menu_wode)
    private TextView mTvWode;
    @BindView(id = R.id.tab_menu_mid)
    private ImageView mImgRecommend;
    //tab layout
    @BindView(id = R.id.ly_tab_menu_chide, click = true)
    private LinearLayout mMenuChide;
    @BindView(id = R.id.ly_tab_menu_liaode, click = true)
    private LinearLayout mMenuLiaode;
    @BindView(id = R.id.ly_tab_menu_guangde, click = true)
    private LinearLayout mMenuGuangde;
    @BindView(id = R.id.ly_tab_menu_wode, click = true)
    private LinearLayout mMenuWode;
    @BindView(id = R.id.ly_tab_menu_mid, click = true)
    private LinearLayout mMenuMid;


    //吃的
    private ChideFragment mChide;
    //聊的
    private LiaodeFragment mLiaode;
    //逛的
    private GuangdeFragment mGuangde;
    //我的
    private WodeFragment mWode;
    //推荐
    private RecommenFragment mRecommend;
    /**
     * 现在的fragment
     */
    private TitleBarSupportFragment currentFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //创建5个模块fragment
        mChide = new ChideFragment();
        mGuangde = new GuangdeFragment();
        mWode = new WodeFragment();
        mLiaode = new LiaodeFragment();
        mRecommend = new RecommenFragment();

        //初始化
        changeFragment(R.id.main_content, mRecommend);
        currentFragment = mRecommend;

    }


    @Override
    public void initData() {
        super.initData();
        //设置tab中图片的大小
        setImgSize(mTvChide);
        setImgSize(mTvLiaode);
        setImgSize(mTvGuangde);
        setImgSize(mTvWode);

        //初始化选中中间图标
        mMenuMid.setSelected(true);

//        MenuObject send0 = new MenuObject("");
//        MenuObject send1 = new MenuObject("Send message");
//        MenuObject send2 = new MenuObject("Like profile");
//        MenuObject send3 = new MenuObject("Add to friends");
//        MenuObject send4 = new MenuObject("Add to favorites");
//        MenuObject send5 = new MenuObject("Block user");
//
//        send0.setResource(R.mipmap.icn_close);
//        send1.setResource(R.mipmap.icn_1);
//        send2.setResource(R.mipmap.icn_2);
//        send3.setResource(R.mipmap.icn_3);
//        send4.setResource(R.mipmap.icn_4);
//        send5.setResource(R.mipmap.icn_5);
//        menuObjects.add(send0);
//        menuObjects.add(send1);
//        menuObjects.add(send2);
//        menuObjects.add(send3);
//        menuObjects.add(send4);
//        menuObjects.add(send5);
//
//        MenuParams pa = new MenuParams();
//        pa.setActionBarSize(DensityUtils.dip2px(this, 48));
//        pa.setAnimationDuration(50);
//        pa.setMenuObjects(menuObjects);
//        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(pa);
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);

        switch (v.getId()) {
            case R.id.ly_tab_menu_chide:
                //主动调用相应fragment的change方法，改变不同的titleBar
                mChide.onChange();
                //重置所有，并设置为选中
                setSelected();
                mTvChide.setSelected(true);
                //改变内容
                changeFragment(R.id.main_content, mChide);
                //设置现在的fragment
                currentFragment = mChide;

                break;
            case R.id.ly_tab_menu_liaode:
                mLiaode.onChange();
                setSelected();
                mTvLiaode.setSelected(true);
                changeFragment(R.id.main_content, mLiaode);
                currentFragment = mLiaode;

                break;
            case R.id.ly_tab_menu_guangde:
                mGuangde.onChange();
                setSelected();
                mTvGuangde.setSelected(true);
                changeFragment(R.id.main_content, mGuangde);
                currentFragment = mGuangde;

                break;
            case R.id.ly_tab_menu_wode:
                mWode.onChange();
                setSelected();
                mTvWode.setSelected(true);
                changeFragment(R.id.main_content, mWode);
                currentFragment = mWode;
                break;

            case R.id.ly_tab_menu_mid:
                mRecommend.onChange();
                setSelected();
                mImgRecommend.setSelected(true);
                changeFragment(R.id.main_content, mRecommend);
                currentFragment = mRecommend;
                break;

        }

    }


    public void changeFragment(int resView, SupportFragment targetFragment) {
        if (targetFragment.equals(currentSupportFragment)) {
            return;
        }
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();



        if (!targetFragment.isAdded()) {
            transaction.add(resView, targetFragment, targetFragment.getClass()
                    .getName());
        }

        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
            targetFragment.onChange();
        }

        if (currentSupportFragment != null
                && currentSupportFragment.isVisible()) {
            transaction.hide(currentSupportFragment);
        }
        currentSupportFragment = targetFragment;
        transaction.commit();
    }
    @Override
    public void setRootView() {
        setContentView(R.layout.aty_main);
    }


    /**
     * 设置tab里面每个图片的大小
     * @param textView
     */
    private void setImgSize(TextView textView) {
        Drawable d = textView.getCompoundDrawables()[1];
        d.setBounds(0, 0, DensityUtils.dip2px(this, IMG_WIDTH), DensityUtils.dip2px(this, IMG_HEIGHT));
        textView.setCompoundDrawables(null, d, null, null);
    }

    /**
     * 重置所有选中状态
     */
    private void setSelected() {
        mTvChide.setSelected(false);
        mTvLiaode.setSelected(false);
        mTvGuangde.setSelected(false);
        mTvWode.setSelected(false);
        mImgRecommend.setSelected(false);
    }




    //把点击事件分发到现在的fragment

    @Override
    protected void onBackClick() {
        super.onBackClick();
        currentFragment.onBackClick();

    }

    @Override
    protected void onMenuClick() {
        super.onMenuClick();
        currentFragment.onMenuClick();
    }

    @Override
    protected void onTitleClick() {
        super.onTitleClick();
        currentFragment.onTitleClick();
    }

    /**
     * 点击标题栏的segment
     * @param index
     */
    @Override
    protected void onSegmentClick(int index) {
        super.onSegmentClick(index);
        currentFragment.onSegmentClick(index);
    }


    /**
     * 退出应用
     */
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(aty).setTitle("您要退出么？")
                .setNegativeButton("我才不呢", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("退，必须退", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        aty.finish();
                    }
                }).create().show();
    }

    /**
     *这里注释掉了super方法，目的在于防止 跳转到其他activity，因为内存不足的时候，这个mainActivity
     * 被回收了之后，然后再开启，会出现数据回滚，然后造成fragment的重叠现象，
     * 注释掉之后，防止它保存数据，就可以避免这种情况
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }
}
