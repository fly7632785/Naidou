package com.itspeed.naidou.app.fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.itspeed.naidou.app.AppConfig;
import com.itspeed.naidou.app.activity.SimpleBackActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.adapter.MyCookBookAdapter;
import com.itspeed.naidou.app.helper.UIHelper;
import com.itspeed.naidou.app.listener.LikeAndCollecListener;
import com.itspeed.naidou.model.bean.CookBook;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.SystemTool;

import java.util.ArrayList;

/**
 * Created by jafir on 15/9/28.
 * 我的菜谱fragment
 */
public class MyCookbookFragment extends TitleBarSupportFragment {

    private SimpleBackActivity aty;
    private View layout;
    @BindView(id = R.id.mycookbook_list)
    private ListView mListView;
    private MyCookBookAdapter mAdapter;
    private ArrayList<CookBook> mData;
    @BindView(id = R.id.mycookbook_text)
    private TextView mEmpty;
    private ProgressDialog dialog;
    private BroadcastReceiver mReceiver;

    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        aty = (SimpleBackActivity) getActivity();
        layout = View.inflate(aty, R.layout.frag_mycookbook, null);
        onChange();

        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * 动态注册广播
     */
    private void registerMyReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(AppConfig.RECEIVER_CHANGE_COLLECT_DETAIL);
        filter.addAction(AppConfig.RECEIVER_CHANGE_LIKE_DETAIL);
        aty.registerReceiver(mReceiver, filter);
    }

    @Override
    protected void initData() {
        super.initData();


        KJLoger.debug("344444");
        dialog = new ProgressDialog(aty);
        dialog.setMessage("加载中...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        mAdapter = new MyCookBookAdapter();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UIHelper.showChideDetail(aty, mData.get(position).getCid());
            }
        });
        mData = new ArrayList<>();
        requestData();
        mListView.setDivider(new ColorDrawable(Color.TRANSPARENT));

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String cid = intent.getStringExtra("cid");
                switch (intent.getAction()) {
                    case AppConfig.RECEIVER_CHANGE_LIKE_DETAIL:
                        LikeAndCollecListener.changeLikeLocalState(mData, cid, mAdapter);
                        KJLoger.debug("mybookchangelike");
                        break;
                    case AppConfig.RECEIVER_CHANGE_COLLECT_DETAIL:
                        KJLoger.debug("mybookchangecollect");
                        LikeAndCollecListener.changeCollectLocalState(mData, cid, mAdapter);
                        break;
                }
            }
        };

        registerMyReceiver();

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
        if (!SystemTool.checkNet(aty)) {
            String data = getFromLocal("local", "localMycookbook.txt");
            if (data != null && !data.equals("")) {
                setData(data);
            }

        } else {
            NaidouApi.getMyCookbook(1, 10, new HttpCallBack() {
                @Override
                public void onSuccess(String t) {
                    super.onSuccess(t);
                    setData(t);
                    writeToLocal(t, "local", "localMycookbook.txt");

                }
            });
        }
    }

    private void setData(String t) {
        if (Response.getSuccess(t)) {
            dialog.dismiss();
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

    @Override
    public void onBackClick() {
        super.onBackClick();
        aty.finish();
    }

    @Override
    public void onDestroy() {
        aty.unregisterReceiver(mReceiver);
        aty = null;
        layout = null;
        mData = null;
        mListView = null;
        mAdapter = null;
        super.onDestroy();
    }
}
