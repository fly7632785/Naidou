package com.itspeed.naidou.app.fragment.detail;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.activity.SimpleBackActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.adapter.LiaodeDetailAdapter;
import com.itspeed.naidou.app.fragment.TitleBarSupportFragment;

import java.util.ArrayList;

/**
 * Created by jafir on 10/19/15.
 * 聊的详情fragment
 */
public class LiaodeDetailFragment extends TitleBarSupportFragment {

    private SimpleBackActivity aty;
    private View layout;
    private ListView mListView;
    private LiaodeDetailAdapter mAdapter;


    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layout = View.inflate(aty, R.layout.frag_liaode_detail,null);
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

        mListView = (ListView) layout.findViewById(R.id.liaode_detail_list);
        View header = View.inflate(aty,R.layout.liaode_detail_head,null);
        mListView.addHeaderView(header);
        mListView.setDivider(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        mAdapter = new LiaodeDetailAdapter();
        ArrayList arrayList = new ArrayList();
        for(int i =0;i<5;i++){
            arrayList.add(1);
        }
        //模拟数据5个
        mAdapter.setData(arrayList);
        mListView.setAdapter(mAdapter);


    }

    @Override
    public void onChange() {
        super.onChange();
        setTitleType(TitleBarActivity.TitleBarType.Titlebar3);
        setBackImage(R.drawable.selector_title_back);
        setTitle("");
        setMenuImage(null);
    }


    @Override
    public void onBackClick() {
        super.onBackClick();
        aty.finish();
    }
}
