package com.itspeed.naidou.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.itspeed.naidou.R;
import com.itspeed.naidou.app.adapter.ChideAdapter;
import com.itspeed.naidou.model.bean.CookBook;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.SupportFragment;
import org.kymjs.kjframe.ui.ViewInject;

import java.util.ArrayList;

/**
 * Created by jafir on 15/9/21.
 */
public class Level2Fragment extends SupportFragment implements PullToRefreshBase.OnRefreshListener2{

    private Context aty;
    private View layout;
    private ArrayList<CookBook> data;
    private ChideAdapter adapter;
    @BindView(id = R.id.level2_list)
    private PullToRefreshListView mListView;



    public Level2Fragment(ArrayList<CookBook> data, Context aty) {
        this.data = data;
        this.aty = aty;
    }


    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        aty = getActivity();
        layout = View.inflate(aty,R.layout.frag_level2,null);
        return layout;
    }

    @Override
    protected void initData() {
        super.initData();
        mListView = (PullToRefreshListView) layout.findViewById(R.id.level2_list);
        mListView.setOnRefreshListener(this);
        adapter = new ChideAdapter();
        adapter.setData(data);
        mListView.setAdapter(adapter);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                mListView.onRefreshComplete();
            }else if(msg.what == 2){
                mListView.onRefreshComplete();
            }
        }
    };

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        //刷新数据
        ViewInject.toast("刷新");
        handler.sendEmptyMessageDelayed(1,3000);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        //加载数据
        ViewInject.toast("加载");
        handler.sendEmptyMessageDelayed(2,3000);
    }
}
