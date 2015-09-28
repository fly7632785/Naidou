package com.itspeed.naidou.app.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.activity.MainActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.adapter.ChideAdapter;
import com.squareup.picasso.Picasso;

import org.kymjs.kjframe.utils.DensityUtils;
import org.kymjs.kjframe.utils.KJLoger;

import java.util.Random;

/**
 * Created by jafir on 15/9/1.
 * 聊的fragment
 *
 */
public class LiaodeFragment extends TitleBarSupportFragment {


    private MainActivity aty;
    private View view;
    private RecyclerView mRecycleView;
    private ListView listView;
    private StaggeredGridLayoutManager layoutManager1;

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
        listView = (ListView) view.findViewById(R.id.listview);
        listView.setDividerHeight(DensityUtils.dip2px(aty,30));
        listView.setDivider(new ColorDrawable(android.R.color.transparent));
        listView.setSelector(new ColorDrawable(android.R.color.transparent));
    }

    @Override
    public void onStart() {
        super.onStart();
        KJLoger.debug("LiaodeFragment:——————————onStart");
    }


    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

        @Override
        public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            View view = View.inflate(viewGroup.getContext(), R.layout.item_recycle_img, null);
            MyHolder holder = new MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder myHolder, int i) {
            Picasso.with(aty).load(ChideAdapter.img[i % ChideAdapter.img.length]).resize(DensityUtils.getScreenW(aty) / 3, DensityUtils.getScreenH(aty) / 2).centerInside().into(myHolder.imageView);
        }


        @Override
        public int getItemCount() {
            return ChideAdapter.img.length * 3;
        }

        class MyHolder extends RecyclerView.ViewHolder {

            private ImageView imageView;

            public MyHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.imageView);
            }
        }


    }

    private String[] ss = new String[]{"Community Guidelines","Android Developers","Marlon “Virus” Jones","Marlon “Virus” Jones ","Hello,everybody.I am an Android Developer."};
    private int[] cc = new int[]{R.color.c1,R.color.c3,R.color.c2,R.color.c4};



    private class MyAdapter11 extends BaseAdapter {

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


            View view = View.inflate(aty, R.layout.text2, null);
            view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
            ImageView imageView = (ImageView) view.findViewById(R.id.image1);
            TextView textView = (TextView) view.findViewById(R.id.text1);
            int random = new Random().nextInt(2);


            if(random == 0) {
                imageView.setVisibility(View.VISIBLE);
               textView.setVisibility(View.INVISIBLE);
                Picasso.with(aty).load(ChideAdapter.img[position % ChideAdapter.img.length]).into(imageView);
            }
            else {
                imageView.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.VISIBLE);
                textView.setText(ss[position%ss.length]);
                textView.setBackgroundResource(cc[position % 4]);
            }

            return view;
        }
    }






}
