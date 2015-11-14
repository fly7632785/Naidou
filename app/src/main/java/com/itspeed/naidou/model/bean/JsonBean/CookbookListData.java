package com.itspeed.naidou.model.bean.JsonBean;

import com.itspeed.naidou.model.bean.CookBook;

import java.util.ArrayList;

/**
 * Created by jafir on 11/2/15.
 */
public class CookbookListData extends ListData {
    private ArrayList<CookBook> list ;

    public ArrayList<CookBook> getList() {
        return list;
    }

    public void setList(ArrayList<CookBook> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "CookbookListData{" +
                "list=" + list +
                '}';
    }
}
