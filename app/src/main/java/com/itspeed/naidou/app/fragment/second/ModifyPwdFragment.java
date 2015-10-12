package com.itspeed.naidou.app.fragment.second;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.activity.SimpleBackActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.fragment.TitleBarSupportFragment;

/**
 * Created by jafir on 15/9/28.
 * 设置里面 修改密码  fragment
 */
public class ModifyPwdFragment extends TitleBarSupportFragment{

    private SimpleBackActivity aty;
    private View layout;


    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layout = View.inflate(aty, R.layout.frag_modifypwd,null);
        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aty = (SimpleBackActivity) getActivity();
        onChange();
    }

    @Override
    public void onChange() {
        super.onChange();
        setTitleType(TitleBarActivity.TitleBarType.Titlebar2);
        setBackImage(R.drawable.selector_title_back);
        setTitle("修改密码");
        setMenuImage(null);
    }


    @Override
    public void onBackClick() {
        super.onBackClick();
        aty.finish();
    }
}
