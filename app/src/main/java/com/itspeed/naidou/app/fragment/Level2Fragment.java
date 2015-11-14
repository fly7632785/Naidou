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
import android.widget.Toast;

import com.itspeed.naidou.R;
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;
import com.itspeed.naidou.app.adapter.ChideAdapter;
import com.itspeed.naidou.app.util.UIHelper;
import com.itspeed.naidou.app.view.EmptyLayout;
import com.itspeed.naidou.app.view.PullToRefreshBase;
import com.itspeed.naidou.app.view.PullToRefreshList;
import com.itspeed.naidou.model.bean.CookBook;
import com.itspeed.naidou.model.bean.JsonBean.Entity;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.SupportFragment;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.StringUtils;
import org.kymjs.kjframe.utils.SystemTool;

import java.util.ArrayList;

/**
 * Created by jafir on 15/9/21.
 * 二级菜单的fragment 里面是listView
 */
public class Level2Fragment extends SupportFragment implements PullToRefreshBase.OnRefreshListener, AdapterView.OnItemClickListener {

    private Context aty;
    private View layout;

    private ArrayList<CookBook> data;
    private String cache;

    private ChideAdapter mAdapter;
    @BindView(id = R.id.level2_list)
    private PullToRefreshList mPullLayout;
    private ListView mListView;

    @BindView(id = R.id.empty_layout)
    private EmptyLayout mEmptyLayout;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                mPullLayout.onPullDownRefreshComplete();
            }else if(msg.what == 2){
                mPullLayout.onPullUpRefreshComplete();
            }
            mEmptyLayout.dismiss();
        }
    };


    //种类标识    0-4 为父母的 备孕 孕初 孕中 孕晚 月子  5-9 为孩子的 4-6月 7-8月 9-12月 1-2岁 3-6岁
    private int cate;


    public Level2Fragment(int cate, Context aty) {
        this.aty = aty;
        this.cate = cate;
    }


    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        aty = getActivity();
        layout = View.inflate(aty, R.layout.frag_level2, null);
        KJLoger.debug(this.getClass().getSimpleName() + "inflaterView");
        return layout;
    }

    @Override
    protected void initData() {
        super.initData();
        initPull();
        data = new ArrayList<>();
        //模拟添加数据
        for (int i = 0;i<10;i++){
            CookBook cookBook = new CookBook();
            data.add(cookBook);
        }
//        fillUI();
        mAdapter = new ChideAdapter();
        mAdapter.setData(data);
        mListView.setAdapter(mAdapter);
        handler.sendEmptyMessageDelayed(3, 1000);

        mEmptyLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                refresh();
            }
        });

    }

    private void fillUI() {
        cache = NaidouApi.getChideListCache();
        if (!StringUtils.isEmpty(cache)) {
            KJLoger.debug("youcache");
            data = Response.getChideList(cache);
            mAdapter = new ChideAdapter();
            mAdapter.setData(data);
            mListView.setAdapter(mAdapter);
            mEmptyLayout.dismiss();
        }
        refresh();
    }

    private void refresh(){
        double page = (double)data.size() / 10;
        page += 1.9; // 因为服务器返回的可能会少于10条，所以采用小数进一法加载下一页
        refresh((int) page);
    }

    private void refresh(int  index) {
        NaidouApi.getChideList(cate, index, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                Entity entity = Response.getEntity(t);
                if (entity.isStatus()) {
                    ArrayList<CookBook> cbs = Response.getChideList(t);
                    if (mAdapter == null) {
                        data = cbs;
                        mAdapter = new ChideAdapter();
                        mAdapter.setData(data);
                        mListView.setAdapter(mAdapter);
                    } else {
                        mAdapter.addData(cbs);
                    }
                    mEmptyLayout.dismiss();
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                if (!SystemTool.checkNet(aty)) {
                    Toast.makeText(aty, "您的网络没有连接", Toast.LENGTH_SHORT).show();
                }
                if (mAdapter != null && mAdapter.getCount() > 0) {
                    return;
                } else {
                    mEmptyLayout.setErrorType(EmptyLayout.NODATA);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mPullLayout.onPullDownRefreshComplete();
                mPullLayout.onPullUpRefreshComplete();
            }
        });
    }





    private void initPull() {
        mPullLayout = (PullToRefreshList) layout.findViewById(R.id.level2_list);
        mPullLayout.setOnRefreshListener(this);
        mPullLayout.setPullLoadEnabled(true);
        mListView = mPullLayout.getRefreshView();
        mListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mListView.setOnItemClickListener(this);
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        //刷新数据
        handler.sendEmptyMessageDelayed(1, 3000);
        refresh(1);
        ViewInject.toast("刷新");
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        //加载数据
        ViewInject.toast("加载");
        refresh();
        handler.sendEmptyMessageDelayed(2, 3000);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UIHelper.showChideDetail(aty);
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KJLoger.debug(this.getClass().getSimpleName() + "onCreate");
    }


    @Override
    public void onPause() {
        super.onPause();
        KJLoger.debug(this.getClass().getSimpleName() + "onPause");
    }


    @Override
    public void onStop() {
        super.onStop();
        KJLoger.debug(this.getClass().getSimpleName() + "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KJLoger.debug(this.getClass().getSimpleName() + "onDestroy");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        KJLoger.debug(this.getClass().getSimpleName() + "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);

    }

}
