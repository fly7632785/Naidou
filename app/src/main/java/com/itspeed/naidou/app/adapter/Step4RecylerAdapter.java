package com.itspeed.naidou.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itspeed.naidou.R;

/**
 * Created by jafir on 15/10/8.
 */
public class Step4RecylerAdapter extends RecyclerView.Adapter<Step4RecylerAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = View.inflate(parent.getContext(),R.layout.item_recyclerview_step4,null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.step.setText(position+1+"");
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder {
        public  ImageView img;
        public TextView step;
        public TextView describe;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.item_recycler_step4_img);
            step = (TextView) itemView.findViewById(R.id.item_recycler_step4_step);
            describe = (TextView) itemView.findViewById(R.id.item_recycler_step4_describe);

        }
    }
}
