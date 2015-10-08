package com.itspeed.naidou.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itspeed.naidou.R;

/**
 * Created by jafir on 15/9/21.
 */
public class Step4GridViewAdapter extends ListBaseAdapter {

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

    class  ViewHolder {
        ImageView img;
        TextView desc;
        TextView step;
    }


    @Override
    public int getCount() {
        return 6;
    }
}
