package com.itspeed.naidou.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.itspeed.naidou.R;

/**
 * Created by jafir on 15/10/8.
 */
public class Step3RecylerAdapter extends RecyclerView.Adapter<Step3RecylerAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = View.inflate(parent.getContext(),R.layout.item_recyclerview_step3,null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position == 0){
            holder.cailiao.setHint("如:猪肉");
            holder.yongliang.setHint("如:500g");
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder {
        public  ImageView delete;
        public EditText cailiao;
        public  EditText yongliang;

        public ViewHolder(View itemView) {
            super(itemView);
            delete = (ImageView) itemView.findViewById(R.id.item_recycler_step3_delete);
            yongliang = (EditText) itemView.findViewById(R.id.item_recycler_step3_yongliang);
            cailiao = (EditText) itemView.findViewById(R.id.item_recycler_step3_cailiao);

        }
    }
}
