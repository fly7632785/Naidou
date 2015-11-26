package com.itspeed.naidou.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itspeed.naidou.model.bean.CookBook;
import com.itspeed.naidou.model.bean.JsonBean.CookbookListData;
import com.itspeed.naidou.model.bean.JsonBean.Entity;
import com.itspeed.naidou.model.bean.JsonBean.FollowListData;
import com.itspeed.naidou.model.bean.JsonBean.TopicListData;
import com.itspeed.naidou.model.bean.Topic;
import com.itspeed.naidou.model.bean.User;

import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;

/**
 * Created by jafir on 15/9/2.
 * <p/>
 * 响应类，封装了Http请求返回的数据结构；
 */
public class Response {


    /**
     * 获取数据实体
     *
     * @param string
     * @return
     */
    public static Entity getEntity(String string) {
        return JSON.parseObject(string, Entity.class);
    }

    //获取返回信息
    public static String getMessage(String string) {
        return JSON.parseObject(string, Entity.class).getMessage();
    }

    //获取返回状态
    public static Boolean getStatus(String string) {
        return JSON.parseObject(string, Entity.class).isStatus();
    }

    //获取返回操作数据的结果
    public static Boolean getSuccess(String string) {
        return JSON.parseObject(string, Entity.class).is_success();
    }

    //获取返回错误码
    public static int getErrorCode(String string) {
        return JSON.parseObject(string, Entity.class).getError_code();
    }

    /**
     * 返回食谱列表
     *
     * @param data
     * @return 菜谱列表
     */
    public static ArrayList<CookBook> getChideList(String data) {
        Entity cookBookEntity = JSON.parseObject(data, Entity.class);
        CookbookListData listData = JSON.parseObject(cookBookEntity.getData().toString(), CookbookListData.class);
        return listData.getList();
    }


    /**
     * 返回话题列表
     *
     * @param data
     * @return 话题列表
     */
    public static ArrayList<Topic> getLiaodeList(String data) {
        Entity topicEntity = JSON.parseObject(data, Entity.class);
        TopicListData listData = JSON.parseObject(topicEntity.getData().toString(), TopicListData.class);
        return listData.getList();
    }


    /**
     * 获取用户信息（我的、别人的）
     * @param data
     * @return
     */
    public static User getUserInfo(String data){
        Entity entity = JSON.parseObject(data, Entity.class);
        JSONObject object = JSON.parseObject(entity.getData().toString());
        String userInfo = object.getString("userInfo");
//        KJLoger.debug("userinfo:"+userInfo);
        User user = JSON.parseObject(userInfo,User.class);
        return user;
    }


    /**
     * 获取关注者信息
     */
    public static ArrayList<User> getFollowList(String data) {
        Entity entity = JSON.parseObject(data, Entity.class);
        FollowListData listData = JSON.parseObject(entity.getData().toString(), FollowListData.class);
        KJLoger.debug("getFollowList:" + listData.getList().toString());
        return listData.getList();
    }

    /**
     * 获取我的收藏
     * @return
     */
    public static ArrayList<CookBook> getMyCollectList(String data) {
        Entity entity = JSON.parseObject(data, Entity.class);
        CookbookListData listData = JSON.parseObject(entity.getData().toString(), CookbookListData.class);
        KJLoger.debug("getMyCollectList::" + listData.getList().toString());
        return listData.getList();

    }


    /**
     * 获取我的菜谱
     * @return
     */
    public static ArrayList<CookBook> getMyCookbookList(String data) {
        Entity entity = JSON.parseObject(data, Entity.class);
        CookbookListData listData = JSON.parseObject(entity.getData().toString(), CookbookListData.class);
        KJLoger.debug("getMyCookbookList::" + listData.getList().toString());
        return listData.getList();

    }

    /**
     * 获取APIkey
     * @param data
     * @return
     */
    public static String getApiKey(String data) {
        Entity entity = JSON.parseObject(data, Entity.class);
        JSONObject object = JSON.parseObject(entity.getData().toString());
        return  object.getString("apiKey");
    }
}
