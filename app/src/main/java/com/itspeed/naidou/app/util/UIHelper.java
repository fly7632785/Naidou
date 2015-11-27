package com.itspeed.naidou.app.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.itspeed.naidou.app.activity.LoginActivity;
import com.itspeed.naidou.app.activity.MainActivity;
import com.itspeed.naidou.app.activity.PublishActivity;
import com.itspeed.naidou.app.activity.RegisterActivity;
import com.itspeed.naidou.app.activity.SettingActivity;
import com.itspeed.naidou.app.activity.SimpleBackActivity;
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
    public static void showChideDetail(Context context) {
        SimpleBackActivity.postShowWith(context, SimpleBackPage.CHIDE_DETAIL);

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
        Intent intent = new Intent(context,PublishActivity.class);
        context.startActivity(intent);
    }




}
