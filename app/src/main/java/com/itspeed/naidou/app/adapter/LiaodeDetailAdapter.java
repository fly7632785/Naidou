package com.itspeed.naidou.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itspeed.naidou.R;

/**
 * Created by jafir on 15/9/29.
 * 关注的适配器
 */
public class LiaodeDetailAdapter extends ListBaseAdapter {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.item_liaode_detail,null);
            holder.portrait = (ImageView) convertView.findViewById(R.id.item_liaode_detail_portrait);
            holder.name = (TextView) convertView.findViewById(R.id.item_liaode_detail_author);
            holder.content = (TextView) convertView.findViewById(R.id.item_liaode_detail_content);
            holder.time = (TextView) convertView.findViewById(R.id.item_liaode_detail_time);
            holder.reply = convertView.findViewById(R.id.item_liaode_detail_reply);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class  ViewHolder {
        ImageView portrait;
        TextView name;
        TextView time;
        TextView content;
        View reply;

    }



}
