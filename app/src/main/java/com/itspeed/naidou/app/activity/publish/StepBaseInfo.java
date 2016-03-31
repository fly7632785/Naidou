package com.itspeed.naidou.app.activity.publish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.AppConstant;
import com.itspeed.naidou.app.helper.UIHelper;
import com.itspeed.naidou.app.view.CustomView.PickLableView;

import org.kymjs.kjframe.ui.BindView;

/**
 * Created by jafir on 16/1/18.
 * 发布菜谱的 基本属性
 * 包含 对象、工艺、时间、口味、难度等
 *
 */
public class StepBaseInfo extends BasePublishActivity implements PickLableView.onPickViewListener {


    private static final int RQ_PROCE = 1;
    private static final int RQ_DIFFICULT = 2;
    private static final int RQ_OBJECT = 3;
    private static final int RQ_TASTE = 4;
    private static final int RQ_TIME = 5;
    @BindView(id = R.id.publish_base_info_object)
    private PickLableView mObject;
    @BindView(id = R.id.publish_base_info_proce)
    private PickLableView mProce;
    @BindView(id = R.id.publish_base_info_time)
    private PickLableView mTime;
    @BindView(id = R.id.publish_base_info_taste)
    private PickLableView mTaste;
    @BindView(id = R.id.publish_base_info_difficult)
    private PickLableView mDifficult;


    /**
     * 各类属性的index指针
     * 指向 各个属性的数组下标
     * 属性数组 在AppConstant里面
     */
    private int proceIndex = 0;
    private int difficultIndex;
    private int objectIndex;
    private int timeIndex;
    private boolean isModify;
    private int tasteIndex;

    @Override
    public void setRootView() {
        setContentView(R.layout.aty_publish_base_info);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvTitle.setText("基本信息");
        isModify = getIntent().getBooleanExtra("isModify", false);
        if (isModify) {
            mTvRight.setText("完成");
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        addGuideImage(R.mipmap.hint_publish_baseinfo,R.id.publish_base_info_layout);
    }

    @Override
    public void initData() {
        super.initData();

        mObject.setContent(AppConstant.object_f);
        mProce.setContent(AppConstant.proce);
        mTime.setContent(AppConstant.time);
        mTaste.setContent(AppConstant.taste);
        mDifficult.setContent(AppConstant.difficult);

        //从本地获取 菜谱的工艺属性 然后设置对应的指针下标
        String proce = cookBook.getProce();
        if (!proce.equals("")) {
            for (int i = 0; i < AppConstant.proce.length; i++) {
                if (proce.equals(AppConstant.proce[i])) {
                    mProce.setIndex(i);
                }
            }
        }

        String taste = cookBook.getTaste();
        if (!taste.equals("")) {
            for (int i = 0; i < AppConstant.taste.length; i++) {
                if (taste.equals(AppConstant.taste[i])) {
                    mTaste.setIndex(i);
                }
            }
        }

        String difficult = cookBook.getDifficult();
        if (!difficult.equals("")) {
            for (int i = 0; i < AppConstant.difficult.length; i++) {
                if (difficult.equals(AppConstant.difficult[i])) {
                    mDifficult.setIndex(i);
                }
            }
        }
        String object = cookBook.getDifficult();
        if (!object.equals("")) {
            for (int i = 0; i < AppConstant.object.length; i++) {
                if (object.equals(AppConstant.object[i])) {
                    mObject.setIndex(i);
                }
            }
        }
        String time = cookBook.getCookTime();
        if (!time.equals("")) {
            for (int i = 0; i < AppConstant.time.length; i++) {
                if (time.equals(AppConstant.time[i])) {
                    mTime.setIndex(i);
                }
            }
        }

        mObject.setViewClickListener(this);
        mProce.setViewClickListener(this);
        mTime.setViewClickListener(this);
        mTaste.setViewClickListener(this);
        mDifficult.setViewClickListener(this);

        proceIndex = mProce.getIndex();
        difficultIndex = mDifficult.getIndex();
        timeIndex = mTime.getIndex();
        tasteIndex = mTaste.getIndex();
    }


    @Override
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.publish_base_info_object:
                UIHelper.showPublishObject(this, RQ_OBJECT, mObject.getIndex());
                break;
            case R.id.publish_base_info_proce:
                UIHelper.showPublishProce(this, RQ_PROCE, mProce.getIndex());
                break;
            case R.id.publish_base_info_time:
                UIHelper.showPublishTime(this, RQ_TIME, mTime.getIndex());
                break;
            case R.id.publish_base_info_taste:
                UIHelper.showPublishTaste(this, RQ_TASTE, mTaste.getIndex());
                break;
            case R.id.publish_base_info_difficult:
                UIHelper.showPublishDifficult(this, RQ_DIFFICULT, mDifficult.getIndex());
                break;

        }
    }

    @Override
    protected void onBackClick() {
        super.onBackClick();
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case RQ_PROCE:

                /**
                 * 从返回的data里面获取对应的数组 下标然后设置 显示
                 */
                proceIndex = data.getIntExtra("proceIndex", 0);
                mProce.setIndex(proceIndex);
                cookBook.setProce(AppConstant.proce[proceIndex]);
                break;
            case RQ_TASTE:
                tasteIndex = data.getIntExtra("proceIndex", 0);
                mTaste.setIndex(tasteIndex);
                cookBook.setTaste(AppConstant.taste[tasteIndex]);
                break;
            case RQ_DIFFICULT:
                difficultIndex = data.getIntExtra("proceIndex", 0);
                mDifficult.setIndex(difficultIndex);
                cookBook.setProce(AppConstant.difficult[difficultIndex]);
                break;
            case RQ_TIME:
                timeIndex = data.getIntExtra("proceIndex", 0);
                mTime.setIndex(timeIndex);
                cookBook.setCookTime(AppConstant.time[timeIndex]);
                break;
            case RQ_OBJECT:
                objectIndex = data.getIntExtra("proceIndex", 0);
                mObject.setIndex(objectIndex);
                cookBook.setObject(AppConstant.object[objectIndex]);
                break;
        }

    }


    @Override
    protected void onRightTextClick() {
        super.onRightTextClick();
        done();
        if (!isModify) {
            UIHelper.showPublishAddFoodMaterial(this);
        } else {
            this.finish();
        }
    }

    private void done() {

        cookBook.setObject(mObject.getText());
        cookBook.setProce(mProce.getText());
        cookBook.setDifficult(mDifficult.getText());
        cookBook.setCookTime(mTime.getText());
        cookBook.setTaste(mTaste.getText());
        setCookbook(cookBook);
    }


}
