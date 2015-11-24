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
import com.itspeed.naidou.app.activity.MainActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.fragment.TitleBarSupportFragment;
import com.itspeed.naidou.app.util.UIHelper;
import com.itspeed.naidou.model.bean.User;
import com.squareup.picasso.Picasso;

import org.kymjs.kjframe.http.HttpCallBack;
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

        NaidouApi.getMyFollow(new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("getMyFollow:" + t);
            }
        });

        NaidouApi.getUserInfo("1", new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("getUserInfo:" + t);
            }
        });

    }

    private void setData(User user) {
        KJLoger.debug(""+user.getAvatar());
        Picasso.with(aty).load("http://139.129.29.84/"+user.getAvatar()).into(mAvatar);
        mCoins.setText(user.getCoins()+"");
        mFollow.setText("12");
        mNickname.setText(user.getNickname());
        mMotto.setText(user.getMotto());
    }


    @Override
    public void onMenuClick() {
        super.onMenuClick();
        UIHelper.showSetting(aty);
    }
}
