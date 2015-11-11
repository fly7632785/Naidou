package com.itspeed.naidou.app;

import android.app.Application;

import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.utils.DensityUtils;

/**
 * 
 */
public class AppContext extends Application {


    public static int screenH;
    public static int screenW;

    @Override
    public void onCreate() {
        super.onCreate();
        HttpConfig.CACHEPATH = AppConfig.httpCachePath;
        screenH = DensityUtils.getScreenH(this);
        screenW = DensityUtils.getScreenW(this);
    }




}
