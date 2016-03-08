package com.itspeed.naidou.app.activity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.util.LayoutUtils;
import com.sevenheaven.segmentcontrol.SegmentControl;

/**
 *
 * 带titlebar的应用Activity基类
 * 
 * @author Jafir
 * @since 2015-9
 */
public abstract class TitleBarActivity extends BaseActivity {

    private static final int IMG_SIZW = 23;
    //titleBar 左边后退按钮
    public ImageView mImgBack;
    //titleBar 中间标题
    public TextView mTvTitle;
    //titleBar 右边菜单按钮
    public ImageView mImgMenu;
    //titleBar
    public RelativeLayout mRlTitleBar;
    //titleBar 中间的segment选项按钮
    public SegmentControl segmentControl;
    //titleBar 右边的text
    public TextView mTvRight;

    /**
     * titleBar类型  目前只有两种
     */
    public enum  TitleBarType{
        Titlebar1,Titlebar2,Titlebar3
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //设置屏幕方向 竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //设置为重力感应适应
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        //设置软键盘输入适应模式
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);



        mRlTitleBar = (RelativeLayout) findViewById(R.id.main_title);
        try {
            mRlTitleBar = (RelativeLayout) findViewById(R.id.main_title);
            segmentControl = (SegmentControl) findViewById(R.id.segment_control);
            mImgBack = (ImageView) findViewById(R.id.titlebar_img_back);
            mTvTitle = (TextView) findViewById(R.id.titlebar_text_title);
            mImgMenu = (ImageView) findViewById(R.id.titlebar_img_menu);
            mTvRight = (TextView) findViewById(R.id.titlebar_text_right);

            //设置左右按钮的图片大小
            int size = (int)(getResources().getDisplayMetrics().density*IMG_SIZW);
            //android:adjustViewBounds 是否保持宽高比。
            //需要与maxWidth、MaxHeight一起使用，否则单独使用没有效果。
            mImgMenu.setAdjustViewBounds(true);
            mImgBack.setAdjustViewBounds(true);
            //设置最大宽高
            mImgMenu.setMaxWidth(size);
            mImgBack.setMaxWidth(size);
            mImgBack.setMaxHeight(size);
            mImgMenu.setMaxHeight(size);
            //设置监听
            mImgBack.setOnClickListener(this);
            mImgMenu.setOnClickListener(this);
            mTvTitle.setOnClickListener(this);
            mTvRight.setOnClickListener(this);

            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                //设置状态栏 包括颜色改变
                LayoutUtils.init(aty, mRlTitleBar, R.color.topic_red);
            }

            //设置segment监听
            segmentControl.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
                @Override
                public void onSegmentControlClick(int index) {
                    onSegmentClick(index);
                }
            });

        } catch (NullPointerException e) {
            throw new NullPointerException(
                    "TitleBar Notfound from Activity layout");
        }
    }

    /**
     * 设置Title的type
     * @param type 类型  3种
     * 第一种：就是左右图标，中间是segment的选择控件
     * 第二种：左右图标，中间文字
     * 第三种：只有左边图标，和右边是文字
     */
    public  void setTitleBarType(TitleBarType type){
        if(type == TitleBarType.Titlebar1){
            mTvRight.setVisibility(View.GONE);
            segmentControl.setVisibility(View.VISIBLE);
            mTvTitle.setVisibility(View.GONE);
        }else if (type == TitleBarType.Titlebar2){
            mTvRight.setVisibility(View.GONE);
            mTvTitle.setVisibility(View.VISIBLE);
            segmentControl.setVisibility(View.GONE);
        }else if (type == TitleBarType.Titlebar3){
            mTvTitle.setVisibility(View.GONE);
            mImgMenu.setVisibility(View.GONE);
            mTvRight.setVisibility(View.VISIBLE);
            segmentControl.setVisibility(View.GONE);
        }
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
        case R.id.titlebar_img_back:
            onBackClick();
            break;
        case R.id.titlebar_img_menu:
            onMenuClick();
            break;
        case R.id.titlebar_text_title:
            onTitleClick();
            break;
        case R.id.titlebar_text_right:
            onRightTextClick();
            break;
        default:
            break;
        }
    }

    /**
     * 给子类去实现，右边文字的点击（举报）
     */
    protected void onRightTextClick(){}

    /**
     * 右边图标的点击（菜单键）
     */
    protected void onMenuClick() {}

    /**
     * 中间Title的点击（双击中间  跳到顶部）
     */
    protected void onTitleClick(){}

    /**
     * 左边图标的点击（返回键）
     */
    protected void onSegmentClick(int index){}

    /**
     * 中间segment的选择 （父母孩子）
     */
    protected void onBackClick() {}


    @Override
    protected void onDestroy() {
        setContentView(R.layout.view_null);
        mTvRight = null;
        mTvTitle = null;
        mImgMenu = null;
        mRlTitleBar = null;
        mImgBack = null;
        super.onDestroy();
    }

}
