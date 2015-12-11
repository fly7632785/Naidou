package com.itspeed.naidou.app.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.activity.MainActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.fragment.ChildrenFragment;
import com.itspeed.naidou.app.fragment.ParentFragment;
import com.itspeed.naidou.app.fragment.TitleBarSupportFragment;
import com.itspeed.naidou.app.util.RightsManager;
import com.itspeed.naidou.app.util.UIHelper;

/**
 * Created by jafir on 15/9/1.
 * 吃的fragment
 * 包括 分类  父母（）孩子（）
 *
 */
public class ChideFragment extends TitleBarSupportFragment {


    private static final String TAG = ChideFragment.class.getSimpleName();
    private MainActivity aty;
    //父母的fragment
    private ParentFragment parentFragment;
    //孩子的fragment
    private ChildrenFragment childrenFragment;





    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View  view = View.inflate(aty,R.layout.frag_chide,null);
        return  view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //第一次手动调用
        onChange();
        aty = (MainActivity) getActivity();

        parentFragment = new ParentFragment();
        childrenFragment = new ChildrenFragment();
        changeFragment(R.id.cookbood_fl, parentFragment);
    }


    @Override
    protected void initData() {
        super.initData();



    }

    @Override
    public void onChange() {
        super.onChange();
        setTitleType(TitleBarActivity.TitleBarType.Titlebar1);
//        setBackImage(R.drawable.selector_titlebar_search);
        setBackImage(null);
        setMenuImage(R.drawable.selector_titlebar_add);
    }


    @Override
    public void onMenuClick() {
        super.onMenuClick();

        if(RightsManager.isVisitor(aty)) {
            return;
        }
        UIHelper.showPublish(aty);
    }




    @Override
    public void onBackClick() {
        super.onBackClick();
//        aty.sendBroadcast(new Intent("com.itspeed.naidou.FORCE_OFFLINE"));
    }

    @Override
    public void onSegmentClick(int index) {
        super.onSegmentClick(index);
        switch (index){
            case 0:
                changeFragment(R.id.cookbood_fl,parentFragment);
                break;
            case 1:
                changeFragment(R.id.cookbood_fl,childrenFragment);
                break;
        }

    }



}
