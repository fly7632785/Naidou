package com.itspeed.naidou.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.adapter.ChideAdapter;
import com.itspeed.naidou.app.util.UIHelper;
import com.itspeed.naidou.app.view.PullToRefreshBase;
import com.itspeed.naidou.app.view.PullToRefreshList;
import com.itspeed.naidou.model.bean.CookBook;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.SupportFragment;
import org.kymjs.kjframe.ui.ViewInject;

import java.util.ArrayList;

/**
 * Created by jafir on 15/9/21.
 * 二级菜单的fragment 里面是listView
 */
public class Level2Fragment extends SupportFragment implements PullToRefreshBase.OnRefreshListener, AdapterView.OnItemClickListener {

    private Context aty;
    private View layout;
    private ArrayList<CookBook> data;
    private ChideAdapter adapter;
    @BindView(id = R.id.level2_list)
    private PullToRefreshList mPullLayout;
    private ListView mListView;



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
        initPull();
        adapter = new ChideAdapter();
        adapter.setData(data);
        mListView.setAdapter(adapter);
    }

    private void initPull() {
        mPullLayout = (PullToRefreshList) layout.findViewById(R.id.level2_list);
        mPullLayout.setOnRefreshListener(this);
        mPullLayout.setPullLoadEnabled(true);
        mListView = mPullLayout.getRefreshView();
        mListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mListView.setOnItemClickListener(this);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                mPullLayout.onPullDownRefreshComplete();
            }else if(msg.what == 2){
                mPullLayout.onPullUpRefreshComplete();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UIHelper.showChideDetail(aty);
    }
}
