package com.itspeed.naidou.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.model.bean.CookBook;
import com.squareup.picasso.Picasso;

import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jafir on 15/10/7.
 */
public class RecommendRecyclerAdapter extends RecyclerView.Adapter<RecommendRecyclerAdapter.ImageViewHolder> {

    private Context mContext;

    private LayoutInflater mInflater;

    private ArrayList<CookBook> mCookbooks;

    public RecommendRecyclerAdapter(List<CookBook> cookbooks) {
        mCookbooks = new ArrayList<>(cookbooks);
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClickListener != null) {
                        KJLoger.debug("onitemposi:"+getAdapterPosition());
                        KJLoger.debug("之后的onitemposi:"+(getAdapterPosition()%mCookbooks.size()));
                        mOnItemClickListener.onItemClick(v, getAdapterPosition()%mCookbooks.size());
                    }
                }
            });

            imageView = (ImageView) itemView.findViewById(R.id.item_list_cb_recommend_img);
            textView = (TextView) itemView.findViewById(R.id.item_list_cb_recommend_title);
        }
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mContext = parent.getContext();
            mInflater = LayoutInflater.from(mContext);
        }
        View inflate = mInflater.inflate(R.layout.item_list_cb_recommend, parent, false);
        return new ImageViewHolder(inflate);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }



    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        KJLoger.debug("onBindViewHolder  position:"+position);
        position = position % mCookbooks.size();
        KJLoger.debug("onBindViewHolder 之后的 position:"+position);
        CookBook cookBook = mCookbooks.get(position);
        Picasso.with(mContext).load(ChideAdapter.img[position]).into(holder.imageView);
        holder.textView.setText(cookBook.getTitle());
    }





    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }


    public static OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }
}
