package com.itspeed.naidou.app.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.activity.PublishActivity;
import com.itspeed.naidou.app.adapter.PublishPagerAdapter;
import com.itspeed.naidou.app.adapter.Step4GridViewAdapter;

import org.kymjs.kjframe.ui.SupportFragment;
import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;

/**
 * Created by jafir on 15/10/8.
 */
public class PublishCookbookFragment extends SupportFragment {


    private PublishActivity aty;
    private View layout;

    //viewpager
    private ViewPager mViewPager;
    private ArrayList<View> steps = new ArrayList<>();
    private PublishPagerAdapter adapter;

    //step1
    private RelativeLayout title1;
    private View step1;
    private ImageView parent;
    private ImageView child;

    //step2
    private RelativeLayout title2;
    private View step2;
    private TextView cate;
    private ImageView beiyun;
    private ImageView yunchu;
    private ImageView yunzhong;
    private ImageView yunwan;
    private ImageView yuezi;

    //step3
    private RelativeLayout title3;
    private View step3;
    private LinearLayout step3linear;
    private ImageView step3next;
    private EditText step3title;
    private EditText step3desc;
    private TextView step3add;


    //step4
    private RelativeLayout title4;
    private GridView mGridView;
    private View step4;
    private Step4GridViewAdapter step4GridViewAdapter;
    private ImageView step4issue;
    private TextView step4add;



    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {

        layout = View.inflate(aty, R.layout.frag_publish_cookbook, null);
        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aty = (PublishActivity) getActivity();
    }

    @Override
    protected void initData() {
        super.initData();
        mViewPager = (ViewPager) layout.findViewById(R.id.view_pager_publish);
        initSteps();
        adapter = new PublishPagerAdapter(steps);
        mViewPager.setAdapter(adapter);
        //step1
        step1();
        //step2
        step2();
        //step3
        step3();
        //step4
        step4();
    }

    /**
     * 第四步
     */
    private void step4() {
        ImageView back = (ImageView) step4.findViewById(R.id.step4_img_back);
        ImageView menu = (ImageView) step4.findViewById(R.id.step4_img_menu);
        menu.setOnClickListener(this);
        back.setOnClickListener(this);

        mGridView = (GridView) step4.findViewById(R.id.step4_gridview);
        step4GridViewAdapter = new Step4GridViewAdapter();
        mGridView.setAdapter(step4GridViewAdapter);


        step4add = (TextView) step4.findViewById(R.id.step4_add);
        step4issue = (ImageView) step4.findViewById(R.id.step4_next);

        step4add.setOnClickListener(this);
        step4issue.setOnClickListener(this);
    }
    /**
     * 第三步
     */
    private void step3() {
        ImageView back = (ImageView) step3.findViewById(R.id.step3_img_back);
        ImageView menu = (ImageView) step3.findViewById(R.id.step3_img_menu);

        menu.setOnClickListener(this);
        back.setOnClickListener(this);


        step3linear = (LinearLayout) step3.findViewById(R.id.step3_linear);
        //初始化4个 item
        View view1 = View.inflate(aty,R.layout.item_recyclerview_step3,null);
        View view2 = View.inflate(aty,R.layout.item_recyclerview_step3,null);
        View view3 = View.inflate(aty,R.layout.item_recyclerview_step3,null);
        View view4 = View.inflate(aty,R.layout.item_recyclerview_step3,null);
        step3linear.addView(view1);
        step3linear.addView(view2);
        step3linear.addView(view3);
        step3linear.addView(view4);

        //为这个4个item的delete设置监听
        for(int i=0 ; i < step3linear.getChildCount();i++ ){
            EditText cailiao = (EditText) step3linear.getChildAt(i).findViewById(R.id.item_recycler_step3_cailiao);
            EditText yongliang = (EditText)step3linear.getChildAt(i).findViewById(R.id.item_recycler_step3_yongliang);
            ImageView delete = (ImageView) step3linear.getChildAt(i).findViewById(R.id.item_recycler_step3_delete);
            //第一栏的hint提示
            if(i == 0){
                cailiao.setHint("例如:猪肉");
                yongliang.setHint("例如:500g");
            }
            delete.setOnClickListener(this);
        }

        //初始化
        step3add = (TextView) step3.findViewById(R.id.step3_add);
        step3desc = (EditText) step3.findViewById(R.id.step3_describe);
        step3title = (EditText) step3.findViewById(R.id.step3_title);
        step3next = (ImageView) step3.findViewById(R.id.step3_next);
        //增加 和 下一步 按钮设置监听
        step3add.setOnClickListener(this);
        step3next.setOnClickListener(this);


    }
    /**
     * 第二步
     */
    private void step2() {
        ImageView back = (ImageView) step2.findViewById(R.id.step2_img_back);
        ImageView menu = (ImageView) step2.findViewById(R.id.step2_img_menu);
        menu.setOnClickListener(this);
        back.setOnClickListener(this);

        cate = (TextView) step2.findViewById(R.id.step2_cate);
        beiyun = (ImageView) step2.findViewById(R.id.step2_beiyun);
        yunchu = (ImageView) step2.findViewById(R.id.step2_yunchu);
        yunzhong = (ImageView) step2.findViewById(R.id.step2_yunzhong);
        yunwan = (ImageView) step2.findViewById(R.id.step2_yunwan);
        yuezi = (ImageView) step2.findViewById(R.id.step2_yuezi);
        beiyun.setOnClickListener(this);
        yunchu.setOnClickListener(this);
        yunzhong.setOnClickListener(this);
        yunwan.setOnClickListener(this);
        yuezi.setOnClickListener(this);
    }

    /**
     * 第一步
     */
    private void step1() {
        ImageView menu = (ImageView) step1.findViewById(R.id.step1_img_menu);
        menu.setOnClickListener(this);
        parent = (ImageView) step1.findViewById(R.id.step1_parent);
        child = (ImageView) step1.findViewById(R.id.step1_child);
        parent.setOnClickListener(this);
        child.setOnClickListener(this);
    }

    /**
     * 初始化viewpager的view
     */
    private void initSteps() {
        step1 = View.inflate(aty, R.layout.view_step1, null);
        step2 = View.inflate(aty, R.layout.view_step2, null);
        step3 = View.inflate(aty, R.layout.view_step3, null);
        step4 = View.inflate(aty, R.layout.view_step4, null);
        steps.add(step1);
        steps.add(step2);
        steps.add(step3);
        steps.add(step4);
    }


    /**
     * 点击事件
     * @param v
     */
    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {

            /**
             * 1-4步的title栏图标点击事件
             */
            case R.id.step1_img_menu:
                backToHome();
                break;
            case R.id.step2_img_menu:
                backToHome();
                break;
            case R.id.step2_img_back:
                mViewPager.setCurrentItem(0, true);
                break;
            case R.id.step3_img_menu:
                backToHome();
                break;
            case R.id.step3_img_back:
                mViewPager.setCurrentItem(1, true);
                break;
            case R.id.step4_img_menu:
                backToHome();
                break;
            case R.id.step4_img_back:
                mViewPager.setCurrentItem(2, true);
                break;




            //step1
            case R.id.step1_parent:
                mViewPager.setCurrentItem(1, true);
                selectParent();
                cate.setText("父母");
                break;
            case R.id.step1_child:
                mViewPager.setCurrentItem(1, true);
                cate.setText("孩子");
                selectChild();
                break;

            //step2
            case R.id.step2_yunchu:
                mViewPager.setCurrentItem(2, true);
                break;
            case R.id.step2_yunzhong:
                mViewPager.setCurrentItem(2, true);
                break;
            case R.id.step2_yunwan:
                mViewPager.setCurrentItem(2, true);
                break;
            case R.id.step2_yuezi:
                mViewPager.setCurrentItem(2, true);
                break;
            case R.id.step2_beiyun:
                mViewPager.setCurrentItem(2, true);
                break;

            //step3
            case R.id.step3_next:
                mViewPager.setCurrentItem(3,true);
                //打印   整理出来的数据
                for(int i=0;i< step3linear.getChildCount();i++){
                    EditText cailiao = (EditText) step3linear.getChildAt(i).findViewById(R.id.item_recycler_step3_cailiao);
                    EditText yongliang = (EditText) step3linear.getChildAt(i).findViewById(R.id.item_recycler_step3_yongliang);
                    KJLoger.debug("第二种111材料"+cailiao.getText()+":" +yongliang.getText());
                }
                break;
            case R.id.step3_add:
                //增加一栏
                step3add();
                break;

            //step4
            case R.id.step4_next:
                //发布
                break;
            case R.id.step4_add:
                //增加一栏
                step4GridViewAdapter.addDate();
                break;


            case R.id.item_recycler_step3_delete:
                //删除 item
                step3linear.removeView((View) v.getParent().getParent());
                break;


        }

    }

    /**
     *step3里面增加一栏
     */
    private void step3add() {
        View view = View.inflate(aty, R.layout.item_recyclerview_step3, null);
        ImageView delete = (ImageView) view.findViewById(R.id.item_recycler_step3_delete);
        delete.setOnClickListener(this);
        step3linear.addView(view);
    }

    /**
     * 改变step2的选择  孩子
     */
    private void selectChild() {
        beiyun.setImageResource(R.drawable.selector_publish_1_2);
        yunchu.setImageResource(R.drawable.selector_publish_3_4);
        yunzhong.setImageResource(R.drawable.selector_publish_4_5);
        yunwan.setImageResource(R.drawable.selector_publish_6_8);
        yuezi.setImageResource(R.drawable.selector_publish_9_12);

    }
    /**
     * 改变step2的选择  父母
     */
    private void selectParent() {
        beiyun.setImageResource(R.drawable.selector_publish_beiyun);
        yunchu.setImageResource(R.drawable.selector_publish_yunchu);
        yunzhong.setImageResource(R.drawable.selector_publish_yunzhong);
        yunwan.setImageResource(R.drawable.selector_publish_yunwan);
        yuezi.setImageResource(R.drawable.selector_publish_yuezi);
    }

    /**
     * 返回主页的dialog
     */
    public void backToHome() {
        AlertDialog.Builder builder = new AlertDialog.Builder(aty);
        builder.setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                aty.finish();
            }
        }).setTitle("放弃添加，回到主界面").create().show();
    }



}
