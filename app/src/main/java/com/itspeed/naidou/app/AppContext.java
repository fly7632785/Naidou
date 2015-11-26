package com.itspeed.naidou.app;

import android.app.Application;

import com.itspeed.naidou.model.bean.User;

import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.utils.DensityUtils;

import cn.smssdk.SMSSDK;

/**
 * 
 */
public class AppContext extends Application {


    public static int screenH;
    public static int screenW;
    public static int UID;
    public static User user;
    public static String CryptoKey="jafir7632785";
    public static String TOKEN = "";
    public static String HOST = "http://139.129.29.84/";
    public static String ShareSDKAppKey = "cb608ba9eb78";
    public static String ShareSDKAppSecret = "2ee504a3e7bea6c5624c7e1254461205";

    @Override
    public void onCreate() {
        super.onCreate();
        user = new User();
        //设置默认 user信息
        user.setNickname("请登录");
        user.setMotto("登录之后可以修改个性签名哟");
        user.setCoins(0);
        user.setFollowCount(0);

        HttpConfig.CACHEPATH = AppConfig.httpCachePath;
        screenH = DensityUtils.getScreenH(this);
        screenW = DensityUtils.getScreenW(this);

        //init smsSDK
        SMSSDK.initSDK(this, ShareSDKAppKey, ShareSDKAppSecret);



    }




}
