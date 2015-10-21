package com.itspeed.naidou.app.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by jafir on 15/10/8.
 * 发布菜谱的 viewpager的适配器   里面包括4步  每部一个view
 */
public class PublishPagerAdapter extends PagerAdapter {

    ArrayList<View> viewLists;

    public PublishPagerAdapter(ArrayList<View> lists) {
        viewLists = lists;
    }

    @Override
    public int getCount() {                                                                 //获得size
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)                       //销毁Item
    {
        container.removeView(viewLists.get(position));
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position)                                //实例化Item
    {
        container.addView(viewLists.get(position));
        return viewLists.get(position);
    }
}
