package com.itspeed.naidou.app.helper;

import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;

/**
 * Created by jafir on 16/3/2.
 *
 * 操作帮助类
 * 把一些常用的复用的 操作API的方法归结于这里
 *
 */
public class OperateHelper {

    public static void doCollect(String cid) {
        NaidouApi.doCollectForChide(cid, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("收藏成功：" + t);
                if (Response.getSuccess(t)) {
                    ViewInject.toast("收藏成功");
                }
            }
        });
    }

    public static void cancelCollect(String cid) {
        NaidouApi.cancelCollectForChide(cid, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("取消收藏成功：" + t);
                if (Response.getSuccess(t)) {
                    ViewInject.toast("取消收藏成功");
                }
            }
        });
    }

    public  static void doLike(String cid) {
        NaidouApi.doLikeForChide(cid, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("点赞成功：" + t);
                if (Response.getSuccess(t)) {
                    ViewInject.toast("点赞成功");
                }
            }
        });

    }

    public  static void cancelLike(String cid) {
        NaidouApi.cancelLikeForChide(cid, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("取消点赞成功：" + t);
                if (Response.getSuccess(t)) {
                    ViewInject.toast("取消点赞成功");
                }
            }
        });
    }


    public static void  doFollow(String uid){
        NaidouApi.doFollow(uid, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                if(Response.getSuccess(t)){
                    ViewInject.toast("关注成功");
                }
            }
        });
    }
    public static void  cancelFollow(String uid){
        NaidouApi.cancelFollow(uid, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                if(Response.getSuccess(t)){
                    ViewInject.toast("取消关注");
                }
            }
        });
    }
}
