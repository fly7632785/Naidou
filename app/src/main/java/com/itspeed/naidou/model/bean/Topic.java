package com.itspeed.naidou.model.bean;

/**
 * Created by jafir on 15/9/14.
 */
public class Topic {

    private int tId;
    //标题
    private String title;
    //时间
    private String time;
    //浏览数（热度）
    private int countOfScan;
    //图片url
    private String img;
    //描述


    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;


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
