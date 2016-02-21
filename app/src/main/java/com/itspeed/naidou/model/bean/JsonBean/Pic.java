package com.itspeed.naidou.model.bean.JsonBean;

import java.io.Serializable;

/**
 * Created by jafir on 15/12/3.
 */
public class Pic implements Serializable{

    private String path;
    private String localPath;
    private int id;


    public Pic() {
        path = "0";
        localPath = "0";
        id = 0;
    }

    @Override
    public String toString() {
        return "Pic{" +
                "path='" + path + '\'' +
                ", localPath='" + localPath + '\'' +
                ", id=" + id +
                '}';
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
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
