package com.itspeed.naidou.app.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.activity.PublishActivity;
import com.itspeed.naidou.app.adapter.PublishPagerAdapter;
import com.itspeed.naidou.app.adapter.Step3RecylerAdapter;
import com.itspeed.naidou.app.adapter.Step4RecylerAdapter;

import org.kymjs.kjframe.ui.SupportFragment;

import java.util.ArrayList;

/**
 * Created by jafir on 15/10/8.
 */
public class PublishCookbookFragment extends SupportFragment {


    private PublishActivity aty;
    private View layout;

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
    private RecyclerView mRecyclerViewStep3;
    //step4
    private RelativeLayout title4;
    private RecyclerView mRecyclerViewStep4;
    private View step4;


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


    private void step4() {
        ImageView back = (ImageView) step4.findViewById(R.id.step4_img_back);
        ImageView menu = (ImageView) step4.findViewById(R.id.step4_img_menu);
        menu.setOnClickListener(this);
        back.setOnClickListener(this);


        mRecyclerViewStep4 = (RecyclerView) step4.findViewById(R.id.step4_list);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerViewStep4.setLayoutManager(layoutManager);
        mRecyclerViewStep4.setAdapter(new Step4RecylerAdapter());
    }

    private void step3() {
        ImageView back = (ImageView) step3.findViewById(R.id.step3_img_back);
        ImageView menu = (ImageView) step3.findViewById(R.id.step3_img_menu);
        mRecyclerViewStep3 = (RecyclerView) step3.findViewById(R.id.step3_list);
        menu.setOnClickListener(this);
        back.setOnClickListener(this);
        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(aty);
        // 设置布局管理器
        mRecyclerViewStep3.setLayoutManager(layoutManager);

        // 创建Adapter，并指定数据集
        Step3RecylerAdapter adapter = new Step3RecylerAdapter();
        // 设置Adapter
        mRecyclerViewStep3.setAdapter(adapter);


    }

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

    private void step1() {
        ImageView menu = (ImageView) step1.findViewById(R.id.step1_img_menu);
        menu.setOnClickListener(this);
        parent = (ImageView) step1.findViewById(R.id.step1_parent);
        child = (ImageView) step1.findViewById(R.id.step1_child);
        parent.setOnClickListener(this);
        child.setOnClickListener(this);
    }

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


    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
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
            case R.id.step5_img_menu:
                backToHome();
                break;
            case R.id.step5_img_back:
                mViewPager.setCurrentItem(3, true);
                break;

            //step1
            case R.id.step1_parent:
                mViewPager.setCurrentItem(1, true);
                cate.setText("父母");
                break;
            case R.id.step1_child:
                mViewPager.setCurrentItem(1, true);
                cate.setText("孩子");
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


        }

    }

    private void backToHome() {
        AlertDialog.Builder builder = new AlertDialog.Builder(aty);
        builder.setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                aty.finish();
            }
        }).setTitle("放弃添加，回到主界面").create().show();
    }


}
