package com.itspeed.naidou.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.domain.SimpleBackPage;
import com.itspeed.naidou.app.fragment.TitleBarSupportFragment;


/**
 * Created by jafir on 15/9/28.
 */
public class SimpleBackActivity extends TitleBarActivity {
    public static String TAG = SimpleBackActivity.class.getSimpleName();
    public static String CONTENT_KEY = "sba_content_key";
    public static String DATA_KEY = "sba_datat_key";

    private TitleBarSupportFragment currentFragment;

    @Override
    public void setRootView() {
        setContentView(R.layout.aty_simple_back);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int value = getIntent().getIntExtra(CONTENT_KEY, -1);
        if (value != -1) {
            try {
                currentFragment = (TitleBarSupportFragment) SimpleBackPage
                        .getPageByValue(value).newInstance();
                changeFragment(currentFragment);
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            }
        }
    }

    public Bundle getBundleData() {
        return getIntent().getBundleExtra(DATA_KEY);
    }

    public void changeFragment(TitleBarSupportFragment targetFragment) {
        changeFragment(R.id.simple_main_content, targetFragment);
    }



    @Override
    protected void onBackClick() {
        super.onBackClick();
        if (currentFragment != null) {
            currentFragment.onBackClick();
        }
    }

    @Override
    protected void onMenuClick() {
        super.onMenuClick();
        if (currentFragment != null) {
            currentFragment.onMenuClick();
        }
    }

    /**
     * 跳转到SimpleBackActivity时，只能使用该方法跳转
     *
     * @param cxt
     * @param page
     * @param data
     */
    public static void postShowWith(Context cxt, SimpleBackPage page,
                                    Bundle data) {
        Intent intent = new Intent(cxt, SimpleBackActivity.class);
        intent.putExtra(CONTENT_KEY, page.getValue());
        intent.putExtra(DATA_KEY, data);
        cxt.startActivity(intent);
    }

    /**
     * 跳转到SimpleBackActivity时，只能使用该方法跳转
     *
     * @param cxt
     * @param page
     */
    public static void postShowWith(Context cxt, SimpleBackPage page) {
        postShowWith(cxt, page, null);
    }

    /**
     * 跳转到SimpleBackActivity时，只能使用该方法跳转
     *
     *            从哪个Activity跳转
     * @param code
     *            启动码
     * @param page
     *            要显示的Fragment
     * @param data
     *            传递的Bundle数据
     */
    public static void postShowForResult(Fragment fragment, int code,
                                         SimpleBackPage page, Bundle data) {
        Intent intent = new Intent(fragment.getActivity(),
                SimpleBackActivity.class);
        intent.putExtra(CONTENT_KEY, page.getValue());
        intent.putExtra(DATA_KEY, data);
        fragment.startActivityForResult(intent, code);
    }

    /**
     * 跳转到SimpleBackActivity时，只能使用该方法跳转
     *
     *            从哪个Activity跳转
     * @param code
     *            启动码
     * @param page
     *            要显示的Fragment
     */
    public static void postShowForResult(Fragment fragment, int code,
                                         SimpleBackPage page) {
        postShowForResult(fragment, code, page, null);
    }
}