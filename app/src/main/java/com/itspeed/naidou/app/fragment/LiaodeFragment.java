package com.itspeed.naidou.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.activity.MainActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.view.PullToRefreshList;

import org.kymjs.kjframe.utils.KJLoger;

/**
 * Created by jafir on 15/9/1.
 * 聊的fragment
 *
 */
public class LiaodeFragment extends TitleBarSupportFragment {


    private MainActivity aty;
    private View view;
    private PullToRefreshList mPullToRefresh;
    private ListView listView;

    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        view = View.inflate(aty, R.layout.frag_liaode, null);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aty = (MainActivity) getActivity();
        onChange();
    }

    @Override
    public void onChange() {
        super.onChange();
        setTitleType(TitleBarActivity.TitleBarType.Titlebar2);
        setTitle("聊的");
        setBackImage(null);
        setMenuImage(null);

    }

    @Override
    protected void initData() {
        super.initData();
        mPullToRefresh = (PullToRefreshList) view.findViewById(R.id.pull_to_refresh);
        mPullToRefresh.setPullLoadEnabled(true);
        listView = mPullToRefresh.getRefreshView();
        listView.setDividerHeight(0);
        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        listView.setAdapter(new MyAdapter());
    }

    @Override
    public void onStart() {
        super.onStart();
        KJLoger.debug("LiaodeFragment:——————————onStart");
    }



    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return ss.length*4;
        }

        @Override
        public Object getItem(int position) {
            return ss[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View  view = View.inflate(aty,R.layout.test,null);
            TextView t = (TextView) view.findViewById(R.id.textView);
            t.setText(ss[position%ss.length]);
            t.setBackgroundResource(cc[position % 4]);
            return view;
        }
    }


    private String[] ss = new String[]{"Community Guidelines","Android Developers","Marlon “Virus” Jones","Marlon “Virus” Jones ","Hello,everybody.I am an Android Developer."};
    private int[] cc = new int[]{R.color.c1,R.color.c3,R.color.c2,R.color.c4};



}
