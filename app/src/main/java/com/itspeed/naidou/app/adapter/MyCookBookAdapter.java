package com.itspeed.naidou.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.AppContext;
import com.itspeed.naidou.app.util.TimeUtil;
import com.itspeed.naidou.model.bean.CookBook;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.utils.StringUtils;

import java.util.Random;

/**
 * Created by jafir on 15/9/29.
 */
public class MyCookBookAdapter extends ListBaseAdapter<CookBook> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.item_list_mycookbook,null);
            holder.img = (ImageView) convertView.findViewById(R.id.item_list_mycookbook_img);
            holder.isLike = (ImageView) convertView.findViewById(R.id.item_list_mycookbook_islike);
            holder.isCollect = (ImageView) convertView.findViewById(R.id.item_list_mycookbook_iscollect);
            holder.drip = (ImageView) convertView.findViewById(R.id.item_list_mycookbook_drip);
            holder.title = (TextView) convertView.findViewById(R.id.item_list_mycookbook_title);
            holder.time = (TextView) convertView.findViewById(R.id.item_list_mycookbook_time);
            holder.likes = (TextView) convertView.findViewById(R.id.item_list_mycookbook_likes);
            holder.collects = (TextView) convertView.findViewById(R.id.item_list_mycookbook_collects);
            holder.cate = (TextView) convertView.findViewById(R.id.item_list_mycookbook_type);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        CookBook cb = mDatas.get(position);
        new KJBitmap.Builder().imageUrl(AppContext.HOST+cb.getCover()).errorBitmapRes(R.mipmap.img2).view(holder.img).display();
        holder.drip.setImageResource(drips[new Random().nextInt(3)]);
        holder.title.setText(cb.getTitle());
        holder.isLike.setSelected(cb.isLike());
        holder.isLike.setEnabled(false);
        holder.isCollect.setEnabled(false);
        holder.isCollect.setSelected(cb.isCollect());
        holder.time.setText(StringUtils.friendlyTime(TimeUtil.msToDate(cb.getTime())));
        holder.likes.setText(cb.getLikedCount()+"");
        holder.collects.setText(cb.getCollectCount() + "");
        holder.cate.setText(cb.getCateName()+"餐");
        return convertView;
    }

    class  ViewHolder {
        ImageView img;
        ImageView drip;
        TextView title;
        TextView time;
        TextView likes;
        TextView collects;
        TextView cate;
        ImageView isLike;
        ImageView isCollect;
    }

    private static int[] drips = new int[]{
            R.mipmap.common_drip_purple,
            R.mipmap.common_drip_red,
            R.mipmap.common_drip_yellow
    };




}
