package com.itspeed.naidou.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.squareup.picasso.Picasso;

/**
 * Created by jafir on 15/9/21.
 * 逛得 适配器
 */
public class GuangdeGridViewAdapter extends ListBaseAdapter {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.item_gridview_guangde,null);
            holder.img = (ImageView) convertView.findViewById(R.id.item_gridview_guangde_img);
            holder.title = (TextView) convertView.findViewById(R.id.item_gridview_guangde_title);
            holder.price = (TextView) convertView.findViewById(R.id.item_gridview_guangde_price);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(parent.getContext()).load(img[position%img.length]).into(holder.img);
        return convertView;
    }

    class  ViewHolder {
        ImageView img;
        TextView title;
        TextView price;
    }
    public static  String []img = new String[]{
            "http://image.leyou.com.cn/upload_img/B2c/weiyang/H56/H56K305600.jpg",
            "http://d04.res.meilishuo.net/pic/_o/34/f2/7581002b3b7170fbcfbfda0dea26_315_324.c6.jpg?refer_type=&expr_alt=bundefinedfrm=out_title",
            "http://d05.res.meilishuo.net/pic/_o/05/f0/1c590c5d118d95034dacc72864aa_400_400.c6.jpg?refer_type=&expr_alt=aundefinedfrm=out_title",
            "http://pic4.nipic.com/20090731/2990649_211318068_2.jpg",
            "http://file.youboy.com/d/150/50/47/4/298024.jpg",
            "http://www.dazhe.de/cn/wp-content/uploads/2012/07/medela2.jpg",
            "http://img11.360buyimg.com/n0/1466/54556afd-2607-4622-9aae-3613dcb88088.jpg"
    };

    @Override
    public int getCount() {
        return 8;
    }
}
