package com.itspeed.naidou.app;

import android.app.Application;

import com.itspeed.naidou.app.activity.LoginActivity;
import com.itspeed.naidou.app.activity.SelectActivity;
import com.itspeed.naidou.app.util.CryptoUtil;
import com.itspeed.naidou.model.bean.User;

import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.utils.DensityUtils;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.PreferenceHelper;

/**
 * 
 */
public class AppContext extends Application {


    public static int screenH;
    public static int screenW;
    public static User user;
    public static String CryptoKey="jafir7632785";
    public static String TOKEN = "";
    public static String HOST = "http://139.129.29.84/";
    public static String HOST1 = "http://139.129.29.84";
    public static String ShareSDKAppKey = "cb608ba9eb78";
    public static String ShareSDKAppSecret = "2ee504a3e7bea6c5624c7e1254461205";

    public static String JpushAppKey = "494431c4ab15e0103267f15c";
    public static String JpushSecret = "63f8befa3f4f4ac2d87c5e2b";
    public static boolean  isVisitor = false;
    public static String userAvatarPath;


    @Override
    public void onCreate() {
        super.onCreate();
        user = new User();
        //设置默认 user信息
        user.setAvatar("");
        user.setNickname("请登录");
        user.setMotto("登录之后可以修改个性签名哟");
        user.setCoins(0);
        user.setFollowCount(0);

        HttpConfig.CACHEPATH = AppConfig.httpCachePath;
        screenH = DensityUtils.getScreenH(this);
        screenW = DensityUtils.getScreenW(this);

        userAvatarPath = SelectActivity.IMG_AVATAR_PATH + "avatar.jpeg";
        /**
         * 这里 token做了本地化
         * 防止 应用程序 因内存不足而前面的activity被回收，
         * 然后重启之后，丢失token  那时候会重新获取token
         */

        String token = PreferenceHelper.readString(this, LoginActivity.TAG, "apiKey");
        if(token != null) {
            TOKEN = CryptoUtil.decrypto(token);
            KJLoger.debug("application.token"+TOKEN);
        }





    }




}
