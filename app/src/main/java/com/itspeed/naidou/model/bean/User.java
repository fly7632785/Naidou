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
    //用户头像ID
    private int avatarId;
    //用户头像url
    private String avatar;
    //奶豆数
    private int coins;
    //关注别人数
    private int followCount;
    //被关注的人数
    private int followedCount;
    //邮箱
    private String email;
    //发布的菜谱数
    private int cookBookCount;


    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", motto='" + motto + '\'' +
                ", avatarId=" + avatarId +
                ", avatar='" + avatar + '\'' +
                ", coins=" + coins +
                ", followCount=" + followCount +
                ", followedCount=" + followedCount +
                ", email='" + email + '\'' +
                ", cookBookCount=" + cookBookCount +
                '}';
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public int getCookBookCount() {
        return cookBookCount;
    }

    public void setCookBookCount(int cookBookCount) {
        this.cookBookCount = cookBookCount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public int getFollowedCount() {
        return followedCount;
    }

    public void setFollowedCount(int followedCount) {
        this.followedCount = followedCount;
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
