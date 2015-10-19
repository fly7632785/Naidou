package com.itspeed.naidou.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.model.bean.CookBook;
import com.squareup.picasso.Picasso;

/**
 * Created by jafir on 15/9/21.
 * 吃的 适配器
 */
public class ChideAdapter extends ListBaseAdapter<CookBook> implements View.OnClickListener {

    private ViewHolder holder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.item_list_chide, null);
            holder.img = (ImageView) convertView.findViewById(R.id.item_list_cookbook_img);
            holder.isLike = (ImageView) convertView.findViewById(R.id.item_list_cookbook_islike);
            holder.isCollect = (ImageView) convertView.findViewById(R.id.item_list_cookbook_iscollect);
            holder.title = (TextView) convertView.findViewById(R.id.item_list_cookbook_title);
            holder.time = (TextView) convertView.findViewById(R.id.item_list_cookbook_time);
            holder.likes = (TextView) convertView.findViewById(R.id.item_list_cookbook_likes);
            holder.collects = (TextView) convertView.findViewById(R.id.item_list_cookbook_collects);
            holder.isCollect.setOnClickListener(this);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CookBook cb = mDatas.get(position);
        Picasso.with(parent.getContext()).load(img[position % img.length]).into(holder.img);
        holder.title.setText("我是小鸡炖蘑菇");
        holder.time.setText("1小时前");
        holder.likes.setText("12");
        holder.collects.setText("234");
        holder.isCollect.setSelected(isCollect);
        holder.isCollect.setSelected(isLike);
        return convertView;
    }


    private boolean isLike;
    private boolean isCollect;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_list_cookbook_iscollect:
                holder.isCollect.setSelected(isCollect);
                isCollect = !isCollect;
                break;
            case R.id.item_list_cookbook_islike:
                holder.isLike.setSelected(isLike);
                isLike = !isLike;
                break;

        }
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

    public static String[] img = new String[]{
            "http://pic31.nipic.com/20130722/11643229_142459548179_2.jpg",
            "http://file2.zhituad.com/thumb/201201/13/201201130300339473kNpfJ_priv.jpg",
            "http://pic29.nipic.com/20130522/11024153_152851327158_2.jpg",
            "http://pic4.nipic.com/20091010/1054952_012941461069_2.jpg",
            "http://pic1a.nipic.com/2008-10-20/2008102095428911_2.jpg",
            "http://pica.nipic.com/2008-02-25/200822512913645_2.jpg"
    };

}
