package com.itspeed.naidou.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.kymjs.kjframe.utils.DensityUtils;

/**
 * Created by jafir on 15/7/12.
 * 用于状态栏的修改，透明，改色
 */
public class LayoutUtils {

    /**
     * 获取状态栏的高度
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context){
        int mStatusBarHeight = getInternalDimensionSize(context.getResources(), "status_bar_height");
        return mStatusBarHeight;
    }


    /**
     * 获取系统状态栏高度
     * @param res
     * @param key
     * @return
     */
    private static  int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 初始化 状态栏的颜色
     * @param context 上下文
     * @param rl    titleBar 或者 紧挨状态栏的布局
     * @param id 颜色id
     */
    public static void init(Context context ,RelativeLayout rl,int id){
        Window window = ((Activity)context).getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        final int bits =  WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            params.flags |= bits;
        }else {
            params.flags &= ~bits;
        }
        window.setAttributes(params);
        SystemBarTintManager manager = new SystemBarTintManager((Activity)context);
        manager.setStatusBarTintEnabled(true);
        manager.setStatusBarTintResource(id);

        SystemBarTintManager.SystemBarConfig config = manager.getConfig();

        Class clz = rl.getParent().getClass();
        String i;

        if(clz == LinearLayout.class){
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    DensityUtils.dip2px(context, 48));
            lp.setMargins(0, config.getPixelInsetTop(false), 0, 0);
            rl.setLayoutParams(lp);
        }else if(clz == RelativeLayout.class){
            RelativeLayout.LayoutParams lr = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    DensityUtils.dip2px(context, 48));
            lr.setMargins(0, config.getPixelInsetTop(false), 0, 0);
            rl.setLayoutParams(lr);
        }


    }

    /**
     * 初始化 状态栏的颜色
     * @param context 上下文
     * @param rl    titleBar 或者 紧挨状态栏的布局
     * @param id 颜色id
     */
    public static void init1(Context context ,RelativeLayout rl,int id){
        Window window = ((Activity)context).getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        final int bits =  WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            params.flags |= bits;
        }else {
            params.flags &= ~bits;
        }
        window.setAttributes(params);
        SystemBarTintManager manager = new SystemBarTintManager((Activity)context);
        manager.setStatusBarTintEnabled(true);
        manager.setStatusBarTintResource(id);

        SystemBarTintManager.SystemBarConfig config = manager.getConfig();

        Class clz = rl.getParent().getClass();

        if(clz == LinearLayout.class){
            //这里不同 这里是1  是一个间隔
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    DensityUtils.dip2px(context, 1));
            lp.setMargins(0, config.getPixelInsetTop(false), 0, 0);
            rl.setLayoutParams(lp);
        }else if(clz == RelativeLayout.class){
            RelativeLayout.LayoutParams lr = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    DensityUtils.dip2px(context, 1));
            lr.setMargins(0, config.getPixelInsetTop(false), 0, 0);
            rl.setLayoutParams(lr);
        }


    }
}
