package com.itspeed.naidou.model.bean.JsonBean;

import com.itspeed.naidou.model.bean.Topic;

import java.util.ArrayList;

/**
 * Created by jafir on 11/2/15.
 */
public class TopicListData extends ListData {
    private ArrayList<Topic> list ;

    public ArrayList<Topic> getList() {
        return list;
    }

    public void setList(ArrayList<Topic> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "TopicListData{" +
                "list=" + list +
                '}';
    }
}
