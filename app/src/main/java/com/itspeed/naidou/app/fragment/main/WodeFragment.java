package com.itspeed.naidou.app.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;
import com.itspeed.naidou.app.AppContext;
import com.itspeed.naidou.app.activity.MainActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.fragment.TitleBarSupportFragment;
import com.itspeed.naidou.app.util.UIHelper;
import com.itspeed.naidou.model.bean.User;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.bitmap.BitmapConfig;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.KJLoger;

/**
 * Created by jafir on 15/9/1.
 * 我的 fragment
 */
public class WodeFragment extends TitleBarSupportFragment {

    private static final int REQUEST_PICK_PHOTO = 111;
    MainActivity aty;
    private View view;


    @BindView(id = R.id.wode_cookbook_layout,click = true)
    private LinearLayout ly_mycookbook;
//    @BindView(id = R.id.wode_message_layout,click = true)
//    private LinearLayout ly_myMessage;
    @BindView(id = R.id.wode_collect_layout,click = true)
    private LinearLayout ly_myCollect;
    @BindView(id = R.id.wode_follow_layout,click = true)
    private LinearLayout ly_follow;

    @BindView(id = R.id.wode_portrait,click = true)
    private ImageView mAvatar;
    @BindView(id = R.id.wode_count_of_naidou)
    private TextView mCoins;
    @BindView(id = R.id.wode_count_of_follow)
    private TextView mFollow;
    @BindView(id = R.id.wode_nickname)
    private TextView mNickname;
    @BindView(id = R.id.wode_motto)
    private TextView mMotto;

    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        aty = (MainActivity) getActivity();
        view = View.inflate(aty, R.layout.frag_wode,null);
        onChange();
        return view;
    }

    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);


        switch (v.getId()){
            case R.id.wode_cookbook_layout:
                UIHelper.showMyCookbook(aty);

                break;
            case R.id.wode_collect_layout:

                UIHelper.showMyCollect(aty);
                break;
//            case R.id.wode_message_layout:
//                break;

            case R.id.wode_follow_layout:
                UIHelper.showFollow(aty);
                break;

            case R.id.wode_portrait:
                UIHelper.showEditInfo(aty);
                break;
        }

    }

    @Override
    public void onChange() {
        super.onChange();
        setTitleType(TitleBarActivity.TitleBarType.Titlebar2);
        setTitle("我的");
        setBackImage(null);
        setMenuImage(R.drawable.selector_title_setting);
    }

    @Override
    protected void initData() {
        super.initData();

        //从全局user里面获取 显示信息
        setData(AppContext.user);

    }

    @Override
    public void onStart() {
        super.onStart();
        requestData();
    }

    public void requestData(){
        NaidouApi.getMyInfo(new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("getmyInfo:" + t);
                if (Response.getSuccess(t)) {
                    User user = Response.getUserInfo(t);
                    setData(user);
                }
            }
        });
    }


    /**
     * 设置数据显示数据
     * @param user
     */
    private void setData(User user) {
        if(user != null) {
            //设置全局user信息
            AppContext.user = user;
            //显示数据
            //头像     本地的avatar.jpeg
            //设置 头像缓存时间30天
            HttpConfig httpConfig = new HttpConfig();
            httpConfig.cacheTime = 30*3600;
            new KJBitmap.Builder().imageUrl(AppContext.HOST + user.getAvatar()).view(mAvatar).errorBitmapRes(R.mipmap.portrait).display(new KJBitmap(httpConfig,new BitmapConfig()));
//            KJLoger.debug("cachepath:" + HttpConfig.CACHEPATH + "");
//            KJLoger.debug("cachetime:" + httpConfig.cacheTime);
            mCoins.setText(user.getCoins() + "");
            mFollow.setText(user.getFollowCount()+"");
            mNickname.setText(user.getNickname());
            mMotto.setText(user.getMotto());
        }
    }


    @Override
    public void onMenuClick() {
        super.onMenuClick();
        UIHelper.showSetting(aty);
    }
}
