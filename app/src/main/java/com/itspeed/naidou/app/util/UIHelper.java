package com.itspeed.naidou.app.util;

import android.content.Context;
import android.content.Intent;

import com.itspeed.naidou.app.activity.SettingActivity;
import com.itspeed.naidou.app.activity.SimpleBackActivity;
import com.itspeed.naidou.app.domain.SimpleBackPage;

/**
 * Created by jafir on 15/9/27.
 */
public class UIHelper {


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

}
