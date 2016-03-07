package com.itspeed.naidou.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;
import com.itspeed.naidou.app.AppContext;
import com.itspeed.naidou.app.adapter.MyCookBookAdapter;
import com.itspeed.naidou.app.util.RightsManager;
import com.itspeed.naidou.app.helper.UIHelper;
import com.itspeed.naidou.model.bean.CookBook;
import com.itspeed.naidou.model.bean.User;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.SystemTool;

import java.util.ArrayList;

/**
 * Created by jafir on 15/9/27.
 * <p/>
 * 个人空间
 */
public class ZoneActivity extends BaseActivity {


    @BindView(id = R.id.zone_list)
    private ListView mListView;
    @BindView(id = R.id.load_layout)
    private RelativeLayout mLoadLayout;
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
    private User user;


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
        mBack = (ImageView) head.findViewById(R.id.zone_list_head_back);
        mUserAvatar = (ImageView) head.findViewById(R.id.zone_list_head_avatar);
        mFollow = (ImageView) head.findViewById(R.id.zone_list_head_follow);
        mUsername = (TextView) head.findViewById(R.id.zone_list_head_name);
        mCookbookCount = (TextView) head.findViewById(R.id.zone_list_head_cookbook_count);
        mUserMotto = (TextView) head.findViewById(R.id.zone_list_head_motto);

        mBack.setOnClickListener(this);
        mFollow.setOnClickListener(this);

        if(uid.equals(AppContext.user.getUid())){
            //如果是自己 则隐藏 关注按钮
            mFollow.setVisibility(View.INVISIBLE);
        }
        //请求userInfo
        //设置数据

        if(!SystemTool.checkNet(aty)){

            String data = getFromLocal("localUsers", "localUsers"+uid+".txt");
            if (data != null && !data.equals("")) {
                setData(data);
            }
        }else {

            NaidouApi.getUserInfo(uid, new HttpCallBack() {
                @Override
                public void onSuccess(String t) {
                    super.onSuccess(t);
                    KJLoger.debug("getUserInfo:" + t);
                    setData(t);
                    writeToLocal(t,"localUsers", "localUsers"+uid+".txt");
                }
            });
        }
    }

    private void setData(String t) {
        if (Response.getSuccess(t)) {
            mLoadLayout.setVisibility(View.GONE);
            user = Response.getUserInfo(t);
            mData = Response.getUserCookbook(t);
            setHeaderData(user);
            setUserInfo(user);
        }
    }

    private void setUserInfo(User user) {
        KJLoger.debug(user.toString());
//        mCookbookCount.setText("他有" + user.getCookBookCount() + "个菜谱呢");
        if (user.isFollowedByMe()) {
            mFollow.setImageResource(R.mipmap.personal_home_followed);
        } else {
            mFollow.setImageResource(R.mipmap.personal_home_follow);
        }
        mAdapter.setData(mData);
        mListView.setAdapter(mAdapter);
    }


    /**
     * 设置 list header 的数据
     * 用于展示 封面图 user name 菜谱描述 点赞 等等
     * @param user
     */
    private void setHeaderData(User user) {
        new KJBitmap.Builder().imageUrl(AppContext.HOST + user.getAvatar()).errorBitmapRes(R.mipmap.portrait).view(mUserAvatar).display();
        mUsername.setText(user.getNickname());
        mFollow.setSelected(user.isFollowedByMe());
        mUserMotto.setText(user.getMotto());
        mCookbookCount.setText("他有" + user.getCookBookCount() + "个菜谱呢");

    }


    @Override
    public void initData() {
        super.initData();
        uid = getIntent().getStringExtra("uid");
        //模拟uid
        if (uid == null) {
            uid = "1";
        }
        mAdapter = new MyCookBookAdapter();
        initHeader();
        mListView = (ListView) findViewById(R.id.zone_list);
        mListView.setDividerHeight(0);
        mListView.addHeaderView(head);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    UIHelper.showChideDetail(aty, mData.get(position - 1).getCid());
                }
            }
        });
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
        switch (v.getId()) {
            case R.id.zone_list_head_back:
                this.finish();
                break;
            case R.id.zone_list_head_follow:
                if (RightsManager.isVisitor(v.getContext())) {
                    return;
                }
                if(uid.equals(AppContext.user.getUid())){
                    ViewInject.toast("不能关注自己哦");
                    return;
                }
                boolean isFollowed = user.isFollowedByMe();
                if (isFollowed) {
                    cancelFollow();
                    user.setIsFollowedByMe(false);
                    mFollow.setImageResource(R.mipmap.personal_home_follow);
                } else {
                    doFollow();
                    mFollow.setImageResource(R.mipmap.personal_home_followed);
                    user.setIsFollowedByMe(true);
                }

                break;

        }
    }


    private void doFollow() {
        NaidouApi.doFollow(uid, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                if (Response.getSuccess(t)) {
                    ViewInject.toast("关注成功");
                }
            }
        });
    }

    private void cancelFollow() {
        NaidouApi.cancelFollow(uid, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                if (Response.getSuccess(t)) {
                    ViewInject.toast("取消关注");
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
        System.gc();
        super.onDestroy();
    }


}

