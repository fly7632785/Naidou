package com.itspeed.naidou.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.model.bean.CookBook;
import com.squareup.picasso.Picasso;

/**
 * Created by jafir on 15/9/29.
 */
public class MyCollectAdapter extends ListBaseAdapter<CookBook> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.item_list_mycollect,null);
            holder.img = (ImageView) convertView.findViewById(R.id.item_list_mycollect_img);
            holder.portrait = (ImageView) convertView.findViewById(R.id.item_list_mycollect_portrait);
            holder.author = (TextView) convertView.findViewById(R.id.item_list_mycollect_name);
            holder.isLike = (ImageView) convertView.findViewById(R.id.item_list_mycollect_islike);
            holder.isCollect = (ImageView) convertView.findViewById(R.id.item_list_mycollect_iscollect);
            holder.title = (TextView) convertView.findViewById(R.id.item_list_mycollect_title);
            holder.likes = (TextView) convertView.findViewById(R.id.item_list_mycollect_likes);
            holder.collects = (TextView) convertView.findViewById(R.id.item_list_mycollect_collects);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        CookBook cb = mDatas.get(position);
        Picasso.with(parent.getContext()).load(RecommendRecyclerAdapterForCb.img[position% RecommendRecyclerAdapterForCb.img.length]).placeholder(R.mipmap.img1).into(holder.img);
        holder.title.setText("我是小鸡炖蘑菇");
        holder.likes.setText("12");
        holder.isLike.setSelected(true);
        holder.collects.setText("234");
        return convertView;
    }

    class  ViewHolder {
        ImageView img;
        ImageView portrait;
        TextView title;
        TextView author;
        TextView likes;
        TextView collects;
        ImageView isLike;
        ImageView isCollect;
    }




}
