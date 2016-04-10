package com.itspeed.naidou.app.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.PreferenceHelper;
import org.kymjs.kjframe.utils.SystemTool;

import java.util.ArrayList;

/**
 * Created by jafir on 15/9/28.
 * 我的菜谱fragment
 */
public class MyCookbookFragment extends TitleBarSupportFragment {

    private static final String TAG = MyCookbookFragment.class.getSimpleName();
    private SimpleBackActivity aty;
    private View layout;
    @BindView(id = R.id.mycookbook_list)
    private ListView mListView;
    private MyCookBookAdapter mAdapter;
    private ArrayList<CookBook> mData;
    @BindView(id = R.id.mycookbook_text, click = true)
    private TextView mEmpty;
    private ProgressDialog dialog;
    private BroadcastReceiver mReceiver;
    @BindView(id = R.id.mycookbook_hint, click = true)
    private ImageView mHint;

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
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(aty);
                builder.setTitle("老大，你确定要删除你的菜谱么？");

                builder.setPositiveButton("必须的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NaidouApi.doDeleteCookbook(mData.get(position).getCid(), new HttpCallBack() {
                            @Override
                            public void onSuccess(String t) {
                                super.onSuccess(t);
                                if (Response.getSuccess(t)) {
                                    KJLoger.debug("delete:" + t);
                                    ViewInject.toast("删除成功");
                                    //重新获取数据
//                                    requestData();
                                    mAdapter.removeItem(mData.get(position));
                                }

                            }
                        });
                    }
                });
                builder.setNegativeButton("我才不呢", null);
                builder.create().show();
                return true;
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
    protected void widgetClick(View v) {
        super.widgetClick(v);
        if (v.getId() == R.id.mycookbook_text) {
            KJLoger.debug("cccccccccc");
            UIHelper.showPublish(aty);
            aty.finish();
        } else if (v.getId() == R.id.mycookbook_hint) {
            mHint.setVisibility(View.GONE);
        }

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
        mAdapter.notifyDataSetChanged();
        if (!SystemTool.checkNet(aty)) {
//            String data = getFromLocal("local", "localMycookbook.txt");
            String data = NaidouApi.getMyCookbookCache(1, 10);
            if (data != null && !data.equals("")) {
                setData(data);
            }

        } else {
            NaidouApi.getMyCookbook(1, 10, new HttpCallBack() {
                @Override
                public void onSuccess(String t) {
                    super.onSuccess(t);
                    setData(t);
//                    writeToLocal(t, "local", "localMycookbook.txt");

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
                //如果有数据

                /**
                 * fragment 这个不可用
                 */
//        aty.addGuideImage(R.mipmap.hint_mycookbook,TAG,R.id.mycookbook_layout);
                boolean isFirst = PreferenceHelper.readBoolean(aty, TAG, "first_open", true);
                if (isFirst) {
                    mHint.setVisibility(View.VISIBLE);
                    PreferenceHelper.write(aty, TAG, "first_open", false);
                }


                mAdapter.setData(mData);
                mListView.setAdapter(mAdapter);

            }
            mAdapter.notifyDataSetChanged();
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
