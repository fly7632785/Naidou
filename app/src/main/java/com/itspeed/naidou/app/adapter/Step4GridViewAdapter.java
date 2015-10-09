package com.itspeed.naidou.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itspeed.naidou.R;

import java.util.ArrayList;

/**
 * Created by jafir on 15/9/21.
 */
public class Step4GridViewAdapter extends ListBaseAdapter {

    private  ArrayList list = new ArrayList();

    public Step4GridViewAdapter() {
        for(int i=0;i < 6;i++){
            list.add(i);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.item_gridview_step4,null);
            holder.img = (ImageView) convertView.findViewById(R.id.item_gridview_step4_img);
            holder.desc = (TextView) convertView.findViewById(R.id.item_gridview_step4_describe);
            holder.step = (TextView) convertView.findViewById(R.id.item_gridview_step4_step);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.step.setText(""+(position+1));

        return convertView;
    }

    public void addDate() {
        list.add(1);
        notifyDataSetChanged();
    }

    class  ViewHolder {
        ImageView img;
        TextView desc;
        TextView step;
    }


    @Override
    public int getCount() {
        return list.size();
    }
}
