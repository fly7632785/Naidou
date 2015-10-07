package com.itspeed.naidou.app.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.activity.MainActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.adapter.RecommendRecyclerAdapter;
import com.itspeed.naidou.app.view.AdapterIndicator;
import com.itspeed.naidou.model.bean.CookBook;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import org.kymjs.kjframe.ui.ViewInject;

import java.util.ArrayList;

/**
 * Created by jafir on 15/9/1.
 */
public class RecommenFragment extends TitleBarSupportFragment {

    MainActivity aty;
    private View layout;
//    private Image3DSwitchView imageSwitchView;
//    private Image3DView [] image3DViews = new Image3DView[5];


    private ArrayList<CookBook> mData;
    private RecyclerViewPager mRecyclerView;
    private AdapterIndicator mIndicator;

    @Override
    public void onChange() {
        super.onChange();
        setTitleType(TitleBarActivity.TitleBarType.Titlebar2);
        setTitle("推荐");
        setBackImage(null);
        setMenuImage(null);
    }


    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
//            case R.id.image1:
//                ViewInject.toast("点击了1");
//                break;
//            case R.id.image2:
//                ViewInject.toast("点击了2");
//                break;
//            case R.id.image3:
//                ViewInject.toast("点击了3");
//                break;
//            case R.id.image4:
//                ViewInject.toast("点击了4");
//                break;
//            case R.id.image5:
//                ViewInject.toast("点击了5");
//                break;

        }
    }

    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        aty = (MainActivity) getActivity();
        onChange();
        layout = View.inflate(aty, R.layout.frag_recommend, null);
        return layout;
    }

    @Override
    protected void initData() {
        super.initData();

        mRecyclerView = (RecyclerViewPager) layout.findViewById(R.id.list);
        mIndicator = (AdapterIndicator) layout.findViewById(R.id.indicator);

        mIndicator.setClickable(false);

        mIndicator.setPointCount(5);

        LinearLayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layout);

        mData = new ArrayList<>();
        for (int i = 0;i< 5;i++){
            CookBook cookBook = new CookBook();
            cookBook.setTitle("小鸡炖蘑菇"+i);
            mData.add(cookBook);
        }
        RecommendRecyclerAdapter adapter = new RecommendRecyclerAdapter(mData);

        mIndicator.bindView(mRecyclerView);

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.scrollToPosition(Integer.MAX_VALUE/2-Integer.MAX_VALUE/2%5);

        mRecyclerView.setHasFixedSize(true);
        adapter.setOnItemClickListener(new RecommendRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ViewInject.toast("点击："+position);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int childCount = mRecyclerView.getChildCount();
                int width = mRecyclerView.getChildAt(0).getWidth();
                int padding = (mRecyclerView.getWidth() - width) / 2;
                for (int j = 0; j < childCount; j++) {
                    View v = recyclerView.getChildAt(j);
                    //往左 从 padding 到 -(v.getWidth()-padding) 的过程中，由大到小
                    float rate = 0;
                    if (v.getLeft() <= padding) {
                        if (v.getLeft() >= padding - v.getWidth()) {
                            rate = (padding - v.getLeft()) * 1f / v.getWidth();
                        } else {
                            rate = 1;
                        }
                        v.setScaleY(1 - rate * 0.1f);
                        v.setScaleX(1 - rate * 0.1f);
                    } else {
                        //往右 从 padding 到 recyclerView.getWidth()-padding 的过程中，由大到小
                        if (v.getLeft() <= recyclerView.getWidth() - padding) {
                            rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                        }
                        v.setScaleY(0.9f + rate * 0.1f);
                        v.setScaleX(0.9f + rate * 0.1f);
                    }
                }
            }
        });

        mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mRecyclerView.getChildCount() < 3) {
                    if (mRecyclerView.getChildAt(1) != null) {
                        View v1 = mRecyclerView.getChildAt(1);
                        v1.setScaleY(0.9f);
                    }
                } else {
                    if (mRecyclerView.getChildAt(0) != null) {
                        View v0 = mRecyclerView.getChildAt(0);
                        v0.setScaleY(0.9f);
                    }
                    if (mRecyclerView.getChildAt(2) != null) {
                        View v2 = mRecyclerView.getChildAt(2);
                        v2.setScaleY(0.9f);
                    }
                }

            }
        });

//        imageSwitchView = (Image3DSwitchView) layout.findViewById(R.id.image_switch_view);
//        image3DViews[0] = (Image3DView) layout.findViewById(R.id.image1);
//        image3DViews[1] = (Image3DView) layout.findViewById(R.id.image2);
//        image3DViews[2] = (Image3DView) layout.findViewById(R.id.image3);
//        image3DViews[3] = (Image3DView) layout.findViewById(R.id.image4);
//        image3DViews[4] = (Image3DView) layout.findViewById(R.id.image5);
//
//        for(int i=0;i<image3DViews.length;i++){
//            image3DViews[i].setOnClickListener(this);
//            new KJBitmap().display(image3DViews[i],ChideAdapter.img[i]);
//        }
//
//        imageSwitchView.setOnImageSwitchListener(new Image3DSwitchView.OnImageSwitchListener() {
//            @Override
//            public void onImageSwitch(int currentImage) {
//                KJLoger.debug("current image is " + currentImage);
//            }
//        });
//        imageSwitchView.setCurrentImage(1);
    }


    @Override
    public void onDestroy() {
//        imageSwitchView.clear();
    }
}
