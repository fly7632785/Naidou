package com.itspeed.naidou.model.bean;

import java.util.ArrayList;

/**
 * Created by jafir on 15/9/14.
 */
public class Step {
    //图片
    private ArrayList<String> imgs;
    //描述
    private ArrayList<String> describe;

    public ArrayList<String> getImgs() {
        return imgs;
    }

    public void setImgs(ArrayList<String> imgs) {
        this.imgs = imgs;
    }

    public ArrayList<String> getDescribe() {
        return describe;
    }

    public void setDescribe(ArrayList<String> describe) {
        this.describe = describe;
    }
}
