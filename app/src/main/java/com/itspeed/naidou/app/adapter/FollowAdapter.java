package com.itspeed.naidou.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.model.bean.User;

/**
 * Created by jafir on 15/9/29.
 * 关注的适配器
 */
public class FollowAdapter extends ListBaseAdapter<User> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.item_list_follow,null);
            holder.portrait = (ImageView) convertView.findViewById(R.id.item_list_follow_portrait);
            holder.count = (TextView) convertView.findViewById(R.id.item_list_follow_count);
            holder.isFollow = (ImageView) convertView.findViewById(R.id.item_list_follow_isfollow);
            holder.name = (TextView) convertView.findViewById(R.id.item_list_follow_name);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    class  ViewHolder {
        ImageView portrait;
        TextView name;
        TextView count;
        ImageView isFollow;
    }



}
