package com.itspeed.naidou.app.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.flyco.pageindicator.indicator.FlycoPageIndicaor;
import com.itspeed.naidou.R;
import com.itspeed.naidou.app.helper.UIHelper;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

/**
 * Created by jafir on 10/15/15.
 * 用户引导页，第一次打开应用的时候做一个引导
 */
public class GuideActivity extends KJActivity {

    @BindView(id = R.id.viewpager_guide)
    private ViewPager viewPager;
    @BindView(id = R.id.indicator_guide)
    private FlycoPageIndicaor mIndicator;
    private int[ ]  guides = {R.mipmap.welcome2,R.mipmap.welcome3,R.mipmap.welcome5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setRootView() {
     setContentView(R.layout.aty_guide);
    }

    @Override
    public void initData() {
        super.initData();


        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return guides.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = View.inflate(aty, R.layout.item_viewpager_guide, null);

                ImageView imageView = (ImageView) view.findViewById(R.id.item_viewpager_guide_img);
                imageView.setImageResource(guides[position % guides.length]);
                if (position == guides.length - 1) {
                    ImageView enter = (ImageView) view.findViewById(R.id.item_viewpager_guide_login);
                    enter.setVisibility(View.VISIBLE);
                    enter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UIHelper.showLogin(aty);
                            GuideActivity.this.finish();
                        }
                    });
                }
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        mIndicator.setViewPager(viewPager, guides.length);
    }


    @Override
    protected void onDestroy() {
        setContentView(R.layout.view_null);
        viewPager = null;
        mIndicator = null;
        guides = null;
        System.gc();
        super.onDestroy();
    }
}
