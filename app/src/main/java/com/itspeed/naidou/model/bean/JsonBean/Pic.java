package com.itspeed.naidou.model.bean.JsonBean;

/**
 * Created by jafir on 15/12/3.
 */
public class Pic {

    private String path;
    private int id;


    @Override
    public String toString() {
        return "Pic{" +
                "path='" + path + '\'' +
                ", id=" + id +
                '}';
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
