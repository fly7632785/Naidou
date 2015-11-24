package com.itspeed.naidou.app;

import android.app.Application;

import com.itspeed.naidou.model.bean.User;

import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.utils.DensityUtils;

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

    @Override
    public void onCreate() {
        super.onCreate();
        user = new User();
        //设置默认 user信息
        user.setNickname("请登录");
        user.setMotto("登录之后可以修改个性签名哟");
        user.setCoins(0);
        user.setFollows(0);

        HttpConfig.CACHEPATH = AppConfig.httpCachePath;
        screenH = DensityUtils.getScreenH(this);
        screenW = DensityUtils.getScreenW(this);
    }




}
