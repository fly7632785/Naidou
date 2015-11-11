package com.itspeed.naidou.app.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.itspeed.naidou.R;
import com.itspeed.naidou.model.bean.Level2Cate;

import org.kymjs.kjframe.utils.DensityUtils;

import java.util.ArrayList;

/**
 * Created by jafir on 15/9/21.
 * 父母的fragment 这是一级菜单的fragment
 */
public class ParentFragment extends Level1Fragment {


    @Override
    protected void initData() {
        super.initData();
        //初始化
        fragments = new ArrayList<>();
        level2Cate = new Level2Cate();

        //从服务解析标题菜单
        level2Cate.setType(0);
        titles = new String[]{"备孕","孕前","孕中","孕晚","月子"};
        level2Cate.setNames(titles);




//        getData();

        //动态添加二级子fragment
        for (int i=0;i < level2Cate.getCount();i++) {
            //这里的数据要做不同的处理data
            Level2Fragment fragment = new Level2Fragment(i,aty);
            fragments.add(fragment);
        }

        //初始化设置viewpager
        viewPager = (ViewPager) layout.findViewById(R.id.view_pager);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return level2Cate.getCount();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return level2Cate.getNames()[position];
            }
        });

        //初始化pagerIndicator
        pagerSlidingTabStrip = (PagerSlidingTabStrip) layout.findViewById(R.id.tabs);
        pagerSlidingTabStrip.setTextColorResource(R.color.light_black);
        pagerSlidingTabStrip.setTextSize(DensityUtils.dip2px(aty, INDICATOR_FONT_SIZE));
        pagerSlidingTabStrip.setViewPager(viewPager);


    }


}
