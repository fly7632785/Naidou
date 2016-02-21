package com.itspeed.naidou.app.activity.publish;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.util.UIHelper;
import com.itspeed.naidou.model.bean.FoodMaterial;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.StringUtils;

/**
 * Created by jafir on 16/1/22.
 */
public class StepAll extends BasePublishActivity {


    @BindView(id = R.id.publish_all_cb)
    private TextView cb;
    @BindView(id = R.id.publish_all_baseinfo)
    private TextView baseinfo;
    @BindView(id = R.id.publish_all_food)
    private TextView food;
    @BindView(id = R.id.publish_all_step)
    private TextView step;

    @BindView(id = R.id.publish_all_cb_detail)
    private TextView cbDetail;
    @BindView(id = R.id.publish_all_baseinfo_detail)
    private TextView baseinfoDetail;
    @BindView(id = R.id.publish_all_food_detail)
    private TextView foodDetail;
    @BindView(id = R.id.publish_all_step_detail)
    private TextView stepDetail;

    @BindView(id = R.id.publish_all_publish, click = true)
    private TextView publish;

    @BindView(id = R.id.publish_all_cb_ll, click = true)
    private LinearLayout cbLayout;
    @BindView(id = R.id.publish_all_food_ll, click = true)
    private LinearLayout foodLayout;
    @BindView(id = R.id.publish_all_baseinfo_ll, click = true)
    private LinearLayout baseinfoLayout;
    @BindView(id = R.id.publish_all_step_ll, click = true)
    private LinearLayout stepLayout;


    @BindView(id = R.id.publish_all_cb_point)
    private ImageView cbPoint;
    @BindView(id = R.id.publish_all_baseinfo_point)
    private ImageView baseinfoPoint;
    @BindView(id = R.id.publish_all_food_point)
    private ImageView foodPoint;
    @BindView(id = R.id.publish_all_step_point)
    private ImageView stepPoint;

    @BindView(id = R.id.publish_all_perfect)
    private ImageView stepPerfect;


    private boolean step1;
    private boolean step2;
    private boolean step3;
    private boolean step4;


    @Override
    public void setRootView() {

        setContentView(R.layout.aty_publish_all);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvTitle.setText("总览");
        mImgBack.setVisibility(View.GONE);
        mTvRight.setText("预览");
        mImgConfirm.setVisibility(View.GONE);

    }


    @Override
    protected void onBackClick() {
        super.onBackClick();
        this.finish();
    }

    @Override
    protected void onRightTextClick() {
        super.onRightTextClick();
        preview();
//        this.finish();
    }


    private void preview() {
        UIHelper.showLocalCbDetail(this, "-1", cookBook);
    }


    @Override
    public void initData() {
        super.initData();
//        setData();

    }

    private void setData() {
        //重新获取
        cookBook = getCookbook();


        if (!StringUtils.isEmpty(cookBook.getTitle()) && !StringUtils.isEmpty(cookBook.getDescription())) {
            cb.setText("菜谱 " + cookBook.getTitle());
            cbDetail.setText(cookBook.getDescription());
            cbPoint.setImageResource(R.mipmap.indicator_red_point);
            step1 = true;
        } else {
            cb.setText("菜谱");
            cbDetail.setText("未添加");
            cbPoint.setImageResource(R.mipmap.indicator_white_point);
        }

        if (!StringUtils.isEmpty(cookBook.getObject())
                || !StringUtils.isEmpty(cookBook.getCookTime())
                || !StringUtils.isEmpty(cookBook.getDifficult())
                || !StringUtils.isEmpty(cookBook.getProce())
                || !StringUtils.isEmpty(cookBook.getTaste())
                ) {
            baseinfoDetail.setText(cookBook.getObject()+" "+ cookBook.getProce()+" " + cookBook.getCookTime()+" " +cookBook.getTaste()+" "+ cookBook.getDifficult());
            baseinfoPoint.setImageResource(R.mipmap.indicator_red_point);
            step2 = true;
        } else {
            baseinfoDetail.setText("未添加完整");
            baseinfoPoint.setImageResource(R.mipmap.indicator_white_point);
        }


        if (cookBook.getFoods() != null && cookBook.getFoods().size() != 0) {

            StringBuilder builder = new StringBuilder();
            for (FoodMaterial food : cookBook.getFoods()) {
                builder.append(food.getWeight() + food.getFood() + " ");
            }
            foodDetail.setText(builder);
            foodPoint.setImageResource(R.mipmap.indicator_red_point);

            step3 = true;
        } else {
            foodDetail.setText("未添加完整");
            foodPoint.setImageResource(R.mipmap.indicator_white_point);
        }


        if (cookBook.getSteps() != null) {
            stepDetail.setText("共" + cookBook.getSteps().size() + "步");
            stepPoint.setImageResource(R.mipmap.indicator_red_point);
            step4 = true;
        } else {
            stepPoint.setImageResource(R.mipmap.indicator_white_point);
        }

        if (step1 && step2&&step3&&step4){
            stepPerfect.setVisibility(View.VISIBLE);
        }

        KJLoger.debug("cb:" + cookBook.toString());
    }


    @Override
    protected void onStart() {
        super.onStart();
        setData();
        if (step1 && step2&&step3&&step4){
            stepPerfect.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);

        switch (v.getId()) {
            case R.id.publish_all_cb_ll:
                UIHelper.showModifyPublish(this);
                break;
            case R.id.publish_all_baseinfo_ll:
                UIHelper.showModifyPublishBaseInfo(this);
                break;
            case R.id.publish_all_food_ll:
                UIHelper.showModifyPublishAddFoodMaterial(this);
                break;
            case R.id.publish_all_step_ll:
                UIHelper.showModifyPublishAddStep(this);
                break;
        }
    }
}

