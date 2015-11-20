package com.itspeed.naidou.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.activity.SimpleBackActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.adapter.MyCollectAdapter;
import com.itspeed.naidou.model.bean.CookBook;

import org.kymjs.kjframe.ui.BindView;

import java.util.ArrayList;

/**
 * Created by jafir on 15/9/28.
 * 我的收藏fragment
 */
public class MyCollectFragment extends TitleBarSupportFragment{

    private SimpleBackActivity aty;
    private View layout;
    @BindView(id = R.id.mycollect_grid)
    private GridView mGridView;
    private MyCollectAdapter mAdapter;
    private ArrayList<CookBook> data;


    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {

        layout = View.inflate(aty, R.layout.frag_mycollect,null);
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
        mAdapter = new MyCollectAdapter();
        data = new ArrayList<>();
        //模拟添加数据
        for (int i = 0;i<10;i++){
            CookBook cookBook = new CookBook();
            data.add(cookBook);
        }
        mAdapter.setData(data);
        mGridView.setAdapter(mAdapter);
    }

    @Override
    public void onChange() {
        super.onChange();
        setTitleType(TitleBarActivity.TitleBarType.Titlebar2);
        setBackImage(R.drawable.selector_title_back);
        setTitle("我的收藏");
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
        mAdapter = null;
        mGridView = null;
        data = null;
        super.onDestroy();
    }
}
