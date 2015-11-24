package com.itspeed.naidou.model.bean;

/**
 * Created by jafir on 15/9/2.
 */
public class User {
    //用户id
    private String uid;
    //用户昵称
    private String nickname;
    //用户个性签名
    private String motto;
    //用户头像url
    private String avatar;
    //奶豆数
    private int coins;
    //token
    private String apiKey;
    //关注数
    private int follows;
    //邮箱
    private String email;


    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", motto='" + motto + '\'' +
                ", avatar='" + avatar + '\'' +
                ", coins=" + coins +
                ", apiKey='" + apiKey + '\'' +
                '}';
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }


    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
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
