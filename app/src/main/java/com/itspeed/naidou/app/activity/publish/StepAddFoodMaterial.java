package com.itspeed.naidou.app.activity.publish;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.helper.UIHelper;
import com.itspeed.naidou.app.view.CustomView.WheelView;
import com.itspeed.naidou.model.bean.FoodMaterial;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jafir on 16/1/18.
 *
 * 添加食材
 *
 */
public class StepAddFoodMaterial extends BasePublishActivity {


    @BindView(id = R.id.publish_add_foodmaterial_linear)
    private LinearLayout mLinear;

    @BindView(id = R.id.publish_add_foodmaterial_more_l, click = true)
    private RelativeLayout mMore;

    private boolean isModify;
    private ArrayList<FoodMaterial> data = new ArrayList<>();
    private List<String> number = new ArrayList<>();
    //食材的单位
    private String[] units = new String[]{"-","克","千克","毫升","勺","滴","块","片","两","斤","个","只","颗","条"};
    //食材的 分量分数单位
    private String[] fraction = new String[]{"-","½","⅓","⅔","¼","¾"};

    //分量多少
    private String weight1;
    private String weight2;
    private String weight3;

    //选择滚轮的 100 400 700 的数字按钮
    private TextView weight100;
    private TextView weight400;
    private TextView weight700;
    private WheelView wv;

    @Override
    public void setRootView() {
        setContentView(R.layout.aty_publish_add_foodmaterial);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvTitle.setText("食材");
        isModify = getIntent().getBooleanExtra("isModify", false);
        if (isModify) {
            mTvRight.setText("完成");
        }

    }

    @Override
    public void initData() {
        super.initData();
        //初始化 数量 1000 共有 100 400 700的数字选择 直接跳转
        for (int i = 0; i < 1000; i++) {
            number.add(""+i+"");
        }

        /**
         * 从本地获取（或者说草稿箱）
         */
        data = cookBook.getFoods();
        /**
         * 如果data为0 说明里面没有装入数据
         * 所以 要初始化 一栏
         * 如果不为0
         */
        if (data.size() == 0 || data == null) {
            add();
        } else {
            init();
        }
    }

    /**
     * 这个初始化方法是为了 把本地数据直接拿来显示到界面上
     * 所以 每添加的一栏都是含有每栏的数据的
     */
    private void init() {

        for (int i = 0; i < data.size(); i++) {
            add(data.get(i));
        }

    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.publish_add_foodmaterial_more_l:
                add();
                break;

            case R.id.weight100:
                wv.setToSeletion(100);
                break;
            case R.id.weight400:
                wv.setToSeletion(400);
                break;
            case R.id.weight700:
                wv.setToSeletion(700);
                break;
        }
    }


    /**
     * 添加一栏
     */
    private void add() {
        View view = View.inflate(aty, R.layout.item_list_add_foodmaterial, null);
        TextView weight = (TextView) view.findViewById(R.id.item_list_add_foodmaterial_weight);
        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView textView = (TextView) v;
                initWheel(textView);
            }
        });
        mLinear.addView(view);
    }

    /**
     * 初始化 滚轮选择器
     * @param textView
     */
    private void initWheel(final TextView textView) {
        View outerView = LayoutInflater.from(StepAddFoodMaterial.this).inflate(R.layout.dialog_wheel_weight, null);

        weight100 = (TextView) outerView.findViewById(R.id.weight100);
        weight400 = (TextView) outerView.findViewById(R.id.weight400);
        weight700 = (TextView) outerView.findViewById(R.id.weight700);

        weight100.setOnClickListener(this);
        weight400.setOnClickListener(this);
        weight700.setOnClickListener(this);



        //初始化滚轮数据
        wv = (WheelView) outerView.findViewById(R.id.weight1);
        //除了中间选中之外有2个   如果为1 一栏有3个选项 如果为2 一栏有5个选项
        wv.setOffset(2);
        wv.setItems(number);
        //设置现在选择第几个
        wv.setSeletion(3);
        weight1 = "3";
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                weight1 = item;
            }
        });


        WheelView wv1 = (WheelView) outerView.findViewById(R.id.weight2);
        wv1.setOffset(2);
        wv1.setItems(Arrays.asList(fraction));
        wv1.setSeletion(0);
        weight2 = "";
        wv1.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                weight2 = item;
                if (weight2.equals("-")) {
                    weight2 = "";
                }
            }
        });


        WheelView wv2 = (WheelView) outerView.findViewById(R.id.weight3);
        wv2.setOffset(2);
        wv2.setItems(Arrays.asList(units));
        wv2.setSeletion(0);
        weight3 = "";
        wv2.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                weight3 = item;
                if(weight3.equals("-")){
                    weight3 = "";
                }
            }
        });

        new AlertDialog.Builder(StepAddFoodMaterial.this)
                .setTitle("选择分量")
                .setView(outerView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView.setText(weight1+" "+weight2+weight3);
                    }
                })
                .show();
    }

    /**
     * 初始化一栏 并添加这栏数据
     * @param foodMaterial
     */
    private void add(FoodMaterial foodMaterial) {
        View view = View.inflate(aty, R.layout.item_list_add_foodmaterial, null);
        EditText food = (EditText) view.findViewById(R.id.item_list_add_foodmaterial_food);
        TextView weight = (TextView) view.findViewById(R.id.item_list_add_foodmaterial_weight);
        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView textView = (TextView) v;
                initWheel(textView);
            }
        });
        food.setText(foodMaterial.getFood());
        weight.setText(foodMaterial.getWeight());
        mLinear.addView(view);
    }

    private void done() {

        //初始化原来的数据 重新整合
        data = new ArrayList<>();
        //打印   整理出来的数据
        for (int i = 0; i < mLinear.getChildCount(); i++) {
            EditText food = (EditText) mLinear.getChildAt(i).findViewById(R.id.item_list_add_foodmaterial_food);
            TextView weight = (TextView) mLinear.getChildAt(i).findViewById(R.id.item_list_add_foodmaterial_weight);
            if (!StringUtils.isEmpty(food.getText().toString())) {
                FoodMaterial foodMaterial = new FoodMaterial();
                foodMaterial.setFood(food.getText().toString());
                foodMaterial.setWeight(weight.getText().toString());
                data.add(foodMaterial);
                KJLoger.debug("材料" + food.getText() + ":" + weight.getText() );
            }
        }

        cookBook.setFoods(data);
        setCookbook(cookBook);

    }

    @Override
    protected void onBackClick() {
        super.onBackClick();
        //退出
        this.finish();
    }

    @Override
    protected void onRightTextClick() {
        super.onRightTextClick();

        done();

        if (!isModify) {

            UIHelper.showPublishAddStep(this);
        } else {
            this.finish();
        }

    }


}
