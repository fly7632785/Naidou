package com.itspeed.naidou.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;
import com.itspeed.naidou.app.AppContext;
import com.itspeed.naidou.model.bean.CookBook;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;

/**
 * Created by jafir on 15/9/29.
 */
public class MyCollectAdapter extends ListBaseAdapter<CookBook> {

    private boolean isCollect;
    private boolean isLike;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.item_list_mycollect,null);
            holder.img = (ImageView) convertView.findViewById(R.id.item_list_mycollect_img);
            holder.portrait = (ImageView) convertView.findViewById(R.id.item_list_mycollect_portrait);
            holder.author = (TextView) convertView.findViewById(R.id.item_list_mycollect_name);
            holder.title = (TextView) convertView.findViewById(R.id.item_list_mycollect_title);

            View likecollect = convertView.findViewById(R.id.item_list_mycollect_layout_like_collect);
            holder.isLike = (ImageView) likecollect.findViewById(R.id.layout_islike);
            holder.isCollect = (ImageView) likecollect.findViewById(R.id.layout_iscollect);
            holder.likes = (TextView) likecollect.findViewById(R.id.layout_likes);
            holder.collects = (TextView) likecollect.findViewById(R.id.layout_collects);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置监听
        holder.isCollect.setOnClickListener(new MyClick(position));
        holder.isLike.setOnClickListener(new MyClick(position));


        CookBook cb = mDatas.get(position);
        new KJBitmap.Builder().imageUrl(AppContext.HOST+cb.getCover()).errorBitmapRes(R.mipmap.default_bg).view(holder.img).display();
        new KJBitmap.Builder().imageUrl(AppContext.HOST+cb.getFromWhoAvatar()).errorBitmapRes(R.mipmap.default_avatar).view(holder.portrait).display();
        holder.title.setText(cb.getTitle());
        holder.author.setText(cb.getFromWho());
        holder.likes.setText(cb.getLikedCount()+"");
        holder.isCollect.setSelected(cb.isCollect());
        holder.isLike.setSelected(cb.isLike());
        holder.collects.setText(cb.getCollectCount()+"");
        return convertView;
    }

    class  ViewHolder {
        ImageView img;
        ImageView portrait;
        TextView title;
        TextView author;
        TextView likes;
        TextView collects;
        ImageView isLike;
        ImageView isCollect;
    }

    private class MyClick implements View.OnClickListener{
        private int position;

        public MyClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            ImageView img = (ImageView) v;
            CookBook cb = mDatas.get(position);
            isCollect = cb.isCollect();
            isLike = cb.isLike();
            String cid =String.valueOf(mDatas.get(position).getCid());
            switch (v.getId()) {
                case R.id.layout_iscollect:
                    isCollect = !isCollect;
                    //本地改变状态
                    mDatas.get(position).setIsCollect(isCollect);

                    //网络数据改变状态
                    //请求点赞或者取消点赞
                    if(isCollect){
                        doCollect(cid);
                        //收藏数改变
                        mDatas.get(position).setCollectCount(cb.getCollectCount() + 1);
                    }else {
                        cancelCollect(cid);
                        mDatas.get(position).setCollectCount(cb.getCollectCount() - 1);
                    }
                    //点击之后刷新
                    notifyDataSetChanged();
                    break;
                case R.id.layout_islike:
                    isLike = !isLike;
                    //本地改变状态
                    mDatas.get(position).setIsLike(isLike);
                    //网络数据改变状态
                    //请求点赞或者取消点赞
                    if(isLike){
                        doLike(cid);
                        //点赞数改变
                        mDatas.get(position).setLikedCount(cb.getLikedCount() + 1);
                    }else {
                        cancelLike(cid);
                        //点赞数改变
                        mDatas.get(position).setLikedCount(cb.getLikedCount() - 1);
                    }
                    notifyDataSetChanged();
                    break;
            }
        }
    }

    private void doCollect(String cid) {
        NaidouApi.doCollectForChide(cid, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                if (Response.getSuccess(t)) {
                    ViewInject.toast("收藏成功");
                }
            }
        });
    }

    private void cancelCollect(String cid) {
        NaidouApi.cancelCollectForChide(cid, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("点赞：" + t);
                if (Response.getSuccess(t)) {
                    ViewInject.toast("取消收藏成功");
                }
            }
        });
    }

    private void doLike(String cid) {
        NaidouApi.doLikeForChide(cid, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                if (Response.getSuccess(t)) {
                    ViewInject.toast("点赞成功");
                }
            }
        });

    }

    private void cancelLike(String cid) {
        NaidouApi.cancelLikeForChide(cid, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                if (Response.getSuccess(t)) {
                    ViewInject.toast("取消点赞成功");
                }
            }
        });
    }


}
