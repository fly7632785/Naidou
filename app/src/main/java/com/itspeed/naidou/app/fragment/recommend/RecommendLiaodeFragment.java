package com.itspeed.naidou.app.fragment.recommend;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.activity.MainActivity;
import com.itspeed.naidou.app.adapter.RecommendRecyclerAdapter;
import com.itspeed.naidou.app.util.UIHelper;
import com.itspeed.naidou.app.view.AdapterIndicator;
import com.itspeed.naidou.model.bean.CookBook;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.SupportFragment;

import java.util.ArrayList;

/**
 * Created by jafir on 10/23/15.
 */
public class RecommendLiaodeFragment extends SupportFragment{
    private ArrayList<CookBook> mData;
    private  RecyclerViewPager mRecyclerView;
    private  AdapterIndicator mIndicator;
    private View layout;
    private MainActivity aty;
    @BindView(id = R.id.recommend_liaode_img,click = true)
    private ImageView mImageView;
    private Handler mHandler;

    public RecommendLiaodeFragment(Handler mHandler) {
        this.mHandler = mHandler;
    }

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        aty = (MainActivity) getActivity();
        layout = View.inflate(aty, R.layout.frag_recommend_liaode,null);
        return layout;
    }


    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()){

            case R.id.recommend_liaode_img:
                mHandler.sendEmptyMessage(2);
                break;
        }
    }


    @Override
    protected void initData() {
        super.initData();
        init();
    }

    private void init() {
        mRecyclerView = (RecyclerViewPager) layout.findViewById(R.id.recommend_liaode_recyler_pager);
        mIndicator = (AdapterIndicator) layout.findViewById(R.id.recommend_liaode_indicator);

        mIndicator.setClickable(false);

        //最大页数5
        mIndicator.setPointCount(5);

        //布局管理器  线性
        LinearLayoutManager lllayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(lllayout);

        //模拟数据
        mData = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CookBook cookBook = new CookBook();
            cookBook.setTitle("小鸡炖蘑菇" + i);
            mData.add(cookBook);
        }
        RecommendRecyclerAdapter adapter = new RecommendRecyclerAdapter(mData);

        //绑定indicator和recyclerViewpager
        mIndicator.bindView(mRecyclerView);

        mRecyclerView.setAdapter(adapter);
        //设置现在的 卡片position
        mRecyclerView.scrollToPosition(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % 5);

        mRecyclerView.setHasFixedSize(true);
        adapter.setOnItemClickListener(new RecommendRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                ViewInject.toast("点击：" + position);
                UIHelper.showChideDetail(aty);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int childCount = recyclerView.getChildCount();
                int width = recyclerView.getChildAt(0).getWidth();
                int padding = (recyclerView.getWidth() - width) / 2;
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
                RecyclerViewPager mRecyclerView = (RecyclerViewPager) v;
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
    }
}
