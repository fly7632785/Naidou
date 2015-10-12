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
 *
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
            "http://d.hiphotos.baidu.com/image/pic/item/1e30e924b899a901d79bee3a1f950a7b0208f514.jpg",
            "http://b.hiphotos.baidu.com/image/pic/item/72f082025aafa40f02b95dd0a964034f78f019bb.jpg",
            "http://b.hiphotos.baidu.com/image/pic/item/ca1349540923dd54af0b3565d309b3de9c824847.jpg",
            "http://d.hiphotos.baidu.com/image/pic/item/500fd9f9d72a605966a0dc862d34349b023bbafd.jpg",
            "http://b.hiphotos.baidu.com/image/pic/item/b3b7d0a20cf431adaa697bf14f36acaf2fdd98ba.jpg",
            "http://c.hiphotos.baidu.com/image/pic/item/5fdf8db1cb134954db9e1444534e9258d1094a0a.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/8d5494eef01f3a29e8a92cee9c25bc315c607ca9.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/b64543a98226cffc932cfbbfbb014a90f603eabd.jpg",
            "http://c.hiphotos.baidu.com/image/pic/item/e824b899a9014c08d47342820f7b02087bf4f449.jpg",
            "http://b.hiphotos.baidu.com/image/pic/item/8b82b9014a90f603d24af6603c12b31bb051ed1b.jpg",
            "http://b.hiphotos.baidu.com/image/pic/item/dc54564e9258d109f216c395d458ccbf6c814d1b.jpg",
            "http://a.hiphotos.baidu.com/image/pic/item/0b7b02087bf40ad1a278adc4522c11dfa9ecce7d.jpg"
    };

}
