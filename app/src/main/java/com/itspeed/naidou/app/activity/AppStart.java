package com.itspeed.naidou.app.activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;
import com.itspeed.naidou.app.AppContext;
import com.itspeed.naidou.app.util.ReleaseResource;
import com.itspeed.naidou.app.helper.UIHelper;
import com.itspeed.naidou.model.bean.User;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.PreferenceHelper;
import org.kymjs.kjframe.utils.SystemTool;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by jafir on 10/15/15.
 * 应用程序的开始和入口，有一个动画界面
 */
public class AppStart extends KJActivity {

    public static String TAG = "appstart";
    private ImageView image;
    //检测证书是否有效
    private boolean isTokenLegal = false;
    //是不是第一次进入APP
    private boolean isFirst;
    private String token;

    @Override
    public void setRootView() {
        image = new ImageView(aty);
        image.setImageResource(R.mipmap.start);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        Animation anim = AnimationUtils.loadAnimation(aty, R.anim.splash_start);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束 跳转
                jumpTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        setContentView(image);
        image.startAnimation(anim);

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        //动画开始的时候去 检测证书
        checkToken();
        //恢复本地的 用户信息
        restroreUserInfo();
    }

    /**
     * 回滚本地user信息
     * 因为 所有的用户信息都进行了本地化
     * 一开始 会去本地获取
     */
    private void restroreUserInfo() {
        User user = new User();
        user.setAvatarId(PreferenceHelper.readInt(aty,"userInfo","avatarId"));
        user.setUid(PreferenceHelper.readString(aty, "userInfo", "uid"));
        user.setAvatar(PreferenceHelper.readString(aty, "userInfo", "avatar"));
        user.setNickname(PreferenceHelper.readString(aty, "userInfo", "nickName"));
        user.setEmail(PreferenceHelper.readString(aty, "userInfo", "email"));
        user.setMotto(PreferenceHelper.readString(aty, "userInfo", "motto"));
        user.setCoins(PreferenceHelper.readInt(aty, "userInfo", "coins"));
        user.setFollowCount(PreferenceHelper.readInt(aty, "userInfo", "follow"));
        AppContext.user = user;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置成没有标题
        Window window = this.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 让屏幕高亮
        super.onCreate(savedInstanceState);
    }


    /**
     * 在有网络的情况下 回去检测证书是否有效
     * （没有网络的情况下 直接跳转切换为离线模式）
     *
     */
    private void checkToken() {
        //验证证书
        token = AppContext.TOKEN;
        NaidouApi.isTokenLegal(new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("isTokenLegal"+t);
                if (Response.getSuccess(t)) {
                    isTokenLegal = true;
                }
            }
        });
    }


    /**
     * 1、判断是不是第一次进入，如果是就到引导页
     * 2、判断是否有网，没有网就切换至离线模式，进入APP后所有的数据都是从缓存提取
     * 3、有网的时候，检测证书是否有效 无效则去登录
     * 4、有效则进入主界面
     */
    private void jumpTo() {
        isFirst = PreferenceHelper.readBoolean(aty, TAG, "first_open",
                true);
        //有token验证
        if (isFirst) {
            //引导页
            UIHelper.showGuide(aty);
            PreferenceHelper.write(aty, TAG, "first_open", false);
        } else if(!SystemTool.checkNet(aty)){//没有网的时候 不检查 直接去main
            UIHelper.showMain(aty);
        } else if (token.equals("") || token == null || !isTokenLegal) {
            //去登录
            UIHelper.showLogin(aty);
        }else {
            UIHelper.showMain(aty);
        }

        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(aty);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(aty);
    }

    @Override
    protected void onDestroy() {
        setContentView(R.layout.view_null);
        ReleaseResource.recyclerImg(image);
        image = null;
        System.gc();
        super.onDestroy();
    }
}
