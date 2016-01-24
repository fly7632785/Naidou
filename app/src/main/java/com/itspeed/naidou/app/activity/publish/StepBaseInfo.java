package com.itspeed.naidou.app.activity.publish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.AppConstant;
import com.itspeed.naidou.app.util.UIHelper;
import com.itspeed.naidou.app.view.CustomView.PickLableView;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;

/**
 * Created by jafir on 16/1/18.
 */
public class StepBaseInfo extends BasePublishActivity implements PickLableView.onPickViewListener {


    private static final int RQ_PROCE = 1;
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



    private int proceIndex = 0;

    @Override
    public void setRootView() {
        setContentView(R.layout.aty_publish_base_info);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvTitle.setText("基本信息");
    }

    @Override
    public void initData() {
        super.initData();

        mObject.setContent(new String[]{"1", "2", "3", "4", "5"});
        mProce.setContent(AppConstant.proce);
        mTime.setContent(new String[]{"1", "2", "3", "4", "5"});
        mTaste.setContent(new String[]{"1", "2", "3", "4", "5"});
        mDifficult.setContent(new String[]{"1", "2", "3", "4", "5"});

        mObject.setViewClickListener(this);
        mProce.setViewClickListener(this);
        mTime.setViewClickListener(this);
        mTaste.setViewClickListener(this);
        mDifficult.setViewClickListener(this);

        proceIndex = mProce.getIndex();
    }



    @Override
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.publish_base_info_object:
                ViewInject.toast("object");
                break;
            case R.id.publish_base_info_proce:
                UIHelper.showPublishProce(this,RQ_PROCE,mProce.getIndex());
                break;
            case R.id.publish_base_info_time:
                break;
            case R.id.publish_base_info_taste:
                break;
            case R.id.publish_base_info_difficult:
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK){
            return;
        }
        switch (requestCode){
            case RQ_PROCE:
                proceIndex = data.getIntExtra("proceIndex",0);
                mProce.setIndex(proceIndex);
                cookBook.setProce(AppConstant.proce[proceIndex]);
                break;
        }

    }


    @Override
    protected void onRightTextClick() {
        super.onRightTextClick();
        UIHelper.showPublishAddFoodMaterial(this);
    }
}
