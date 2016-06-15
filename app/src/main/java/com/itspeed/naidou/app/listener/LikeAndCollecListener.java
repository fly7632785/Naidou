package com.itspeed.naidou.app.listener;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.helper.OperateHelper;
import com.itspeed.naidou.app.util.RightsManager;
import com.itspeed.naidou.model.bean.CookBook;

import java.util.List;

/**
 * Created by jafir on 16/3/2.
 * 点赞和收藏的 点击事件监听
 * 这里目前适用于 chieAdapter里面的按钮点击事件
 * 如果有特殊改动则另行复制添加处理
 */
public class LikeAndCollecListener implements View.OnClickListener {

    private BaseAdapter mAdapter;
    private int position;
    private List mDatas;
    public LikeAndCollecListener(int position, List data,BaseAdapter adapter) {
        this.mAdapter = adapter;
        this.position = position;
        this.mDatas = data;
    }

    @Override
    public void onClick(View v) {
        if (RightsManager.isVisitor(v.getContext())) {
            return;
        }
        ImageView img = (ImageView) v;
        CookBook cb = (CookBook) mDatas.get(position);
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
                changeCollectNetState(mDatas,position);
                changeCollectLocalState(mDatas,cid,mAdapter);
                break;
            case R.id.layout_islike:
                changeLikeNetState(mDatas,position);
                changeLikeLocalState(mDatas,cid,mAdapter);
                break;
        }
    }

    /**
     * 这里的点赞和收藏 注意
     * 最好 分开来写
     * 分为 网络改变 和 本地改变
     * 由于网络是有时延的，我们采取的策略是
     * 网络访问和本地改变独立开来，一起进行
     * 这样 代码执行下去立马就能看到效果
     *
     * @param position adapter里面的position
     */


    public static void changeLikeNetState(List mDatas,int position) {
        CookBook cb = (CookBook) mDatas.get(position);
        String cid = cb.getCid();
        if (!cb.isLike()) {
            OperateHelper.doLike(cid);
        } else {
            OperateHelper.cancelLike(cid);
        }
    }

    public static void changeCollectNetState(List mDatas,int position) {
        CookBook cb = (CookBook) mDatas.get(position);
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
     * @param ocid 原来的cid就是传入的cid看看是否有与 mData里面的cid匹配  有就说明是同一个 可以操作数据
     */
    public static  void changeLikeLocalState(List mDatas, String ocid,BaseAdapter mAdapter) {
        int position = 0;
        /**
         * 通过cid找出对应的position
         */
        for (int i = 0; i < mDatas.size(); i++) {
            CookBook cb = (CookBook) mDatas.get(i);
            if(cb.getCid().equals(ocid)){
                position = i;
            }
        }
        CookBook cb = (CookBook) mDatas.get(position);
        String cid = cb.getCid();
        if (!ocid.equals(cid)) {
            return;
        }
        //本地改变状态
        cb.setIsLike(!cb.isLike());
        //网络数据改变状态
        //请求点赞或者取消点赞
        if (cb.isLike()) {
            //点赞数改变
            cb.setLikedCount(cb.getLikedCount() + 1);
        } else {
            //点赞数改变
            cb.setLikedCount(cb.getLikedCount() - 1);
        }
        mAdapter.notifyDataSetChanged();
    }

    public static void changeCollectLocalState(List mDatas,String ocid,BaseAdapter mAdapter) {
        int position = 0;
        /**
         * 通过cid找出对应的position
         */
        for (int i = 0; i < mDatas.size(); i++) {
            CookBook cb = (CookBook) mDatas.get(i);
            if(cb.getCid().equals(ocid)){
                position = i;
            }
        }
        CookBook cb = (CookBook) mDatas.get(position);
        String cid = cb.getCid();
        if (!ocid.equals(cid)) {
            return;
        }
        //本地改变状态
        cb.setIsCollect(!cb.isCollect());
        //网络数据改变状态
        //请求点赞或者取消点赞
        if (cb.isCollect()) {
            //收藏数改变
            cb.setCollectCount(cb.getCollectCount() + 1);
        } else {
            cb.setCollectCount(cb.getCollectCount() - 1);
        }
        mAdapter.notifyDataSetChanged();
    }
}
