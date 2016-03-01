package com.itspeed.naidou.app.activity.publish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.itspeed.naidou.R;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;

/**
 * Created by jafir on 16/1/22.
 * 菜谱基本属性 时间选择
 */
public class StepTime extends BasePublishActivity {



    @BindView(id =  R.id.t1,click = true)
    private TextView t1;
    @BindView(id =  R.id.t2,click = true)
    private TextView t2;
    @BindView(id =  R.id.t3,click = true)
    private TextView t3;
    @BindView(id =  R.id.t4,click = true)
    private TextView t4;
    @BindView(id =  R.id.t5,click = true)
    private TextView t5;
    @BindView(id =  R.id.t6,click = true)
    private TextView t6;
    @BindView(id =  R.id.t7,click = true)
    private TextView t7;
    @BindView(id =  R.id.t8,click = true)
    private TextView t8;
    @BindView(id =  R.id.t9,click = true)
    private TextView t9;
    private int currentIndex;


    //数组 装按钮 便于使用index
    private TextView[] times = new TextView[9];

    @Override
    public void setRootView() {

        setContentView(R.layout.aty_publish_time);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvTitle.setText("选择时间");
        mTvRight.setVisibility(View.GONE);
        mImgConfirm.setVisibility(View.VISIBLE);

    }

    @Override
    public void initData() {
        super.initData();
        //获取 现在选中的下标
        currentIndex = getIntent().getIntExtra("proceIndex", -1);
        times[0] = t1;
        times[1] = t2;
        times[2] = t3;
        times[3] = t4;
        times[4] = t5;
        times[5] = t6;
        times[6] = t7;
        times[7] = t8;
        times[8] = t9;

        //设置现在选中的状态
        if(currentIndex != -1){
            times[currentIndex].setSelected(true);
            times[currentIndex].setTextColor(getResources().getColor(R.color.white));
        }



    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()){
            case R.id.t1:
                initSelect();
                t1.setSelected(true);
                t1.setTextColor(getResources().getColor(R.color.white));
                currentIndex = 0;
                break;
            case R.id.t2:
                initSelect();
                t2.setSelected(true);
                t2.setTextColor(getResources().getColor(R.color.white));
                currentIndex = 1;
                break;
            case R.id.t3:
                initSelect();
                t3.setSelected(true);
                t3.setTextColor(getResources().getColor(R.color.white));
                currentIndex = 2;
                break;
            case R.id.t4:
                initSelect();
                t4.setSelected(true);
                t4.setTextColor(getResources().getColor(R.color.white));
                currentIndex = 3;
                break;
            case R.id.t5:
                initSelect();
                t5.setSelected(true);
                t5.setTextColor(getResources().getColor(R.color.white));
                currentIndex = 4;
                break;
            case R.id.t6:
                initSelect();
                t6.setSelected(true);
                t6.setTextColor(getResources().getColor(R.color.white));
                currentIndex = 5;
                break;
            case R.id.t7:
                initSelect();
                t7.setSelected(true);
                t7.setTextColor(getResources().getColor(R.color.white));
                currentIndex = 6;
                break;
            case R.id.t8:
                initSelect();
                t8.setSelected(true);
                t8.setTextColor(getResources().getColor(R.color.white));
                currentIndex = 7;
                break;
            case R.id.t9:
                initSelect();
                t9.setSelected(true);
                t9.setTextColor(getResources().getColor(R.color.white));
                currentIndex = 8;
                break;
        }
    }

    @Override
    protected void onBackClick() {
        super.onBackClick();
        this.finish();
    }



    @Override
    protected void onConfirmClick() {
        super.onConfirmClick();
        if (currentIndex == -1) {
            ViewInject.toast("请选择");
            return;
        }
        Intent intent = getIntent();
        intent.putExtra("proceIndex", currentIndex);
        this.setResult(RESULT_OK, intent);
        this.finish();
    }

    /**
     * 初始化 所有按钮的状态
     */
    private void initSelect() {
        t1.setSelected(false);
        t2.setSelected(false);
        t3.setSelected(false);
        t4.setSelected(false);
        t5.setSelected(false);
        t6.setSelected(false);
        t7.setSelected(false);
        t8.setSelected(false);
        t9.setSelected(false);

        t1.setTextColor(getResources().getColor(R.color.normal_dark_font));
        t2.setTextColor(getResources().getColor(R.color.normal_dark_font));
        t3.setTextColor(getResources().getColor(R.color.normal_dark_font));
        t4.setTextColor(getResources().getColor(R.color.normal_dark_font));
        t5.setTextColor(getResources().getColor(R.color.normal_dark_font));
        t6.setTextColor(getResources().getColor(R.color.normal_dark_font));
        t7.setTextColor(getResources().getColor(R.color.normal_dark_font));
        t8.setTextColor(getResources().getColor(R.color.normal_dark_font));
        t9.setTextColor(getResources().getColor(R.color.normal_dark_font));
    }



}

