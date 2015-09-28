package com.itspeed.naidou.model.bean;

/**
 * Created by jafir on 15/9/24.
 */
public class Level2Cate  {
    /**
     * 二级菜单有多少个
     */
    private int count;
    /**
     * 二级菜单的名字
     */
    private String[]  names;
    /**
     *二级菜单的类型 0为父母  1为孩子
     */
    private int type;


    public int getCount() {
        return names.length;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
