package com.itspeed.naidou.app.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
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
import com.itspeed.naidou.app.adapter.Step3RecylerAdapter;
import com.itspeed.naidou.app.adapter.Step4GridViewAdapter;
import com.itspeed.naidou.model.bean.FoodMaterial;

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
    private RecyclerView mRecyclerViewStep3;
    private LinearLayout step3linear;
    private Step3RecylerAdapter step3RecylerAdapter;
    private ImageView step3next;
    private EditText step3title;
    private EditText step3desc;
    private TextView step3add;

    private ArrayList<EditText> cailiaoList = new ArrayList<>();
    private ArrayList<EditText> yongliangList = new ArrayList<>();
        //数据
    private ArrayList<FoodMaterial> step3list = new ArrayList<>();

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

//        mRecyclerViewStep3 = (RecyclerView) step3.findViewById(R.id.step3_list);
//        // 创建一个线性布局管理器
//        LinearLayoutManager layoutManager = new LinearLayoutManager(aty);
//        // 设置布局管理器
//        mRecyclerViewStep3.setLayoutManager(layoutManager);
//
//        //模拟数据
//
//        for(int i=0;i<6;i++){
//         step3list.add(new FoodMaterial());
//        }
//        // 创建Adapter，并指定数据集
//        step3RecylerAdapter = new Step3RecylerAdapter(step3list);
//        // 设置Adapter
//        mRecyclerViewStep3.setAdapter(step3RecylerAdapter);

        step3linear = (LinearLayout) step3.findViewById(R.id.step3_linear);
        //初始化4个
        View view1 = View.inflate(aty,R.layout.item_recyclerview_step3,null);
        View view2 = View.inflate(aty,R.layout.item_recyclerview_step3,null);
        View view3 = View.inflate(aty,R.layout.item_recyclerview_step3,null);
        View view4 = View.inflate(aty,R.layout.item_recyclerview_step3,null);
        step3linear.addView(view1);
        step3linear.addView(view2);
        step3linear.addView(view3);
        step3linear.addView(view4);

        for(int i=0 ; i < step3linear.getChildCount();i++ ){
            EditText cailiao = (EditText) step3linear.getChildAt(i).findViewById(R.id.item_recycler_step3_cailiao);
            EditText yongliang = (EditText)step3linear.getChildAt(i).findViewById(R.id.item_recycler_step3_yongliang);
            cailiaoList.add(cailiao);
            yongliangList.add(yongliang);

            ImageView delete = (ImageView) step3linear.getChildAt(i).findViewById(R.id.item_recycler_step3_delete);
            if(i == 0){
                cailiao.setHint("例如:猪肉");
                yongliang.setHint("例如:500g");
            }
            delete.setOnClickListener(this);
        }

        step3add = (TextView) step3.findViewById(R.id.step3_add);
        step3desc = (EditText) step3.findViewById(R.id.step3_describe);
        step3title = (EditText) step3.findViewById(R.id.step3_title);
        step3next = (ImageView) step3.findViewById(R.id.step3_next);

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
//                for(int i=0;i<step3list.size();i++){
//                    FoodMaterial foodMaterial = step3list.get(i);
//                    KJLoger.debug(foodMaterial.getAmount()+":"+foodMaterial.getType());
//                }

                //打印   整理出来的数据
                for (int i=0;i<yongliangList.size();i++){
                    KJLoger.debug("材料"+cailiaoList.get(i).getText()+":" +yongliangList.get(i).getText());
                }
                break;
            case R.id.step3_add:
                //增加一栏
//                step3RecylerAdapter.addData();

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
                //删除
                KJLoger.debug(":"+v.getParent());
                step3linear.removeView((View) v.getParent().getParent());
                yongliangList.remove(((View) v.getParent().getParent()).findViewById(R.id.item_recycler_step3_yongliang));
                cailiaoList.remove(((View) v.getParent().getParent()).findViewById(R.id.item_recycler_step3_cailiao));
                break;


        }

    }

    /**
     *step3里面增加一栏
     */
    private void step3add() {
        View view = View.inflate(aty, R.layout.item_recyclerview_step3, null);
        EditText cailiao = (EditText) view.findViewById(R.id.item_recycler_step3_cailiao);
        EditText yongliang = (EditText)view.findViewById(R.id.item_recycler_step3_yongliang);
        cailiaoList.add(cailiao);
        yongliangList.add(yongliang);
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
