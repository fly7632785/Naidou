package com.itspeed.naidou.app.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.activity.MainActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.fragment.TitleBarSupportFragment;
import com.itspeed.naidou.app.util.UIHelper;

import org.kymjs.kjframe.ui.BindView;

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



    }


    @Override
    public void onMenuClick() {
        super.onMenuClick();
        UIHelper.showSetting(aty);
    }
}
