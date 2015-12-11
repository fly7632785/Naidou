package com.itspeed.naidou.app.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;
import com.itspeed.naidou.app.activity.SimpleBackActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.adapter.MyCollectAdapter;
import com.itspeed.naidou.app.util.UIHelper;
import com.itspeed.naidou.model.bean.CookBook;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.KJLoger;

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
    @BindView(id = R.id.mycollect_text)
    private TextView mEmpty;
    private MyCollectAdapter mAdapter;
    private ArrayList<CookBook> mData;
    private ProgressDialog dialog;


    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        aty = (SimpleBackActivity) getActivity();
        layout = View.inflate(aty, R.layout.frag_mycollect,null);
        onChange();
        return layout;
    }


    @Override
    protected void initData() {
        super.initData();

        dialog = new ProgressDialog(aty);
        dialog.setMessage("加载中...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        mAdapter = new MyCollectAdapter();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UIHelper.showChideDetail(aty,mData.get(position).getCid());
            }
        });
        mData = new ArrayList<>();
        //模拟添加数据
//        for (int i = 0;i<10;i++){
//            CookBook cookBook = new CookBook();
//            mData.add(cookBook);
//        }
        requestData();


    }

    private void requestData() {
        NaidouApi.getMyCollect(1, 10, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                if(Response.getSuccess(t)) {
                    dialog.dismiss();

                    KJLoger.debug("getMyCollect:"+t);
                    mData = Response.getMyCollectList(t);
                    if(mData.isEmpty() || mData == null){
                        mEmpty.setVisibility(View.VISIBLE);
                    }else {
                        mAdapter.setData(mData);
                        mGridView.setAdapter(mAdapter);
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
        mData = null;
        super.onDestroy();
    }
}
