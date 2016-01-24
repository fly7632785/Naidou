package com.itspeed.naidou.app.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.squareup.picasso.Picasso;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by jafir on 16/1/15.
 */
public class StepScanActivity extends KJActivity {

    @BindView(id = R.id.step_scan_viewpager)
    private ViewPager mViewPager;
    @BindView(id = R.id.step_scan_currentstep)
    private TextView mCurrentStep;
    private String[] urls;
    private String[] descs;
    private int oldposition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void setRootView() {

        setContentView(R.layout.aty_step_scan);
    }

    @Override
    public void initData() {
        super.initData();

        urls = getIntent().getStringArrayExtra("urls");
        descs = getIntent().getStringArrayExtra("descs");
        oldposition = getIntent().getIntExtra("position", 0);
        mCurrentStep.setText((oldposition+1) + "/" + urls.length);

        mViewPager.setAdapter(new MyPagerAdapter());
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentStep.setText((position+1)+"/"+urls.length);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(oldposition);

    }


    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return urls.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(container.getContext(), R.layout.item_viewpager_step_scan, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            PhotoView photoView = (PhotoView) view.findViewById(R.id.item_viewpager_step_scan_photo);
            Picasso.with(container.getContext()).load(urls[position]).error(R.mipmap.default_bg).into(photoView);
            TextView desc = (TextView) view.findViewById(R.id.item_viewpager_step_scan_desc);
            desc.setText(descs[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}
