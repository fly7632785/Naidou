package com.itspeed.naidou.app.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.activity.PublishActivity;
import com.itspeed.naidou.app.activity.SelectActivity;
import com.itspeed.naidou.app.adapter.PublishPagerAdapter;

import org.kymjs.kjframe.ui.SupportFragment;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;

/**
 * Created by jafir on 15/10/8.
 * 发表菜谱的fragment
 * 发布菜谱 分为4步
 * 第一步：选择 父母 还是 孩子
 * 第二步：选择 父母的二级菜单  或者 孩子的二级菜单
 * 第三步：菜谱的 标题 描述 食材
 * 第四步：菜谱的 步骤 （图片 描述）
 */
public class PublishCookbookFragment extends SupportFragment {


    private static final int TO_SELECT_PHOTO = 1;
    private PublishActivity aty;
    private View layout;

    /**
     * 4步 为4个view 装在viewpager里面
     */
    private ViewPager mViewPager;
    private ArrayList<View> steps = new ArrayList<>();
    private PublishPagerAdapter adapter;

    //step1
    private View step1;
    private ImageView parent;
    private ImageView child;
    private boolean isFinishStep1;
    private int cate1; //用户判断用户是否选择cate1   初始化为0 父母为1 孩子为2；
    //step2
    private View step2;

    private TextView cate;
    private ImageView beiyun;
    private ImageView yunchu;
    private ImageView yunzhong;
    private ImageView yunwan;
    private ImageView yuezi;
    private boolean isFinishStep2;
    private int cate2; //用户判断用户是否选择cate2
    //注意 这里是 岁 和月      4个月以前都是吃奶粉
    //  初始化为0 备孕（1-2岁）为1 ；孕初（3-4岁）为2 ；孕中（4-5月）为3；孕晚（6-8月）为4；月子（9-12月）；

    //step3
    private View step3;
    private LinearLayout step3linear;
    private ImageView step3next;
    private EditText step3title;
    private EditText step3desc;
    private TextView step3add;
    private boolean isFinishStep3;


    //step4
    private View step4;
    private LinearLayout step4linear;
    private ImageView step4issue;
    private TextView step4add;
    private TextView step4delete;

    private AlertDialog descDialog;

    private EditText descEdit;
    //用来取出点击的view 设置为全局 在dialog click里面调用
    private View viewDesc;
    private ImageView viewImg;

    private boolean isFinishStep4;


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
        //初始化4步 和 设置监听等
        step1();
        step2();
        step3();
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


        step4linear = (LinearLayout) step4.findViewById(R.id.step4_linear);
        //初始化4个 item
        View view1 = View.inflate(aty,R.layout.item_linear_step4,null);
        View view2 = View.inflate(aty,R.layout.item_linear_step4,null);
        View view3 = View.inflate(aty,R.layout.item_linear_step4,null);
        step4linear.addView(view1);
        step4linear.addView(view2);
        step4linear.addView(view3);

        //为这个3个item的delete设置监听
        for(int i=0 ; i < step4linear.getChildCount();i++ ){
            TextView desc = (TextView) step4linear.getChildAt(i).findViewById(R.id.item_linear_step4_describe);
            TextView title = (TextView) step4linear.getChildAt(i).findViewById(R.id.item_linear_step4_step);
            ImageView img = (ImageView) step4linear.getChildAt(i).findViewById(R.id.item_linear_step4_img);

            //step提示
            title.setText("" + (i + 1));
            desc.setOnClickListener(this);
            img.setOnClickListener(new MyOnClick(i+1));
        }


        step4add = (TextView) step4.findViewById(R.id.step4_add);
        step4issue = (ImageView) step4.findViewById(R.id.step4_next);
        step4delete = (TextView) step4.findViewById(R.id.step4_delete);

        step4add.setOnClickListener(this);
        step4issue.setOnClickListener(this);
        step4delete.setOnClickListener(this);






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


            /**
             * 4步的控件的点击事件
             */

            //step1
            case R.id.step1_parent:
                mViewPager.setCurrentItem(1, true);
                selectParent();
                isFinishStep1 = true;
                cate1 = 1;
                cate.setText("父母");
                break;
            case R.id.step1_child:
                mViewPager.setCurrentItem(1, true);
                cate.setText("孩子");
                selectChild();
                cate1 = 2;
                isFinishStep1 =true;
                break;

            //step2
            case R.id.step2_yunchu:
                mViewPager.setCurrentItem(2, true);
                cate2 = 1;
                isFinishStep2 = true;
                break;
            case R.id.step2_yunzhong:
                mViewPager.setCurrentItem(2, true);
                cate2 = 2;
                isFinishStep2 = true;
                break;
            case R.id.step2_yunwan:
                mViewPager.setCurrentItem(2, true);
                cate2 = 3;
                isFinishStep2 = true;
                break;
            case R.id.step2_yuezi:
                mViewPager.setCurrentItem(2, true);
                cate2 = 4;
                isFinishStep2 = true;
                break;
            case R.id.step2_beiyun:
                mViewPager.setCurrentItem(2, true);
                cate2 = 5;
                isFinishStep2 = true;
                break;

            //step3
            case R.id.step3_next:
                isFinishStep3 = checkStep3Finish();
                if(isFinishStep3) {
                    mViewPager.setCurrentItem(3, true);
                }
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

            case R.id.item_recycler_step3_delete:
                //删除 item
                step3linear.removeView((View) v.getParent().getParent());
                break;


            //step4
            case R.id.step4_next:
                //发布
                finishPublish();

                break;
            case R.id.step4_add:
                //增加一栏
//                step4GridViewAdapter.addDate();
                step4add();
                break;
            case R.id.step4_delete:
                if(step4linear.getChildCount() > 0) {
                    step4linear.removeViewAt(step4linear.getChildCount() - 1);
                }
                break;
            /***这里的专门写了一个类去使用 因为需要position*/
//            case R.id.item_linear_step4_img:
//                viewImg = (ImageView) v;
//                toSelectPhoto();
//                break;
            case R.id.item_linear_step4_describe:
                viewDesc = v;
                descEdit = new EditText(aty);
                descDialog =new  AlertDialog.Builder(aty).setTitle("描述")
                        .setView(descEdit)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((TextView)viewDesc).setText(descEdit.getText());
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                descDialog.show();
                break;

        }

    }

    /**
     * 完成发布
     * 这里需要检测各个步骤是否完成
     */
    private void finishPublish() {

        /**发布需要 4步
         * 1、选择孩子/父母
         * 2、选择二级类别
         * 3、标题、描述  用量和食材
         * 4、步骤   图片和描述
         *
         * 检测4步是否完成
         *
         * 上传图片 返回图片url 上传图片是根据步骤的多少n
         * 去相应路径下 SD卡里（img/step1.jpeg） 选择 step1-n的图片上传
         *
         * 所有数据 封装成json
         * **/
    }


    /**
     * 这里去跳转到 选择图片的对话框
     * @param position 传入不同item 的img 的position
     */
    private void toSelectPhoto(int position) {
        Intent intent = new Intent(aty,SelectActivity.class);
        intent.putExtra(SelectActivity.KEY_PHOTO_PATH,"step"+position+".jpeg");
        intent.putExtra(SelectActivity.KEY_X_RATE,16);//x比例
        intent.putExtra(SelectActivity.KEY_Y_RATE,9);//y比例
        intent.putExtra(SelectActivity.KEY_WIDTH,640);//宽
        intent.putExtra(SelectActivity.KEY_HEIGHT,360);//高
        startActivityForResult(intent,TO_SELECT_PHOTO);
    }

    /**
     * 判断第三步是否完成
     * 条件包括 菜谱标题 菜谱描述 菜谱的材料用量
     */
    private boolean checkStep3Finish() {
        String title = step3title.getText().toString().trim();
        String desc = step3desc.getText().toString().trim();
        if(title.equals("") || desc.equals("")){
            ViewInject.toast("标题或者描述不能为空");
            return  false;
        }
        else {
            return true;
        }
        
    }


    /**
     *step4里面增加一栏
     */
    private void step4add() {
        View view = View.inflate(aty, R.layout.item_linear_step4, null);
        TextView step = (TextView) view.findViewById(R.id.item_linear_step4_step);
        ImageView img = (ImageView) view.findViewById(R.id.item_linear_step4_img);
        TextView desc = (TextView) view.findViewById(R.id.item_linear_step4_describe);
        step.setText(""+(step4linear.getChildCount()+1));
        desc.setOnClickListener(this);
        img.setOnClickListener(new MyOnClick(step4linear.getChildCount()+1));
        step4linear.addView(view);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO) {
            String picPath = data.getStringExtra(SelectActivity.KEY_RETURN_PHOTO_PATH);
            Bitmap bm = BitmapFactory.decodeFile(picPath);
            if(bm != null && viewImg!=null) {
                viewImg.setImageBitmap(bm);
            }
        }
    }


    /**
     * 专门针对于步骤4里面的 图片点击
     * 因为涉及到对于不同item里面图片点击的区分  所以传入position
     * 在跳转到 选择图片的时候因为要传入 图片名称，故这里把position拼接到名字里面
     * step+position.jpeg
     *
     */
    private class MyOnClick implements View.OnClickListener {
        private int position;
        public MyOnClick(int i) {
            this.position = i;
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.item_linear_step4_img) {
                viewImg = (ImageView) v;
                toSelectPhoto(position);
            }
        }
    }
}
