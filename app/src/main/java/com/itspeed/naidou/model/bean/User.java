package com.itspeed.naidou.model.bean;

/**
 * Created by jafir on 15/9/2.
 */
public class User {
    //用户id
    private String id;
    //用户昵称
    private String name;
    //用户个性签名
    private String signature;
    //用户头像url
    private String portrait;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
