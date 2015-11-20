package com.itspeed.naidou.app.fragment;

import android.content.Context;
import android.os.Bundle;
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
import com.itspeed.naidou.app.view.PullToRefreshBase;
import com.itspeed.naidou.app.view.PullToRefreshList;
import com.itspeed.naidou.model.bean.CookBook;
import com.itspeed.naidou.model.bean.JsonBean.Entity;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.SupportFragment;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.SystemTool;

import java.util.ArrayList;

/**
 * Created by jafir on 15/9/21.
 * 二级菜单的fragment 里面是listView
 */
public class Level2Fragment extends SupportFragment implements PullToRefreshBase.OnRefreshListener, AdapterView.OnItemClickListener {

    private Context aty;
    private View layout;

    //总的数据
    private ArrayList<CookBook> data;
    //每次请求返回的数据
    private ArrayList<CookBook> addData;
    private String cache;

    private ChideAdapter mAdapter;
    @BindView(id = R.id.level2_list)
    private PullToRefreshList mPullLayout;
    private ListView mListView;

//    @BindView(id = R.id.empty_layout)
//    private EmptyLayout mEmptyLayout;

    //种类标识    0-4 为父母的 备孕 孕初 孕中 孕晚 月子  5-9 为孩子的 4-6月 7-8月 9-12月 1-2岁 3-6岁
    private int cate;

    public String[] category = new String[] {
      "CATE_PARENT_BEIYUN","CATE_PARENT_YUNQIAN","CATE_PARENT_YUNZHONG","CATE_PARENT_YUNWAN","CATE_PARENT_YUEZI",
           "CATE_CHILD_PHASE1","CATE_CHILD_PHASE2","CATE_CHILD_PHASE3","CATE_CHILD_PHASE4","CATE_CHILD_PHASE5",
    };


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
        addData = new ArrayList<>();
        mAdapter = new ChideAdapter();
        //第一次进入请求数据
        requestData(1);
//        mEmptyLayout.setOnLayoutClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //这个布局会在加载不到数据之后显示
//                //然后供第二次 点击重新加载
//                mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
//                requestData(1);
//            }
//        });

    }



    /**
     * 上拉加载数据
     */
    private void loadData() {
        double page = (double)data.size() / 10;
        page += 1.9; // 因为服务器返回的可能会少于10条，所以采用小数进一法加载下一页
        requestData((int) page);
    }

    /**
     * 请求数据
     * @param page 请求的页数 第几页
     * @return
     */
    private ArrayList<CookBook> requestData(int page){

        NaidouApi.getChideList(category[cate], page, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("sssss:" + t);
                Entity entity = Response.getEntity(t);
//                if (entity.is_success()) {
                //解析数据
                ArrayList<CookBook> addData = new ArrayList<CookBook>();
//                    addData = Response.getChideList(t);
                //!!!!!!!异步 设置数据 只能在这里

                for (int i = 0; i < 5; i++) {
                    addData.add(new CookBook(true, "title" + i, "12312312", true,12,34));
                }
                //第一次 加载数据
                //请求第一页数据 然后装入总的data
                if(data.isEmpty()) {
                    data.addAll(addData);
                    mAdapter.setData(data);
                    mListView.setAdapter(mAdapter);
                }else {
                    mAdapter.addData(addData);
                }
                //只要请求到数据就 去掉
//                mEmptyLayout.dismiss();

//                }
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
//                    mEmptyLayout.setErrorType(EmptyLayout.NODATA);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mPullLayout.onPullDownRefreshComplete();
                mPullLayout.onPullUpRefreshComplete();
            }
        });
        return addData;
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
        ViewInject.toast("刷新");
        //清除原有数据
        data.clear();
        //请求第一页 然后解析 设置数据
        requestData(1);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        //加载数据
        ViewInject.toast("加载");
        loadData();
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
