package com.itspeed.naidou.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;
import com.itspeed.naidou.app.activity.SimpleBackActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.adapter.FollowAdapter;
import com.itspeed.naidou.model.bean.User;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;

/**
 * Created by jafir on 15/9/28.
 */
public class FollowFragment extends TitleBarSupportFragment{

    private SimpleBackActivity aty;
    private View layout;
    @BindView(id = R.id.mycookbook_list)
    private ListView mListView;
    private FollowAdapter mAdapter;
    private ArrayList<User> mData;
    @BindView(id = R.id.mycookbook_text)
    private TextView mEmptyText;


    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layout = View.inflate(aty, R.layout.frag_mycookbook,null);
        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aty = (SimpleBackActivity) getActivity();
        onChange();
    }

    @Override
    protected void initData() {
        super.initData();
        mAdapter = new FollowAdapter();
        mData = new ArrayList<>();
//        //模拟添加数据
//        for (int i = 0;i<10;i++){
//            User user = new User();
//            data.add(user);
//        }
        requestData();
    }

    private void requestData() {
        NaidouApi.getMyFollow(new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("getMyFollow:"+t);
                if(Response.getSuccess(t)){
                    mData = Response.getFollowList(t);
                    if(mData.isEmpty() || mData==null){
                        mEmptyText.setVisibility(View.VISIBLE);
                    }else {
                        mAdapter.setData(mData);
                        mListView.setAdapter(mAdapter);
                    }
                }
            }
        });

    }

    @Override
    public void onChange() {
        super.onChange();
        setTitleType(TitleBarActivity.TitleBarType.Titlebar2);
        setBackImage(R.drawable.selector_title_back);
        setTitle("关注");
        setMenuImage(null);
    }


    @Override
    public void onBackClick() {
        super.onBackClick();
        aty.finish();
    }
    @Override
    public void onDestroy() {
        aty = null;
        layout= null;
        mData = null;
        mListView = null;
        mAdapter = null;
        super.onDestroy();
    }
}
