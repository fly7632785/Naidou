package com.itspeed.naidou.api;

import com.alibaba.fastjson.JSON;
import com.itspeed.naidou.model.bean.CookBook;
import com.itspeed.naidou.model.bean.JsonBean.CookbookListData;
import com.itspeed.naidou.model.bean.JsonBean.Entity;
import com.itspeed.naidou.model.bean.JsonBean.TopicListData;
import com.itspeed.naidou.model.bean.Topic;

import java.util.ArrayList;

/**
 * Created by jafir on 15/9/2.
 *
 * 响应类，封装了Http请求返回的数据结构；
 *
 */
public class Response {


    /**
     * 获取数据实体
     * @param string
     * @return
     */
    public static Entity getEntity(String string){
        return JSON.parseObject(string,Entity.class);
    }

    //获取返回信息
    public static String getMessage(String string){
        return JSON.parseObject(string,Entity.class).getMessage();
    }
    //获取返回状态
    public static Boolean getStatus(String string){
        return JSON.parseObject(string,Entity.class).isStatus();
    }
    //获取返回操作数据的结果
    public static Boolean getSuccess(String string){
        return JSON.parseObject(string,Entity.class).isSuccess();
    }
    //获取返回错误码
    public static int getErrorCode(String string){
        return JSON.parseObject(string,Entity.class).getErrorCode();
    }

    /**
     * 返回食谱列表
     * @param data
     * @return 菜谱列表
     */
    public static ArrayList<CookBook> getChideList(String data){
        Entity cookBookEntity = JSON.parseObject(data, Entity.class);
        CookbookListData listData = JSON.parseObject(cookBookEntity.getData().toString(), CookbookListData.class);
        return listData.getList();
    }


    /**
     * 返回话题列表
     * @param data
     * @return 话题列表
     */
    public static ArrayList<Topic> getLiaodeList(String data){
        Entity topicEntity = JSON.parseObject(data, Entity.class);
        TopicListData listData = JSON.parseObject(topicEntity.getData().toString(), TopicListData.class);
        return listData.getList();
    }

}
