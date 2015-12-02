package com.itspeed.naidou.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.AppContext;
import com.itspeed.naidou.app.util.UIHelper;
import com.itspeed.naidou.model.bean.CookBook;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jafir on 15/10/7.
 * 推荐的adapter
 * 因为是recycler的adapter 所以自己写了点击的接口
 * 调用者需要实现这个接口
 */
public class RecommendRecyclerAdapterForCb extends RecyclerView.Adapter<RecommendRecyclerAdapterForCb.ImageViewHolder> {

    private Context mContext;

    private LayoutInflater mInflater;

    private ArrayList<CookBook> mCookbooks;


    public void setData(ArrayList<CookBook> list){
        mCookbooks = list;
        this.notifyDataSetChanged();
    }


    class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView cover;
        TextView title;
        ImageView userAvatar;
        TextView desc;
        TextView userName;
        TextView cate;
        TextView stepCount;



        public ImageViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null && mCookbooks.size()!= 0) {
                        mOnItemClickListener.onItemClick(v, getAdapterPosition() % mCookbooks.size());
                    }
                }
            });

            cover = (ImageView) itemView.findViewById(R.id.item_list_cb_recommend_img);
            userAvatar = (ImageView) itemView.findViewById(R.id.item_list_cb_recommend_portrait);
            title = (TextView) itemView.findViewById(R.id.item_list_cb_recommend_title);
            desc = (TextView) itemView.findViewById(R.id.item_list_cb_recommend_desc);
            userName = (TextView) itemView.findViewById(R.id.item_list_cb_recommend_username);
            cate = (TextView) itemView.findViewById(R.id.item_list_cb_recommend_cate);
            stepCount = (TextView) itemView.findViewById(R.id.item_list_cb_recommend_steps);

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
        if(mCookbooks.isEmpty()){
            return;
        }
        position = position % mCookbooks.size();
        final CookBook cookBook = mCookbooks.get(position);

        holder.userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showZone(mContext, cookBook.getFromWhoId());
            }
        });
        Picasso.with(mContext).load(AppContext.HOST+cookBook.getCover()).into(holder.cover);
        Picasso.with(mContext).load(AppContext.HOST+cookBook.getFromWhoAvata()).into(holder.userAvatar);
        holder.title.setText(cookBook.getTitle());
        holder.desc.setText(cookBook.getDescription());
        holder.userName.setText(cookBook.getFromWho());
        holder.cate.setText(cookBook.getCateName()+"餐");
        holder.stepCount.setText("共"+cookBook.getStepCount()+"步");
    }





    public interface OnItemClickListener{
        void onItemClick(View view,int position);

    }


    public static OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public static  String []img = new String[]{
            "http://img4.duitang.com/uploads/item/201406/09/20140609203417_Ckvwa.jpeg",
            "http://img5.duitang.com/uploads/item/201407/22/20140722231323_EdYkx.thumb.700_0.jpeg",
            "http://cdn.duitang.com/uploads/item/201411/22/20141122202609_Va5uk.thumb.700_0.jpeg",
            "http://picm.photophoto.cn/087/069/122/0691220027.jpg",
            "http://picm.photophoto.cn/069/041/005/0410050117.jpg"
    };

}
