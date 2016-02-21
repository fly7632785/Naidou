package com.itspeed.naidou.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.model.bean.Step;

import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;

/**
 * Created by jafir on 15/10/8.
 */
public class StepRecylerAdapter extends RecyclerView.Adapter<StepRecylerAdapter.ViewHolder> {

    private ArrayList<Step> list;
    private Context context;
    private LayoutInflater mInflater;

    public StepRecylerAdapter(ArrayList<Step> list) {
        super();
        this.list = list;
    }

    public void addData(Step step) {
        list.add(step);
        notifyDataSetChanged();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            context = parent.getContext();
            mInflater = LayoutInflater.from(context);
        }
        View inflate = mInflater.inflate(R.layout.item_recyclerview_step, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(list == null){
            return;
        }
        holder.count.setText(""+(position+1));
        holder.desc.setText(list.get(position).getDescription());
        if(!list.get(position).getPic().getLocalPath().equals("0")) {
            Bitmap bm = BitmapFactory.decodeFile(list.get(position).getPic().getLocalPath());
            if (bm != null ) {
                holder.img.setImageBitmap(bm);
            }
        }else {
            holder.img.setImageResource(R.mipmap.default_bg);
        }

    }

    @Override
    public int getItemCount() {
        KJLoger.debug("size:" + list.size());
        return list.size();
    }

    public void refresh(int position) {
        notifyItemChanged(position);
    }

    public void delete(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public  ImageView img;
        public TextView count;
        public TextView desc;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null && list.size() != 0) {
                        mOnItemClickListener.onItemClick(v, getAdapterPosition() % list.size());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemLongClickListener != null && list.size() != 0) {
                        mOnItemLongClickListener.onItemLongClick(v, getAdapterPosition() % list.size());
                        return true;
                    }
                    return false;
                }
            });
            img = (ImageView) itemView.findViewById(R.id.item_recycler_step_img);
            count = (TextView) itemView.findViewById(R.id.item_recycler_step_count);
            desc = (TextView) itemView.findViewById(R.id.item_recycler_step_desc);

        }
    }


    public interface OnItemClickListener{
        void onItemClick(View view,int position);

    }
    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }


    public static OnItemClickListener mOnItemClickListener = null;
    public static OnItemLongClickListener mOnItemLongClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }





}
