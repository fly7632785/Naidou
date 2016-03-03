package com.itspeed.naidou.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.AppContext;
import com.itspeed.naidou.app.helper.OperateHelper;
import com.itspeed.naidou.model.bean.User;

import org.kymjs.kjframe.KJBitmap;

/**
 * Created by jafir on 15/9/29.
 * 关注的适配器
 */
public class FollowAdapter extends ListBaseAdapter<User> {

    private ViewHolder holder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         holder = null;
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

        holder.isFollow.setOnClickListener(new MyClick(position));
        User user = mDatas.get(position);
        new KJBitmap.Builder().imageUrl(AppContext.HOST + user.getAvatar()).view(holder.portrait).errorBitmapRes(R.mipmap.portrait).display();
//        Picasso.with(parent.getContext()).load(user.getAvatar()).into(holder.portrait);
        holder.count.setText(user.getCookBookCount()+"个菜谱");
        holder.name.setText(user.getNickname());
        if(user.isFollowedByMe()){
            holder.isFollow.setImageResource(R.mipmap.myfollow_cancel);
        }else {
            holder.isFollow.setImageResource(R.mipmap.myfollow_follow);
        }
        return convertView;
    }

    class  ViewHolder {
        ImageView portrait;
        TextView name;
        TextView count;
        ImageView isFollow;
    }


    private class MyClick implements View.OnClickListener {
        private int position;
        public MyClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            final ImageView img = (ImageView) v;
           boolean isFollowed = mDatas.get(position).isFollowedByMe();
            if(v.getId() == R.id.item_list_follow_isfollow) {
                if (isFollowed) {
                    OperateHelper.cancelFollow(mDatas.get(position).getUid());
                    mDatas.get(position).setIsFollowedByMe(false);
                    notifyDataSetChanged();
                } else {
                    OperateHelper.doFollow(mDatas.get(position).getUid());
                    mDatas.get(position).setIsFollowedByMe(true);
                    notifyDataSetChanged();
                }
            }

        }
    }



}
