package com.itspeed.naidou.app.activity.publish;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.util.UIHelper;

import org.kymjs.kjframe.ui.BindView;

/**
 * Created by jafir on 16/1/18.
 */
public class StepAddFoodMaterial extends BasePublishActivity {


    @BindView(id = R.id.publish_add_foodmaterial_linear)
    private LinearLayout mLinear;
    private boolean isModify;

    @Override
    public void setRootView() {
        setContentView(R.layout.aty_publish_add_foodmaterial);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvTitle.setText("食材");
        isModify = getIntent().getBooleanExtra("isModify",false);
        if(isModify){
            mTvRight.setText("完成");
        }

    }

    @Override
    public void initData() {
        super.initData();


    }


    private void done() {
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
        if(!isModify) {
            UIHelper.showPublishAddStep(this);
            this.finish();
        }else {
            this.finish();
        }

    }
}
