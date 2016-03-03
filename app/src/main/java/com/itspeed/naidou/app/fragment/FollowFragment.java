package com.itspeed.naidou.app.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;
import com.itspeed.naidou.app.activity.SimpleBackActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.adapter.FollowAdapter;
import com.itspeed.naidou.app.helper.UIHelper;
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
    private ProgressDialog dialog;


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

        dialog = new ProgressDialog(aty);
        dialog.setMessage("加载中...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        mAdapter = new FollowAdapter();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UIHelper.showZone(aty,mData.get(position).getUid());
            }
        });
        mData = new ArrayList<>();
        requestData();
    }

    private void requestData() {
        NaidouApi.getMyFollow(new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("getMyFollow:"+t);
                if(Response.getSuccess(t)){
                    dialog.dismiss();
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
