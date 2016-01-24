package com.itspeed.naidou.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.itspeed.naidou.app.activity.BigPictureActivity;
import com.itspeed.naidou.app.activity.GuideActivity;
import com.itspeed.naidou.app.activity.LoginActivity;
import com.itspeed.naidou.app.activity.MainActivity;
import com.itspeed.naidou.app.activity.RegisterActivity;
import com.itspeed.naidou.app.activity.SettingActivity;
import com.itspeed.naidou.app.activity.SimpleBackActivity;
import com.itspeed.naidou.app.activity.StepScanActivity;
import com.itspeed.naidou.app.activity.ZoneActivity;
import com.itspeed.naidou.app.activity.publish.StepAddFoodMaterial;
import com.itspeed.naidou.app.activity.publish.StepAddStep;
import com.itspeed.naidou.app.activity.publish.StepAddStepDetail;
import com.itspeed.naidou.app.activity.publish.StepBase;
import com.itspeed.naidou.app.activity.publish.StepBaseInfo;
import com.itspeed.naidou.app.activity.publish.StepProce;
import com.itspeed.naidou.app.domain.SimpleBackPage;

/**
 * Created by jafir on 15/9/27.
 * 跳转管理类
 */
public class UIHelper {
    /**
     * 显示主界面
     * @param context
     */
    public static void showMain(Context context) {
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    /**
     * 显示登录
     * @param context
     */
    public static void showLogin(Context context) {
        Intent intent = new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * 显示注册
     * @param context
     */
    public static void showRegister(Context context) {
        Intent intent = new Intent(context,RegisterActivity.class);
        context.startActivity(intent);
    }


    /**
     * 显示设置
     * @param context
     */
    public static void  showSetting(Context context){
        Intent intent = new Intent(context,SettingActivity.class);
        context.startActivity(intent);
    }
    /**
     * 显示我的菜谱
     * @param context
     */
    public static void  showMyCookbook(Context context){
        SimpleBackActivity.postShowWith(context, SimpleBackPage.MY_COOKBOOK);
    }
    /**
     * 显示我的收藏
     * @param context
     */
    public static void showMyCollect(Context context) {
        SimpleBackActivity.postShowWith(context, SimpleBackPage.MY_COLLECT);
    }
    /**
     * 显示我的关注
     * @param context
     */
    public static void showFollow(Context context) {
        SimpleBackActivity.postShowWith(context, SimpleBackPage.FOLLOW);
    }

    /**
     * 从wode显示编辑信息
     * @param context
     */
    public static void showEditInfo(Fragment context,int requstCode) {
        SimpleBackActivity.postShowForResult(context, requstCode, SimpleBackPage.EDITINFO);

    }

    /**
     * 从setting显示编辑信息
     * @param context
     */
    public static void showEditInfo(Context context) {
        SimpleBackActivity.postShowWith(context, SimpleBackPage.EDITINFO);

    }

    /**
     * 显示意见反馈
     * @param context
     */
    public static void showFeedBack(Context context) {
        SimpleBackActivity.postShowWith(context, SimpleBackPage.FEED_BACK);

    }
    /**
     * 显示修改密码
     * @param context
     */
    public static void showModifyPwd(Context context) {
        SimpleBackActivity.postShowWith(context, SimpleBackPage.MODIFY_PWD);

    }
    /**
     * 显示关于我们
     * @param context
     */
    public static void showAbout(Context context) {
        SimpleBackActivity.postShowWith(context, SimpleBackPage.ABOUT);

    }
    /**
     * 显示关于聊的详情
     * @param context
     */
    public static void showLiaodeDetail(Context context) {
        SimpleBackActivity.postShowWith(context, SimpleBackPage.LIAODE_DETAIL);

    }

    /**
     * 显示关于吃的详情
     * @param context
     */
    public static void showChideDetail(Context context,String cid) {
        Bundle bundle  = new Bundle();
        bundle.putString("cid",cid);
        SimpleBackActivity.postShowWith(context, SimpleBackPage.CHIDE_DETAIL,bundle);

    }
    /**
     * 显示关于逛的详情
     * @param context
     */
    public static void showGuangdeDetail(Context context) {
        SimpleBackActivity.postShowWith(context, SimpleBackPage.GUANGDE_DETAIL);

    }

    /**
     * 显示添加菜谱
     * @param context
     */
    public static void showPublish(Context context) {
        Intent intent = new Intent(context,StepBase.class);
        context.startActivity(intent);
    }


    /**
     * 显示添加菜谱
     * @param context
     */
    public static void showPublishBaseInfo(Context context) {
        Intent intent = new Intent(context,StepBaseInfo.class);
        context.startActivity(intent);
    }


    public static void showPublishAddFoodMaterial(Context context) {
        Intent intent = new Intent(context,StepAddFoodMaterial.class);
        context.startActivity(intent);
    }

    public static void showPublishAddStep(Context context) {
        Intent intent = new Intent(context,StepAddStep.class);
        context.startActivity(intent);
    }
    public static void showPublishAddStepDetail(Activity context,int position,int code,String desc,String path) {
        Intent intent = new Intent(context,StepAddStepDetail.class);
        intent.putExtra("position",position);
        intent.putExtra("path",path);
        intent.putExtra("desc",desc);
        context.startActivityForResult(intent,code);
    }
    public static void showPublishProce(Activity context,int proceCode,int proceIndex) {
        Intent intent = new Intent(context,StepProce.class);
        intent.putExtra("proceIndex",proceIndex);
        context.startActivityForResult(intent,proceCode);
    }

//    /**
//     * 显示添加菜谱
//     * @param context
//     */
//    public static void showPublish(Context context) {
//        Intent intent = new Intent(context,PublishActivity.class);
//        context.startActivity(intent);
//    }

    /**
     * 显示个人空间
     * @param context
     */
    public static void showZone(Context context,String  uid) {
        Intent intent = new Intent(context,ZoneActivity.class);
        intent.putExtra("uid",uid);
        context.startActivity(intent);
    }

    /**
     * 显示 引导页
     * @param context
     */
    public static void showGuide(Context context) {
        Intent intent = new Intent(context,GuideActivity.class);
        context.startActivity(intent);
    }

    /**
     * 显示大图
     * @param context
     */
    public static void showBigPicture(Context context,String url) {
        Intent intent = new Intent(context,BigPictureActivity.class);
        intent.putExtra("bigImgUrl",url);
        context.startActivity(intent);
    }

    /**
     * 步骤查看
     * @param context
     * @param position
     * @param urls
     * @param descs
     */
    public static void showPictrueScan(Context context, int position, String[] urls, String[] descs) {
        Intent intent = new Intent(context,StepScanActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("urls",urls);
        intent.putExtra("descs",descs);
        context.startActivity(intent);
    }


}
