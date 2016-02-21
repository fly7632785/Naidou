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
 */
public class StepDifficult extends BasePublishActivity {


    private int currentIndex;

    @BindView(id = R.id.publish_difficult_1,click = true)
    private TextView level1;
    @BindView(id = R.id.publish_difficult_2,click = true)
    private TextView level2;
    @BindView(id = R.id.publish_difficult_3,click = true)
    private TextView level3;
    @BindView(id = R.id.publish_difficult_4,click = true)
    private TextView level4;
    @BindView(id = R.id.publish_difficult_5,click = true)
    private TextView level5;
    @BindView(id = R.id.publish_difficult_6,click = true)
    private TextView level6;

    private TextView[] levels = new TextView[6];



    @Override
    public void setRootView() {

        setContentView(R.layout.aty_publish_difficult);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvTitle.setText("选择难度");
        mTvRight.setVisibility(View.GONE);
        mImgConfirm.setVisibility(View.VISIBLE);

    }


    @Override
    protected void onBackClick() {
        super.onBackClick();
        this.finish();
    }

    @Override
    public void initData() {
        super.initData();
        currentIndex = getIntent().getIntExtra("proceIndex", -1);

        levels[0] = level1;
        levels[1] = level2;
        levels[2] = level3;
        levels[3] = level4;
        levels[4] = level5;
        levels[5] = level6;

        if(currentIndex != -1){
            levels[currentIndex].setSelected(true);
            levels[currentIndex].setTextColor(getResources().getColor(R.color.white));
        }

    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()){
            case R.id.publish_difficult_1:
                currentIndex = 0;
                initSelect();
                level1.setSelected(true);
                level1.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.publish_difficult_2:
                currentIndex = 1;
                initSelect();
                level2.setSelected(true);
                level2.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.publish_difficult_3:
                currentIndex = 2;
                initSelect();
                level3.setSelected(true);
                level3.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.publish_difficult_4:
                currentIndex = 3;
                initSelect();
                level4.setSelected(true);
                level4.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.publish_difficult_5:
                currentIndex = 4;
                initSelect();
                level5.setSelected(true);
                level5.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.publish_difficult_6:
                currentIndex = 5;
                initSelect();
                level6.setSelected(true);
                level6.setTextColor(getResources().getColor(R.color.white));
                break;
        }


    }

    private void initSelect() {
        level1.setSelected(false);
        level2.setSelected(false);
        level3.setSelected(false);
        level4.setSelected(false);
        level5.setSelected(false);
        level6.setSelected(false);

        level1.setTextColor(getResources().getColor(R.color.normal_dark_font));
        level2.setTextColor(getResources().getColor(R.color.normal_dark_font));
        level3.setTextColor(getResources().getColor(R.color.normal_dark_font));
        level4.setTextColor(getResources().getColor(R.color.normal_dark_font));
        level5.setTextColor(getResources().getColor(R.color.normal_dark_font));
        level6.setTextColor(getResources().getColor(R.color.normal_dark_font));
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






}

