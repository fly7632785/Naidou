package com.itspeed.naidou.app.activity.publish;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itspeed.naidou.R;
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;
import com.itspeed.naidou.app.AppConstant;
import com.itspeed.naidou.app.fragment.Level2Fragment;
import com.itspeed.naidou.app.helper.UIHelper;
import com.itspeed.naidou.model.bean.CookBook;
import com.itspeed.naidou.model.bean.FoodMaterial;
import com.itspeed.naidou.model.bean.Step;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.StringUtils;

/**
 * Created by jafir on 16/1/22.
 *
 * 发布 总览
 *
 * 从纵览 可以跳转到相应的 属性和详情界面去修改内容
 *
 *
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
    private ProgressDialog dialog;


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


    /**
     * 预览
     * chideDetail 界面也有俩个入口模式 一个是从网络获取数据
     * 一个是 本地预览而用
     */
    private void preview() {
        if(!step1 ||!step3||!step2||!step4){
            //如果咩有完成提醒
            ViewInject.toast("菜谱不完整");
            return;
        }


        UIHelper.showLocalCbDetail(this, "-1", cookBook);
    }


    @Override
    public void initData() {
        super.initData();
//        setData();

    }

    /**
     * 设置数据   从本地获取数据 并填充
     * 如果 4个步骤有一个不全则无法发布菜谱
     *
     *
     */
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


        if (cookBook.getSteps() != null && cookBook.getSteps().size()!=0) {
            stepDetail.setText("共" + cookBook.getSteps().size() + "步");
            stepPoint.setImageResource(R.mipmap.indicator_red_point);
            step4 = true;
        } else {
            stepPoint.setImageResource(R.mipmap.indicator_white_point);
        }

        if (step1 && step2&&step3&&step4){
            stepPerfect.setVisibility(View.VISIBLE);
        }

//        KJLoger.debug("cb:" + cookBook.toString());
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

            case R.id.publish_all_publish:
                if(step1 && step2 && step3 &&step4) {
                    uploadData();
                }else {
                    ViewInject.toast("请完成菜谱内容");
                }
                break;
        }
    }


    /**
     * 上传json数据
     */
    private void uploadData() {

        dialog = new ProgressDialog(aty);
        dialog.setMessage("正在上传图片...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();



        //获取cate种类
        int category =  getCategory();
        //获取 食材json
        String materialJson = JSON.toJSONString(cookBook.getFoods());

        //获取 步骤json
        String stepsJson = getStepJson();

        KJLoger.debug("data://"  + "coverid:"+cookBook.getCoverPic().getId()+"category:"+ Level2Fragment.category[category]
                        + "materialjson:" + materialJson + "stepsjson:" + stepsJson
        );

        NaidouApi.publishCookBook(cookBook.getTitle(), cookBook.getDescription(), cookBook.getCoverPic().getId(), Level2Fragment.category[category], materialJson, stepsJson, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("publishCookBook:" + t);
                if (Response.getSuccess(t)) {
                    dialog.dismiss();
                    ViewInject.toast("发布菜谱成功");
                    clearLocal();
                    aty.finish();
                }
            }
        });

    }

    /**
     * 清理草稿箱 相当于重建一个新的菜谱
     */
    private void clearLocal() {
        cookBook = new CookBook();
        setCookbook(cookBook);
    }

    /**
     * 获取步骤的json数据
     *
     * @return
     */
    private String getStepJson() {


        com.alibaba.fastjson.JSONArray array = new com.alibaba.fastjson.JSONArray();
        for (int i = 0; i < cookBook.getSteps().size(); i++) {
            Step  step = cookBook.getSteps().get(i);


            JSONObject o = new JSONObject();
            JSONObject pic = new JSONObject();
            //如果是图片是默认的没有上传的也是Null
            if(step.getPic().getPath().equals("0") || step.getPic().getLocalPath().equals("0")
                    ||step.getPic().getId()==-1
                    ){
                pic.put("path","");
                pic.put("id","");
            }else {
                pic.put("path", step.getPic().getPath());
                pic.put("id", step.getPic().getId());
            }
            o.put("pic", pic);
            //如果 是默认的菜谱描述 就 设为空
            if(step.getDescription().equals("菜谱描述...")){
                o.put("description","");
            }else {
                o.put("description", step.getDescription());
            }
            array.add(o);
        }

        return array.toJSONString();
    }


    /**
     * 获取 对应object 的category 种类
     * @return
     */
    private int getCategory() {


        for (int i = 0; i < AppConstant.object.length; i++) {
            if(cookBook.getObject().equals(AppConstant.object[i])){
                return i;
            }
        }
        return 0;
    }
}

