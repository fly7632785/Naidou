package com.itspeed.naidou.model.bean;

/**
 * Created by jafir on 15/9/2.
 */
public class User {
    //用户id
    private String uId;
    //用户昵称
    private String nickname;
    //用户个性签名
    private String motto;
    //用户头像url
    private String avatar;


    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
