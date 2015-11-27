package com.itspeed.naidou.api;

import com.itspeed.naidou.app.AppContext;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.utils.KJLoger;

import java.io.File;

/**
 * Created by jafir on 10/26/15.
 * 所有API接口的结合
 */
public class NaidouApi {

    private static String baseHost = "http://139.129.29.84/api/";

    /**
     * 登录接口
     * @param name
     * @param password
     * @param callBack
     */
    public static void login(String name,String password,HttpCallBack callBack){
        String url = "loginCheck";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.put("_username",name);
        params.put("_password",password);
        kjh.post(baseHost+url,params,callBack);
    }

    /**
     * 注销接口
     * @param callBack
     */
    public static void logout(HttpCallBack callBack){
        String url = "";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        kjh.post(baseHost+url,params,callBack);
    }



    /**
     * 注册接口
     * @param phone
     * @param password
     * @param callBack
     */
    public static void register(String phone,String password,HttpCallBack callBack){
        String url = "doRegister";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.put("_username",phone);
        params.put("_password", password);
        kjh.post(baseHost+url,params,callBack);
    }

    /**
     * 获取用户信息
     * @param uid
     * @param callBack
     */
    public static void getUserInfo(String uid,HttpCallBack callBack){
        String url = "getUserInfo";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        params.put("_uid", uid);
        kjh.get(baseHost + url, params, callBack);
    }

    /**
     * 获取我的信息
     * @param callBack
     */
    public static void getMyInfo(HttpCallBack callBack){
        String url = "getMyInfo";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        kjh.get(baseHost + url, params, callBack);
    }


    /**
     * 获取吃的推荐列表
     * @param callBack
     */
    public  static  void getRecommendChideList(HttpCallBack callBack){
        String url = "getRecommendRecipe";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;//缓存1小时
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        kjh.get(baseHost + url,params, callBack);
    }

    /**
     * 获取聊的推荐列表
     * @param callBack
     */
    public  static  void getRecommendLiaodeList(HttpCallBack callBack){
        String url = "";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;//缓存1小时
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        kjh.get(baseHost + url,params, callBack);
    }

    /**
     * 获取吃的列表
     * @param callBack
     * @param cate 1:备孕 孕初 孕中 孕晚 月子 5:4-6月 7-8 9-12 2-3 4-6
     */
    public  static  void getChideList(String cate,int page,HttpCallBack callBack){
        String url = "listCookbook";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;//缓存1小时
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        KJLoger.debug("ApiKey:" + AppContext.TOKEN);
        params.put("_cate", cate);
        params.put("_page", page);
        kjh.get(baseHost + url, params, callBack);
    }

    /**
     * 获取吃的列表缓存
     */
    public  static  String  getChideListCache(){
        String url = "listCookbook";
        KJHttp kjh = new KJHttp();
        return kjh.getStringCache(url);
    }


    /**
     * 获取聊的列表
     * @param callBack
     */
    public  static  void getLiaodeList(int page,HttpCallBack callBack){
        String url = "";
        HttpConfig config = new HttpConfig();
        config.cacheTime =0;//缓存1小时
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        params.put("_page",page);
        kjh.post(baseHost+url,params, callBack);
    }

    /**
     * 获取聊的列表缓存
     */
    public  static  String  getLiaodeListCache(){
        String url = "";
        KJHttp kjh = new KJHttp();
        String cache = kjh.getStringCache(url);
        return cache;
    }

    /**
     * 获取聊的详情
     * @param callBack
     */
    public  static  void getLiaodeDetail(String tid,HttpCallBack callBack){
        String url = "";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;//缓存1小时
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        params.put("_tId",tid);
        KJHttp kjh = new KJHttp(config);
        kjh.post(baseHost+url,params, callBack);
    }


    /**
     * 获取逛的列表
     * @param callBack
     */
    public  static  void getGuangdeList(HttpCallBack callBack){
        String url = "";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;//缓存1小时
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        kjh.get(baseHost + url, params, callBack);
    }

    /**
     * 吃的点赞
     * @param callBack
     */
    public  static  void doLikeForChide(String cid,HttpCallBack callBack){
        String url = "doLikeCookbook";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        params.put("_cid", cid);
        kjh.get(baseHost + url, params, callBack);
    }

    /**
     * 吃的取消点赞
     * @param callBack
     */
    public  static  void cancelLikeForChide(String cid,HttpCallBack callBack){
        String url = "doCancelLikeCookbook";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        params.put("_cid",cid);
        kjh.get(baseHost + url, params, callBack);
    }



    /**
     * 吃的收藏
     * @param callBack
     */
    public  static  void doCollectForChide(String cid,HttpCallBack callBack){
        String url = "doCollectCookbook";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        params.put("_cid",cid);
        kjh.get(baseHost + url, params, callBack);
    }

    /**
     * 吃的取消收藏
     * @param callBack
     */
    public  static  void cancelCollectForChide(String cid,HttpCallBack callBack){
        String url = "doCancelCollectCookbook";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        params.put("_cid",cid);
        kjh.get(baseHost + url, params, callBack);
    }


    /**
     * 获取我的菜谱
     * @param callBack
     */
    public static  void getMyCookbook(int page,int count,HttpCallBack callBack){
        String url = "getMyCookbooks";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        params.put("_page", page);
        params.put("_pre_page", count);
        kjh.get(baseHost + url, params, callBack);
    }

    /**
     * 获取我的收藏
     * @param callBack
     */
    public static  void getMyCollect(int page,int count,HttpCallBack callBack){
        String url = "getMyCollects";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        params.put("_page", page);
        params.put("_pre_page", count);
        kjh.get(baseHost + url, params, callBack);
    }

    /**
     * 编辑资料
     * @param callBack
     */
    public static  void editInfo(String nickname,String email,String motto,int avatarId,HttpCallBack callBack){
        String url = "doUpdateUserInfo";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        params.put("_nickname", nickname);
        params.put("_email", email);
        params.put("_avatar_id",avatarId);
        params.put("_motto", motto);
        kjh.post(baseHost + url, params, callBack);
    }


    /**
     * 修改密码
     * @param callBack
     */
    public static  void modifyPwd(String oldPwd,String newPwd,String confirm,HttpCallBack callBack){
        String url = "doResettingPassword";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        params.put("_original_password", oldPwd);
        params.put("_password",newPwd);
        params.put("_re_password",confirm);
        kjh.post(baseHost+url, params, callBack);
    }

    /**
     * 版本更新(文件下载)
     * @param callBack
     */
    public static  void update(HttpCallBack callBack){
        String url = "";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        KJHttp kjh = new KJHttp(config);
        kjh.get(baseHost + url, callBack);
    }

    /**
     * 意见反馈
     * @param callBack
     */
    public static  void feedBack(String content,HttpCallBack callBack){
        String url = "";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        params.put("_content",content);
        kjh.post(baseHost + url, params, callBack);
    }

    /**
     * 关注者
     * @param callBack
     */
    public static  void getMyFollow(HttpCallBack callBack){
        String url = "getMyFollows";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        kjh.get(baseHost + url, params, callBack);
    }

    /**
     * 关注
     * @param callBack
     */
    public  static  void doFollow(String uid,HttpCallBack callBack){
        String url = "followUserById";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        params.put("_uid", uid);
        kjh.post(baseHost + url, params, callBack);
    }

    /**
     * 取消关注
     * @param callBack
     */
    public  static  void cancelFollow(String uid,HttpCallBack callBack){
        String url = "";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.put("_uid", uid);
        params.putHeaders("ApiKey", AppContext.TOKEN);
        kjh.post(baseHost+url, params, callBack);
    }

    /**
     * 上传文件
     */
    public static void upload(File file,HttpCallBack callBack) {
        String url = "uploadPictures";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        KJLoger.debug("picpath:"+file.getAbsolutePath());
        params.put("_picture", file);
        kjh.post(baseHost + url, params, callBack);

    }

    /**
     * 发布菜谱
     */
    public static void publishCookBook(String  tilte,String desc,int coverId,String cate,String material,String steps,HttpCallBack callBack) {
        String url = "publishCookbook";
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        KJHttp kjh = new KJHttp(config);
        HttpParams params = new HttpParams();
        params.putHeaders("ApiKey", AppContext.TOKEN);
        params.put("_title", tilte);
        params.put("_description", desc);
        params.put("_coverId", coverId);
        params.put("_category_name", cate);
        params.put("_foods", material);
        params.put("_steps",steps);
        kjh.post(baseHost + url, params, callBack);

    }
}
