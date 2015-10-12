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

        Picasso.with(parent.getContext()).load(ChideAdapter.img[position%ChideAdapter.img.length]).into(holder.img);
        return convertView;
    }

    class  ViewHolder {
        ImageView img;
        TextView title;
        TextView price;
    }


    @Override
    public int getCount() {
        return 8;
    }
}
