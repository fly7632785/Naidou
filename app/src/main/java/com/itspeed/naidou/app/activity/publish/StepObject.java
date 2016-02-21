package com.itspeed.naidou.app.activity.publish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.AppConstant;
import com.itspeed.naidou.app.view.CustomView.PickLableView;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;

/**
 * Created by jafir on 16/1/22.
 */
public class StepObject extends BasePublishActivity {


    @BindView(id = R.id.publish_object_beiyun, click = true)
    private TextView beiyun;
    @BindView(id = R.id.publish_object_yunchu, click = true)
    private TextView yunchu;
    @BindView(id = R.id.publish_object_yunzhong, click = true)
    private TextView yunzhong;
    @BindView(id = R.id.publish_object_yunwan, click = true)
    private TextView yunwan;
    @BindView(id = R.id.publish_object_yuezi, click = true)
    private TextView yuezi;

    @BindView(id = R.id.publish_object_pickview)
    private PickLableView pickLableView;

    private int currentIndex;

    private TextView[] objects = new TextView[5];
    private boolean isParent;

    @Override
    public void setRootView() {

        setContentView(R.layout.aty_publish_oject);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvTitle.setText("选择对象");
        mTvRight.setVisibility(View.GONE);
        mImgConfirm.setVisibility(View.VISIBLE);

    }


    @Override
    public void initData() {
        super.initData();
        objects[0] = beiyun;
        objects[1] = yunchu;
        objects[2] = yunzhong;
        objects[3] = yunwan;
        objects[4] = yuezi;
        //0-9 对应  备孕 到  孩子的 3-6岁
        currentIndex = getIntent().getIntExtra("proceIndex", 0);
        pickLableView.setContent(new String[]{"父母", "孩子"});
        if (currentIndex >= 4) {
            pickLableView.setIndex(1);
            isParent = false;
        } else {
            pickLableView.setIndex(0);
            isParent = true;
        }
        if (isParent) {
            //父母
            beiyun.setText(AppConstant.object_f[0]);
            yunchu.setText(AppConstant.object_f[1]);
            yunzhong.setText(AppConstant.object_f[2]);
            yunwan.setText(AppConstant.object_f[3]);
            yuezi.setText(AppConstant.object_f[4]);
        } else {
            //孩子
            beiyun.setText(AppConstant.object_h[0]);
            yunchu.setText(AppConstant.object_h[1]);
            yunzhong.setText(AppConstant.object_h[2]);
            yunwan.setText(AppConstant.object_h[3]);
            yuezi.setText(AppConstant.object_h[4]);
        }


        objects[currentIndex % 5].setSelected(true);
        objects[currentIndex % 5].setTextColor(getResources().getColor(R.color.white));

        pickLableView.setViewStateChangedListener(new PickLableView.onPickViewChangedListener() {
            @Override
            public void onViewStateChanged(View view, int index) {
                if (index == 0) {
                    //父母
                    beiyun.setText(AppConstant.object_f[0]);
                    yunchu.setText(AppConstant.object_f[1]);
                    yunzhong.setText(AppConstant.object_f[2]);
                    yunwan.setText(AppConstant.object_f[3]);
                    yuezi.setText(AppConstant.object_f[4]);
                    isParent = true;

                } else {
                    //孩子
                    beiyun.setText(AppConstant.object_h[0]);
                    yunchu.setText(AppConstant.object_h[1]);
                    yunzhong.setText(AppConstant.object_h[2]);
                    yunwan.setText(AppConstant.object_h[3]);
                    yuezi.setText(AppConstant.object_h[4]);
                    isParent = false;

                }

            }
        });


    }

    @Override
    protected void onBackClick() {
        super.onBackClick();
        this.finish();
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.publish_object_beiyun:
                iniSelect();
                if (isParent) {
                    currentIndex = 0;
                } else {
                    currentIndex = 5;
                }
                objects[currentIndex % 5].setSelected(true);
                objects[currentIndex % 5].setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.publish_object_yunchu:
                iniSelect();
                if (isParent) {
                    currentIndex = 1;
                } else {
                    currentIndex = 6;
                }
                objects[currentIndex % 5].setSelected(true);
                objects[currentIndex % 5].setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.publish_object_yunzhong:
                iniSelect();
                if (isParent) {
                    currentIndex = 2;
                } else {
                    currentIndex = 7;
                }
                objects[currentIndex % 5].setSelected(true);
                objects[currentIndex % 5].setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.publish_object_yunwan:
                iniSelect();
                if (isParent) {
                    currentIndex = 3;
                } else {
                    currentIndex = 8;
                }
                objects[currentIndex % 5].setSelected(true);
                objects[currentIndex % 5].setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.publish_object_yuezi:
                iniSelect();
                if (isParent) {
                    currentIndex = 4;
                } else {
                    currentIndex = 9;
                }
                objects[currentIndex % 5].setSelected(true);
                objects[currentIndex % 5].setTextColor(getResources().getColor(R.color.white));
                break;

        }
    }


    private void iniSelect() {
        beiyun.setSelected(false);
        yunchu.setSelected(false);
        yunzhong.setSelected(false);
        yunwan.setSelected(false);
        yuezi.setSelected(false);

        beiyun.setTextColor(getResources().getColor(R.color.normal_dark_font));
        yunchu.setTextColor(getResources().getColor(R.color.normal_dark_font));
        yunzhong.setTextColor(getResources().getColor(R.color.normal_dark_font));
        yunwan.setTextColor(getResources().getColor(R.color.normal_dark_font));
        yuezi.setTextColor(getResources().getColor(R.color.normal_dark_font));
    }

    @Override
    protected void onConfirmClick() {
        super.onConfirmClick();
        if (currentIndex == -1) {
            ViewInject.toast("请选择");
            return;
        }
        KJLoger.debug("objectinde:" + currentIndex);
        Intent intent = getIntent();
        intent.putExtra("proceIndex", currentIndex);
        this.setResult(RESULT_OK, intent);
        this.finish();
    }


}

