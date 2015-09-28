package com.itspeed.naidou.app.util;

import android.content.Context;
import android.content.Intent;

import com.itspeed.naidou.app.activity.SettingActivity;

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

}
