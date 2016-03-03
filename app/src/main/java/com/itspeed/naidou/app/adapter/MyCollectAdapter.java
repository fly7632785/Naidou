package com.itspeed.naidou.app.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.AppConfig;
import com.itspeed.naidou.app.AppContext;
import com.itspeed.naidou.app.helper.OperateHelper;
import com.itspeed.naidou.model.bean.CookBook;
import com.squareup.picasso.Picasso;

/**
 * Created by jafir on 15/9/29.
 * 我收藏的菜谱的adapter
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
        Picasso.with(parent.getContext()).load(AppContext.HOST+cb.getCover()).error(R.mipmap.default_bg).into(holder.img);
        Picasso.with(parent.getContext()).load(AppContext.HOST+cb.getFromWhoAvatar()).error(R.mipmap.default_avatar).into(holder.portrait);
//        new KJBitmap.Builder().imageUrl(AppContext.HOST+cb.getCover()).errorBitmapRes(R.mipmap.default_bg).view(holder.img).display();
//        new KJBitmap.Builder().imageUrl(AppContext.HOST+cb.getFromWhoAvatar()).errorBitmapRes(R.mipmap.default_avatar).view(holder.portrait).display();
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


    /**
     * 这里的点赞和收藏 注意
     * 最好 分开来写
     * 分为 网络改变 和 本地改变
     * 由于网络是有时延的，我们采取的策略是
     * 网络访问和本地改变独立开来，一起进行
     * 这样 代码执行下去立马就能看到效果
     *
     * @param position
     */


    public void changeLikeNetState(int position) {
        CookBook cb = mDatas.get(position);
        String cid = cb.getCid();
        if (!cb.isLike()) {
            OperateHelper.doLike(cid);
        } else {
            OperateHelper.cancelLike(cid);
        }
    }

    public void changeCollectNetState(int position) {
        CookBook cb = mDatas.get(position);
        String cid = cb.getCid();
        if (!cb.isCollect()) {
            OperateHelper.doCollect(cid);
        } else {
            OperateHelper.cancelCollect(cid);
        }
    }

    /**
     * 这里 参数里面有一个ocid 这个表示传入的cid
     * ocid会和 相对应position的data数组里面的cookbook的cid进行比对
     * 这里的比对是为了 确认是不是同一个 列表传入的菜谱的cid和详情里面的cid一致
     * 如果一致则表示 他们是同一个菜谱 则可以进行点赞收藏等操作
     * 如果不一致则表示不同菜谱  可能是 从孕初列表进入的详情界面传入的第一菜谱的cid
     * 而这里的 cid也是position为0 也就是第一个菜谱的cid 但这里的cid可能是 备孕adapter下的
     * 也可能是 月子 或者其他adapter下的 第一个菜谱的cid
     * 因为我们的adapter 和 每个种类列表是复用同一个 level2fragment 改造而来的
     * 这里的cid比对 就是为了排除不同的 fragment下的adapter里面的数据重合
     *
     * @param position
     * @param ocid
     */
    public void changeLikeLocalState(int position, String ocid) {
        CookBook cb = mDatas.get(position);
        String cid = cb.getCid();
        if (!ocid.equals(cid)) {
            return;
        }
        //本地改变状态
        mDatas.get(position).setIsLike(!cb.isLike());
        //网络数据改变状态
        //请求点赞或者取消点赞
        if (cb.isLike()) {
            //点赞数改变
            mDatas.get(position).setLikedCount(cb.getLikedCount() + 1);
        } else {
            //点赞数改变
            mDatas.get(position).setLikedCount(cb.getLikedCount() - 1);
        }
        notifyDataSetChanged();
    }

    public void changeCollectLocalState(int position, String ocid) {

        CookBook cb = mDatas.get(position);
        String cid = cb.getCid();
        if (!ocid.equals(cid)) {
            return;
        }
        //本地改变状态
        mDatas.get(position).setIsCollect(!cb.isCollect());
        //网络数据改变状态
        //请求点赞或者取消点赞
        if (cb.isCollect()) {
            //收藏数改变
            mDatas.get(position).setCollectCount(cb.getCollectCount() + 1);
        } else {
            mDatas.get(position).setCollectCount(cb.getCollectCount() - 1);
        }
        //点击之后刷新
        notifyDataSetChanged();
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
            String cid = cb.getCid();
            switch (v.getId()) {
                case R.id.layout_iscollect:
                    /**
                     * 这里需要先修改网络的状态，因为网络的状态的是否修改是取决于
                     * 本地的数据的状态，先网络修改了
                     * 然后再执行本地的数据修改
                     * 达到逻辑一致的效果
                     * 如果交换顺序 则 网络的那个方法需要把if()条件 ！倒置一下
                     * 所以要注意
                     */
                    /**
                     * 这里发送广播去 修改 列表界面的 数据状态
                     *
                     */
                    Intent intent = new Intent(AppConfig.RECEIVER_CHANGE_COLLECT_MYCOLLECT);
                    intent.putExtra("cid",cid);
                    v.getContext().sendBroadcast(intent);
                    changeCollectNetState(position);
                    changeCollectLocalState(position, cid);
                    break;
                case R.id.layout_islike:
                    Intent intent1 = new Intent(AppConfig.RECEIVER_CHANGE_LIKE_MYCOLLECT);
                    intent1.putExtra("cid",cid);
                    v.getContext().sendBroadcast(intent1);
                    changeLikeNetState(position);
                    changeLikeLocalState(position, cid);
                    break;
            }
        }
    }

}
