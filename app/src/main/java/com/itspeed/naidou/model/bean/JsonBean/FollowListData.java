package com.itspeed.naidou.model.bean.JsonBean;

import com.itspeed.naidou.model.bean.User;

import java.util.ArrayList;

/**
 * Created by jafir on 11/2/15.
 */
public class FollowListData extends ListData {
    private ArrayList<User> list ;

    public ArrayList<User> getList() {
        return list;
    }

    public void setList(ArrayList<User> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "FollowListData{" +
                "list=" + list +
                '}';
    }
}
