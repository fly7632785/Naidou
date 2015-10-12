package com.itspeed.naidou.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.model.bean.Step;

import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;

/**
 * Created by jafir on 15/9/21.
 */
public class Step4GridViewAdapter extends BaseAdapter {

    private ArrayList<Step> list = new ArrayList<Step>();

    public Step4GridViewAdapter() {
            list.add(new Step());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.item_gridview_step4, null);
            holder.img = (ImageView) convertView.findViewById(R.id.item_gridview_step4_img);
            holder.desc = (TextView) convertView.findViewById(R.id.item_gridview_step4_describe);
            holder.step = (TextView) convertView.findViewById(R.id.item_gridview_step4_step);
            KJLoger.debug("position:"+position);
            holder.img.setOnClickListener(new MyClick(position));
            holder.desc.setOnClickListener(new MyClick(position));
            convertView.setTag(holder);
            KJLoger.debug("111111");
        } else {
            holder = (ViewHolder) convertView.getTag();
            KJLoger.debug("22222");
        }
        holder.step.setText("" + (position + 1));
        return convertView;
    }

    public void addDate() {
        list.add(new Step());
        notifyDataSetChanged();
    }

    public void deleteDate() {
        if(list.size() > 0) {
            list.remove(list.size()-1);
            notifyDataSetChanged();
        }
    }

    class ViewHolder {
        ImageView img;
        TextView desc;
        TextView step;
    }


    @Override
    public int getCount() {
        KJLoger.debug("size:"+list.size());
        return list.size();
    }


    @Override
    public long getItemId(int position) {
        return position;

    }


    @Override
    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    private class MyClick implements View.OnClickListener {
        private int position;


        public MyClick(int position) {
            this.position = position;
            KJLoger.debug("myCLick position:"+position);
        }



        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_gridview_step4_img:
                    //选择图片（拍照 或者 相册）
                    ImageView img = (ImageView) v;
                    KJLoger.debug(position+"被点击了：" + img);
                    img.setImageResource(R.mipmap.img4);
                    break;

                case R.id.item_gridview_step4_describe:
                    //填描述
                    TextView textView = (TextView) v;
                    KJLoger.debug(position+"被点击了tet："+textView);
                    textView.setText("描述描述");
                    break;
            }
        }
    }
}
