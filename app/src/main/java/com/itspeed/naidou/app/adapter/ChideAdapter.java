package com.itspeed.naidou.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.AppContext;
import com.itspeed.naidou.app.listener.LikeAndCollecListener;
import com.itspeed.naidou.app.util.TimeUtil;
import com.itspeed.naidou.model.bean.CookBook;
import com.squareup.picasso.Picasso;

import org.kymjs.kjframe.utils.StringUtils;

/**
 * Created by jafir on 15/9/21.
 * 吃的 适配器
 */
public class ChideAdapter extends ListBaseAdapter<CookBook> {

    private ViewHolder holder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.item_list_chide, null);
            holder.img = (ImageView) convertView.findViewById(R.id.item_list_cookbook_img);
            holder.isLike = (ImageView) convertView.findViewById(R.id.layout_islike);
            holder.isCollect = (ImageView) convertView.findViewById(R.id.layout_iscollect);
            holder.title = (TextView) convertView.findViewById(R.id.item_list_cookbook_title);
            holder.time = (TextView) convertView.findViewById(R.id.item_list_cookbook_time);
            holder.likes = (TextView) convertView.findViewById(R.id.layout_likes);
            holder.collects = (TextView) convertView.findViewById(R.id.layout_collects);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.isCollect.setOnClickListener(new LikeAndCollecListener(position, mDatas, this));
        holder.isLike.setOnClickListener(new LikeAndCollecListener(position, mDatas, this));

        if(mDatas.size()==0 || mDatas==null){
            return convertView;
        }
        CookBook cb = mDatas.get(position);
        Picasso.with(parent.getContext()).load(AppContext.HOST + cb.getCover()).into(holder.img);
        holder.title.setText(cb.getTitle());
        holder.time.setText(StringUtils.friendlyTime(TimeUtil.msToDate(cb.getTime())));
        holder.likes.setText(cb.getLikedCount() + "");
        holder.collects.setText(cb.getCollectCount() + "");
        holder.isCollect.setSelected(cb.isCollect());
        holder.isLike.setSelected(cb.isLike());
        return convertView;
    }


    class ViewHolder {
        ImageView img;
        TextView title;
        TextView time;
        TextView likes;
        TextView collects;
        ImageView isLike;
        ImageView isCollect;
    }




}
