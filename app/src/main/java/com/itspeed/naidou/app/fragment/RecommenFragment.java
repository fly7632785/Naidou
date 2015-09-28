package com.itspeed.naidou.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.itspeed.naidou.R;
import com.itspeed.naidou.app.activity.MainActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.adapter.RecommendAdapter;
import com.itspeed.naidou.model.bean.CookBook;
import com.itspeed.naidou.model.bean.Topic;

import java.util.ArrayList;

/**
 * Created by jafir on 15/9/1.
 */
public class RecommenFragment extends TitleBarSupportFragment {

    MainActivity aty;
    private ListView listView;
    private RecommendAdapter mAdapter;

    private ArrayList<Object> topics = new ArrayList<>();
    private ArrayList<Object> cookBooks = new ArrayList<>();

    private PullToRefreshListView mPullToRefresh;

    private ArrayList<Object> as = new ArrayList<>();
    private ArrayList<Object> bs = new ArrayList<>();

    @Override
    public void onChange() {
        super.onChange();
        setTitleType(TitleBarActivity.TitleBarType.Titlebar2);
        setTitle("推荐");
        setBackImage(null);
        setMenuImage(null);
    }



    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        aty = (MainActivity) getActivity();
        onChange();
        View view = View.inflate(aty, R.layout.frag_recommend, null);
        mPullToRefresh = (PullToRefreshListView) view.findViewById(R.id.pull_to_refresh);
        listView = mPullToRefresh.getRefreshableView();
//        setDate();
//        mAdapter = new RecommendAdapter(aty,cookBooks,topics);
//        listView  = (ListView) view.findViewById(R.id.list_recommend);
//        setData();
//        abAdapter = new ABAdapter(aty, as, bs);
        listView.setDividerHeight(0);
        listView.setAdapter(new MyAdapter());


        return view;
    }




    private void setDate() {
        //这里的time是从100开始+2*i
        for (int i = 0; i < 10; i++) {
            CookBook cookBook = new CookBook();
            cookBook.setPortraitUrl("wwww");
            cookBook.setTime("" + (100 + 2 * i));
            cookBooks.add(cookBook);
        }
        //这里是从95 开始+3*i，
        //目的是为了 让他们的时间有交叉，然后才能更好的体现排序
        for (int i = 0; i < 10; i++) {
            Topic topic = new Topic();
            topic.setTitle("topic");
            topic.setTime("" + (95 + 3 * i));
            topics.add(topic);
        }
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
