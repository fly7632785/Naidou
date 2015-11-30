package com.itspeed.naidou.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;
import com.itspeed.naidou.app.adapter.MyCookBookAdapter;
import com.itspeed.naidou.app.util.RightsManager;
import com.itspeed.naidou.model.bean.CookBook;
import com.itspeed.naidou.model.bean.User;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;

/**
 * Created by jafir on 15/9/27.
 * <p/>
 * 个人空间
 */
public class ZoneActivity extends KJActivity {


    @BindView(id = R.id.zone_list)
    private ListView mListView;
    private MyCookBookAdapter mAdapter;
    private ArrayList<CookBook> mData;
    private String uid;
    private View head;

    private ImageView mUserAvatar;
    private ImageView mBack;
    private ImageView mFollow;
    private TextView mUsername;
    private TextView mUserMotto;
    private TextView mCookbookCount;


    @Override
    public void setRootView() {
        setContentView(R.layout.aty_zone);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    private void initHeader() {
        head = View.inflate(this, R.layout.zone_list_head, null);
        mBack  = (ImageView) head.findViewById(R.id.zone_list_head_back);
        mUserAvatar  = (ImageView) head.findViewById(R.id.zone_list_head_avatar);
        mFollow  = (ImageView) head.findViewById(R.id.zone_list_head_follow);
        mUsername = (TextView) head.findViewById(R.id.zone_list_head_name);
        mCookbookCount = (TextView) head.findViewById(R.id.zone_list_head_cookbook_count);
        mUserMotto = (TextView) head.findViewById(R.id.zone_list_head_motto);

        mBack.setOnClickListener(this);
        mFollow.setOnClickListener(this);
        //请求userInfo
        //设置数据
        NaidouApi.getUserInfo(uid, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("getUserInfo:" + t);
                if (Response.getSuccess(t)) {
                    User user = Response.getUserInfo(t);
                    setHeaderData(user);
                }
            }
        });
    }

    private void setHeaderData(User user) {
        new KJBitmap.Builder().imageUrl(user.getAvatar()).errorBitmapRes(R.mipmap.portrait).view(mUserAvatar).display();
        mUsername.setText(user.getNickname());
        mUserMotto.setText(user.getMotto());
        mCookbookCount.setText("他的菜谱（" + user.getCookBookCount() + ")");

    }


    @Override
    public void initData() {
        super.initData();
        uid = getIntent().getStringExtra("uid");
        //模拟uid
        if(uid ==null){
            uid ="1";
        }
        initHeader();
        mListView = (ListView) findViewById(R.id.zone_list);
        mListView.addHeaderView(head);

        mData = new ArrayList<>();
        mAdapter = new MyCookBookAdapter();
        mAdapter.setData(mData);
        mListView.setAdapter(mAdapter);
//        requestData();

    }

    private void requestData() {
        NaidouApi.getCookbook(uid, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("getCookbook:" + t);
                if (Response.getSuccess(t)) {
                    mData = Response.getChideList(t);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()){
            case R.id.zone_list_head_back:
                this.finish();
                break;
            case R.id.zone_list_head_follow:
                if(RightsManager.isVisitor(v.getContext())) {
                    return;
                }
                doFollow();
                break;

        }
    }

    private void doFollow() {
        NaidouApi.doFollow(uid, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("doFollow:" + t);
                if (Response.getSuccess(t)) {
                    ViewInject.toast("关注成功");
                    mFollow.setImageResource(R.mipmap.personal_home_followed);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        setContentView(R.layout.view_null);
         mListView = null;
         mAdapter = null;
         mData = null;
         uid = null;
         head = null;
         mUserAvatar = null;
         mBack = null;
         mFollow = null;
         mUsername = null;
         mUserMotto = null;
         mCookbookCount = null;
        super.onDestroy();
    }


}

