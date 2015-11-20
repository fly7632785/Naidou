package com.itspeed.naidou.app.fragment.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.activity.SimpleBackActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.fragment.TitleBarSupportFragment;

/**
 * Created by jafir on 10/19/15.
 * 吃的的详情fragment
 * 里面是用一个webview来展示的网页
 */
public class ChideDetailFragment extends TitleBarSupportFragment {

    private SimpleBackActivity aty;
    private View layout;
    private WebView mWebview;


    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layout = View.inflate(aty, R.layout.frag_chide_detail,null);
        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aty = (SimpleBackActivity) getActivity();
        onChange();
    }


    @Override
    protected void initData() {
        super.initData();
        mWebview = (WebView) layout.findViewById(R.id.chide_detail_webview);
        WebSettings wSet = mWebview.getSettings();
        mWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        wSet.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //设置自适应
        wSet.setJavaScriptEnabled(true);
        mWebview.loadUrl("http://www.baidu.com");

    }

    @Override
    public void onChange() {
        super.onChange();
        setTitleType(TitleBarActivity.TitleBarType.Titlebar3);
        setBackImage(R.drawable.selector_title_back);
        setTitle("");
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
        mWebview = null;
        super.onDestroy();
    }
}
