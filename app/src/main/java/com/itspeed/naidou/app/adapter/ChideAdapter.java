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
 */
public class ChideAdapter extends ListBaseAdapter<CookBook> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.item_list_chide,null);
            holder.img = (ImageView) convertView.findViewById(R.id.item_list_cookbook_img);
            holder.isLike = (ImageView) convertView.findViewById(R.id.item_list_cookbook_islike);
            holder.isCollect = (ImageView) convertView.findViewById(R.id.item_list_cookbook_iscollect);
            holder.title = (TextView) convertView.findViewById(R.id.item_list_cookbook_title);
            holder.time = (TextView) convertView.findViewById(R.id.item_list_cookbook_time);
            holder.likes = (TextView) convertView.findViewById(R.id.item_list_cookbook_likes);
            holder.collects = (TextView) convertView.findViewById(R.id.item_list_cookbook_collects);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        CookBook cb = mDatas.get(position);
        Picasso.with(parent.getContext()).load(img[position%img.length]).placeholder(R.mipmap.img1).into(holder.img);
        holder.title.setText("我是小鸡炖蘑菇");
        holder.time.setText("1小时前");
        holder.likes.setText("12");
        holder.collects.setText("234");
        return convertView;
    }

    class  ViewHolder {
        ImageView img;
        TextView title;
        TextView time;
        TextView likes;
        TextView collects;
        ImageView isLike;
        ImageView isCollect;
    }

    public static  String []img = new String[]{
            "http://h.hiphotos.baidu.com/image/pic/item/4e4a20a4462309f717d529fa760e0cf3d6cad6a4.jpg",
            "http://www.jiankangzu.com/pian/08/201108311249403130.jpg",
            "http://bcs.91.com/pcsuite-dev/img/0/640_960/d46fde33601bff2598cca768a0c13233.jpeg",
            "http://bcs.img.r1.91.com/data/upload/2014/09_21/23/201409212345301684.jpg",
            "http://c.hiphotos.baidu.com/zhidao/pic/item/a686c9177f3e670943c841b539c79f3df9dc55ab.jpg",
            "http://bbs.58game.com/data/attachment/forum/201410/16/041444wb1yqr6bzha9a1b6.jpg",
            "http://b.hiphotos.baidu.com/image/pic/item/9f2f070828381f304072d541ad014c086f06f0eb.jpg",
            "http://g.hiphotos.baidu.com/image/pic/item/5366d0160924ab18f937b9df31fae6cd7b890b30.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/e4dde71190ef76c6381a26c79916fdfaae516716.jpg",
            "http://c.hiphotos.baidu.com/image/pic/item/a6efce1b9d16fdfa0d011a02b08f8c5495ee7be9.jpg",
            "http://g.hiphotos.baidu.com/image/pic/item/14ce36d3d539b6005483d35bed50352ac75cb789.jpg",
            "http://b.hiphotos.baidu.com/image/pic/item/4610b912c8fcc3ce72c2e9fb9645d688d53f20f3.jpg",
            "http://e.hiphotos.baidu.com/image/pic/item/0dd7912397dda14439f38d6db6b7d0a20df48673.jpg",
            "http://g.hiphotos.baidu.com/image/pic/item/37d12f2eb9389b50b14357858135e5dde6116ead.jpg"

    };

}
