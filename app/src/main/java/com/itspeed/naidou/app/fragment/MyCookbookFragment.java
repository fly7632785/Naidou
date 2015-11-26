package com.itspeed.naidou.app.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.itspeed.naidou.app.adapter.MyCookBookAdapter;
import com.itspeed.naidou.model.bean.CookBook;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;

/**
 * Created by jafir on 15/9/28.
 * 我的菜谱fragment
 */
public class MyCookbookFragment extends TitleBarSupportFragment{

    private SimpleBackActivity aty;
    private View layout;
    @BindView(id = R.id.mycookbook_list)
    private ListView mListView;
    private MyCookBookAdapter mAdapter;
    private ArrayList<CookBook> mData;
    @BindView(id = R.id.mycookbook_text)
    private TextView mEmpty;

    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        aty = (SimpleBackActivity) getActivity();
        layout = View.inflate(aty, R.layout.frag_mycookbook,null);
        onChange();
        return layout;
    }


    @Override
    protected void initData() {
        super.initData();
        mAdapter = new MyCookBookAdapter();
        mData = new ArrayList<>();
        //模拟添加数据
//        for (int i = 0;i<10;i++){
//            CookBook cookBook = new CookBook();
//            mData.add(cookBook);
//        }
        requestData();
        mListView.setDivider(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onChange() {
        super.onChange();
        setTitleType(TitleBarActivity.TitleBarType.Titlebar2);
        setBackImage(R.drawable.selector_title_back);
        setTitle("我的菜谱");
        setMenuImage(null);
    }

    private void requestData() {
        NaidouApi.getMyCookbook(1, 10, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                if (Response.getSuccess(t)) {
                    KJLoger.debug("getMyCookbook:" + t);
                    mData = Response.getMyCookbookList(t);
                    if (mData.isEmpty() || mData == null) {
                        mEmpty.setVisibility(View.VISIBLE);
                    } else {
                        mAdapter.setData(mData);
                        mListView.setAdapter(mAdapter);
                    }
                }

            }
        });

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
