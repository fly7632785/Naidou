package com.itspeed.naidou.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;
import com.itspeed.naidou.app.AppContext;
import com.itspeed.naidou.app.util.TimeUtil;
import com.itspeed.naidou.model.bean.CookBook;
import com.squareup.picasso.Picasso;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.StringUtils;

/**
 * Created by jafir on 15/9/21.
 * 吃的 适配器
 */
public class ChideAdapter extends ListBaseAdapter<CookBook> {

    private ViewHolder holder;
    private boolean isLike;
    private boolean isCollect;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.item_list_chide, null);
            holder.img = (ImageView) convertView.findViewById(R.id.item_list_cookbook_img);
            holder.isLike = (ImageView) convertView.findViewById(R.id.item_list_cookbook_islike);
            holder.isCollect = (ImageView) convertView.findViewById(R.id.item_list_cookbook_iscollect);
            holder.title = (TextView) convertView.findViewById(R.id.item_list_cookbook_title);
            holder.time = (TextView) convertView.findViewById(R.id.item_list_cookbook_time);
            holder.likes = (TextView) convertView.findViewById(R.id.item_list_cookbook_likes);
            holder.collects = (TextView) convertView.findViewById(R.id.item_list_cookbook_collects);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.isCollect.setOnClickListener(new MyClick(position));
        holder.isLike.setOnClickListener(new MyClick(position));

        CookBook cb = mDatas.get(position);
        Picasso.with(parent.getContext()).load(AppContext.HOST+cb.getCover()).into(holder.img);
        holder.title.setText(cb.getTitle());
        holder.time.setText(StringUtils.friendlyTime(TimeUtil.msToDate(cb.getTime())));
        holder.likes.setText(cb.getLikedCount()+"");
        holder.collects.setText(cb.getCollectCount()+"");
        holder.isCollect.setSelected(cb.isCollect());
        holder.isLike.setSelected(cb.isLike());
        return convertView;
    }



    class ViewHolder {
        ImageView img;
        TextView title;
        TextView time;
        TextView likes;
        TextView collects;
        ImageView isLike;
        ImageView isCollect;
    }

    public static String[] img = new String[]{
            "http://pic31.nipic.com/20130722/11643229_142459548179_2.jpg",
            "http://file2.zhituad.com/thumb/201201/13/201201130300339473kNpfJ_priv.jpg",
            "http://pic29.nipic.com/20130522/11024153_152851327158_2.jpg",
            "http://pic4.nipic.com/20091010/1054952_012941461069_2.jpg",
            "http://pic1a.nipic.com/2008-10-20/2008102095428911_2.jpg",
            "http://pica.nipic.com/2008-02-25/200822512913645_2.jpg"
    };

    private class MyClick implements View.OnClickListener{
        private int position;

        public MyClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            ImageView img = (ImageView) v;
            if(mDatas == null){
                return;
            }
            CookBook cb = mDatas.get(position);
            isCollect = cb.isCollect();
            isLike = cb.isLike();
            String cid =String.valueOf(mDatas.get(position).getCid());
            switch (v.getId()) {
                case R.id.item_list_cookbook_iscollect:
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
                case R.id.item_list_cookbook_islike:
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
                KJLoger.debug("收藏成功：" + t);
                if(Response.getSuccess(t)){
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
                KJLoger.debug("取消收藏成功：" + t);
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
                KJLoger.debug("点赞成功：" + t);
                if (Response.getSuccess(t)) {
                    ViewInject.toast("点赞成功");
                }
            }
        });

    }

    private void cancelLike(String cid) {
        NaidouApi.cancelLikeForChide( cid, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("取消点赞成功：" + t);
                if (Response.getSuccess(t)) {
                    ViewInject.toast("取消点赞成功");
                }
            }
        });
    }


}
