package com.itspeed.naidou.model.bean;

import java.util.ArrayList;

/**
 * Created by jafir on 15/9/14.
 */
public class CookBook {

    //id
    private int cId;
    //标题
    private String title;
    //时间
    private String time;
    //一级属性（父母、孩子）
    private String type1;
    //二级属性  父母--（备孕、孕初、孕中、孕晚、月子）
    //         孩子--（4-6月、7-8月、9-12月、1岁-2岁、3-6岁）
    private String type2;
    //赞
    private int like;
    //收藏
    private boolean isCollect;
    //食材
    private ArrayList<FoodMaterial> foodMaterials;
    //步骤
    private ArrayList<Step> steps;
    //成品图
    private String productUrl;
    //用户
    private String portraitUrl;
    private String userName;

    private String cover720;

    @Override
    public String toString() {
        return "CookBook{" +
                "cId=" + cId +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", type1='" + type1 + '\'' +
                ", type2='" + type2 + '\'' +
                ", like=" + like +
                ", isCollect=" + isCollect +
                ", foodMaterials=" + foodMaterials +
                ", steps=" + steps +
                ", productUrl='" + productUrl + '\'' +
                ", portraitUrl='" + portraitUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", cover720='" + cover720 + '\'' +
                '}';
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getCover720() {
        return cover720;
    }

    public void setCover720(String cover720) {
        this.cover720 = cover720;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setIsCollect(boolean isCollect) {
        this.isCollect = isCollect;
    }

    public ArrayList<FoodMaterial> getFoodMaterials() {
        return foodMaterials;
    }

    public void setFoodMaterials(ArrayList<FoodMaterial> foodMaterials) {
        this.foodMaterials = foodMaterials;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
