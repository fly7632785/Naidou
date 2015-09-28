package com.itspeed.naidou.model.bean;

/**
 * Created by jafir on 15/9/14.
 */
public class Topic {

    //标题
    private String title;
    //时间
    private String time;
    //浏览数（热度）
    private int countOfScan;
    //图片url
    private String img;


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

    public int getCountOfScan() {
        return countOfScan;
    }

    public void setCountOfScan(int countOfScan) {
        this.countOfScan = countOfScan;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
