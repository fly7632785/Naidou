package com.itspeed.naidou.app.fragment.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;
import com.itspeed.naidou.app.AppContext;
import com.itspeed.naidou.app.activity.SimpleBackActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.fragment.TitleBarSupportFragment;
import com.itspeed.naidou.app.util.RightsManager;
import com.itspeed.naidou.app.util.TimeUtil;
import com.itspeed.naidou.app.util.UIHelper;
import com.itspeed.naidou.model.bean.CookBook;
import com.itspeed.naidou.model.bean.FoodMaterial;
import com.itspeed.naidou.model.bean.Step;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.DensityUtils;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.StringUtils;
import org.kymjs.kjframe.utils.SystemTool;

import java.util.ArrayList;

/**
 * Created by jafir on 10/19/15.
 * 吃的的详情fragment
 * 里面是用一个webview来展示的网页
 *
 * 详情分为两个入口 模式
 * 一个是 详情 是从网络获取数据或者从缓存
 * 一个是 预览 预览是从本地将要发布的菜谱 提取的数据
 */
public class ChideDetailFragment extends TitleBarSupportFragment {

    private SimpleBackActivity aty;
    private View layout;
    @BindView(id = R.id.load_layout)
    private RelativeLayout mLoadLayout;
    private String cid;
    private String uid;

    private View head;
    private ImageView mCover;
    private ImageView mAvatar;
//    private ImageView mIsLike;
//    private ImageView mIsCollect;

    private TextView mLike;
    private TextView mCollect;
    private LinearLayout mIsLike;
    private LinearLayout mIsCollect;
    private LinearLayout mComment;
    private LinearLayout mShare;
    private TextView mUsername;
    private TextView mTime;
    private TextView mTitle;
    private TextView mDesc;
//    private TextView mLikes;
//    private TextView mCollects;
//    private GridView mGridview;

    private GridLayout mGridLayout;
    private ListView mListView;
    private ListViewAdapter mListAdapter;

    private ArrayList<FoodMaterial> mFoodMaterialData = new ArrayList<>();
    private ArrayList<Step> mStepData = new ArrayList<>();

    //步骤的汉字数组
    private String[] mStepChinese = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十", "二十一", "二十二", "二十三", "二十四", "二十五"};
    //步骤的英文数组
    private String[] mStepEnglish = new String[]{"ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN",
            "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINETEEN", "TWENTY", "TWENTY_ONE", "TWENTY_TWO", "TWENTY_THREE", "TWEENTY_FOUR", "TWENTY_FIVE"};
    private boolean isLike;
    private boolean isCollect;
    private int likes;
    private int collects;


    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layout = View.inflate(aty, R.layout.frag_chide_detail, null);
        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aty = (SimpleBackActivity) getActivity();
        onChange();
        KJLoger.debug("chidedetail:onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        KJLoger.debug("chidedetail:onStart");
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initData() {
        super.initData();

        //获取cid
        cid = (String) aty.getBundleData().get("cid");
        CookBook cookBook = (CookBook) aty.getBundleData().get("cookbook");
        initHead();
        mListView = (ListView) layout.findViewById(R.id.chide_detail_list);
        mListView.setClickable(false);
        mListView.addHeaderView(head);

        mLike = (TextView) layout.findViewById(R.id.chide_detail_head_bottom_like_tip);
        mCollect = (TextView) layout.findViewById(R.id.chide_detail_head_bottom_collect_tip);
        mIsLike = (LinearLayout) layout.findViewById(R.id.chide_detail_head_bottom_like);
        mIsCollect = (LinearLayout) layout.findViewById(R.id.chide_detail_head_bottom_collect);
        mShare = (LinearLayout) layout.findViewById(R.id.chide_detail_head_bottom_share);
        mComment = (LinearLayout) layout.findViewById(R.id.chide_detail_head_bottom_comment);

        mIsLike.setOnClickListener(this);
        mIsCollect.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mComment.setOnClickListener(this);

        //预览功能   如果 是从本地json数据 预览
        //则不去请求数据  直接提取数据
        //规定： 如果是本地预览则是传入 cid为-1
        if (cid.equals("-1")) {
            //本地 提取json数据
            requestDataFromLocal(cookBook);
        } else {
            //网络 请求数据 设置数据
            requestData();
        }

        mListAdapter = new ListViewAdapter();
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mStepData != null) {
                    String[] urls = new String[mStepData.size()];
                    String[] descs = new String[mStepData.size()];
                    for (int i = 0; i < mStepData.size(); i++) {
                        urls[i] = AppContext.HOST1 + mStepData.get(i).getPic().getPath();
                        descs[i] = mStepData.get(i).getDescription();
                    }
                    for (int i = 0; i < urls.length; i++) {
                        KJLoger.debug("urls" + urls[i]);
                        KJLoger.debug("descs" + descs[i]);
                    }
//                    KJLoger.debug("okpositon:"+position);
                    //因为有header所以要减去1
                    UIHelper.showPictrueScan(parent.getContext(), position - 1, urls, descs);
                }
            }
        });
    }

    /**
     * 请求数据
     * 首先检查是否有网络
     * 有：从网络获取数据
     * 没有：从本地缓存获取
     */
    private void requestData() {

        if (!SystemTool.checkNet(aty)) {
            String data = getFromLocal("localChideDetail", "localchide" + cid + ".txt");
            if (data != null && !data.equals("")) {
                setData(data);
            }

        } else {
            NaidouApi.getCookbook(cid, new HttpCallBack() {
                @Override
                public void onSuccess(String t) {
                    super.onSuccess(t);
                    setData(t);
                    writeToLocal(t, "localChideDetail", "localchide" + cid + ".txt");
                }
            });
        }


    }


    private void setData(String t) {
        KJLoger.debug("getCookbook:" + t);
        if (Response.getSuccess(t)) {
            mLoadLayout.setVisibility(View.GONE);
            CookBook cookBook = Response.getChideDetail(t);
            KJLoger.debug("cookBookDetail:" + cookBook.toString());
            setData(cookBook);
        }
    }


    /**
     * 从本地获取数据 预览模式
     * @param cookBook
     */
    private void requestDataFromLocal(CookBook cookBook) {
        mLoadLayout.setVisibility(View.GONE);
        KJLoger.debug("loacal cookBookDetail:" + cookBook.toString());
        setLocalData(cookBook);
    }


    /**
     * 设置网络数据
     * @param cookBook
     */
    private void setData(CookBook cookBook) {
        uid = cookBook.getFromWhoId();

        new KJBitmap.Builder().imageUrl(AppContext.HOST + cookBook.getFromWhoAvatar()).view(mAvatar).display();
        new KJBitmap.Builder().imageUrl(AppContext.HOST + cookBook.getCover()).view(mCover).display();
        mTime.setText(StringUtils.friendlyTime(TimeUtil.msToDate(cookBook.getTime())));
//        mLikes.setText("" + cookBook.getLikedCount());
//        mCollects.setText("" + cookBook.getCollectCount());
        mTitle.setText(cookBook.getTitle());
        mDesc.setText(cookBook.getDescription());
        mUsername.setText(cookBook.getFromWho());

        //食材
        mFoodMaterialData = cookBook.getFoods();
        isCollect = cookBook.isCollect();
        isLike = cookBook.isLike();
        collects = cookBook.getCollectCount();
        likes = cookBook.getLikedCount();

        mIsLike.setSelected(isLike);
        mIsCollect.setSelected(isCollect);
        mLike.setTextColor(isLike ? getResources().getColor(R.color.chide_detail_tip) : getResources().getColor(R.color.chide_detail_tip_normal));
        mCollect.setTextColor(isCollect ? getResources().getColor(R.color.chide_detail_tip) : getResources().getColor(R.color.chide_detail_tip_normal));

        //有了数据之后就设置数据
        for (int i = 0; i < mFoodMaterialData.size(); i++) {
            View view = View.inflate(aty, R.layout.item_gridview_chide_detail_head, null);
            view.setLayoutParams(new FrameLayout.LayoutParams(DensityUtils.getScreenW(aty) / 2, ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView food = (TextView) view.findViewById(R.id.item_gridview_chide_detail_head_food);
            TextView weight = (TextView) view.findViewById(R.id.item_gridview_chide_detail_head_weight);
            food.setText(mFoodMaterialData.get(i).getFood());
            weight.setText(mFoodMaterialData.get(i).getWeight());
            mGridLayout.addView(view);
        }

        mStepData = cookBook.getSteps();

//        mListAdapter.notifyDataSetChanged();
    }


    /**
     * 设置本地的数据  是为预览模式而生的
     * @param cookBook
     */
    private void setLocalData(CookBook cookBook) {

//        mLikes.setVisibility(View.GONE);
//        mCollects.setVisibility(View.GONE);
//        mIsLike.setVisibility(View.GONE);
//        mIsCollect.setVisibility(View.GONE);


        new KJBitmap.Builder().imageUrl(AppContext.userAvatarPath).view(mAvatar).display();
        new KJBitmap.Builder().imageUrl(AppContext.HOST + cookBook.getCoverPic().getPath()).view(mCover).display();
        mTime.setText(StringUtils.friendlyTime(TimeUtil.currentDate()));
//        mLikes.setText("" + cookBook.getLikedCount());
//        mCollects.setText("" + cookBook.getCollectCount());
        mTitle.setText(cookBook.getTitle());
        mDesc.setText(cookBook.getDescription());
        mUsername.setText(AppContext.user.getNickname());

        //食材
        mFoodMaterialData = cookBook.getFoods();
//        isCollect = cookBook.isCollect();
//        isLike = cookBook.isLike();
//        collects = cookBook.getCollectCount();
//        likes = cookBook.getLikedCount();

//        mIsLike.setSelected(isLike);
//        mIsCollect.setSelected(isCollect);

        //有了数据之后就设置数据
        for (int i = 0; i < mFoodMaterialData.size(); i++) {
            View view = View.inflate(aty, R.layout.item_gridview_chide_detail_head, null);
            view.setLayoutParams(new FrameLayout.LayoutParams(DensityUtils.getScreenW(aty) / 2, ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView food = (TextView) view.findViewById(R.id.item_gridview_chide_detail_head_food);
            TextView weight = (TextView) view.findViewById(R.id.item_gridview_chide_detail_head_weight);
            food.setText(mFoodMaterialData.get(i).getFood());
            weight.setText(mFoodMaterialData.get(i).getWeight());
            mGridLayout.addView(view);
        }

        mStepData = cookBook.getSteps();

//        mListAdapter.notifyDataSetChanged();
    }


    /**
     * 初始化 list的 header
     * <p/>
     * 除了 步骤 是list的item
     * 其他的都基本包含在head里面
     */
    private void initHead() {
        head = View.inflate(aty, R.layout.chide_detail_head, null);
        head.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mCover = (ImageView) head.findViewById(R.id.chide_detail_head_cover);
        mAvatar = (ImageView) head.findViewById(R.id.chide_detail_head_avatar);
//        View layout = head.findViewById(R.id.chide_detail_head_layout_like_collect);

//        mLikes = (TextView) layout.findViewById(R.id.layout_likes);
//        mCollects = (TextView) layout.findViewById(R.id.layout_collects);
        mUsername = (TextView) head.findViewById(R.id.chide_detail_head_username);
        mTitle = (TextView) head.findViewById(R.id.chide_detail_head_title);
        mTime = (TextView) head.findViewById(R.id.chide_detail_head_time);
        mDesc = (TextView) head.findViewById(R.id.chide_detail_head_desc);
        mGridLayout = (GridLayout) head.findViewById(R.id.chide_detail_head_gridlayout);
//        mIsLike.setOnClickListener(this);
//        mIsCollect.setOnClickListener(this);
        mAvatar.setOnClickListener(this);


    }

    @Override
    public void onChange() {
        super.onChange();
        setTitleType(TitleBarActivity.TitleBarType.Titlebar3);
        setBackImage(R.drawable.selector_title_back);
        setTitle("菜谱详情");
        setRightTxt("");
        setMenuImage(null);
    }

    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);

        if (cid == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.chide_detail_head_avatar:
                UIHelper.showZone(aty, uid);
                break;
            case R.id.chide_detail_head_bottom_share:
                ViewInject.toast("share");
                break;

            case R.id.chide_detail_head_bottom_comment:
                ViewInject.toast("comment");
                break;

            case R.id.chide_detail_head_bottom_like:
                if (RightsManager.isVisitor(v.getContext()) || !SystemTool.checkNet(v.getContext())) {
                    return;
                }
                isLike = !isLike;
                //请求点赞或者取消点赞
                if (isLike) {
                    doLike(cid);
                    mIsLike.setSelected(true);
                    mLike.setTextColor(getResources().getColor(R.color.chide_detail_tip));
                    //点赞数改变
                    likes++;
//                    mLikes.setText("" + likes);

                } else {
                    cancelLike(cid);
                    mIsLike.setSelected(false);
                    mLike.setTextColor(getResources().getColor(R.color.chide_detail_tip_normal));
                    //点赞数改变
                    likes--;
//                    mLikes.setText("" + likes);
                }
                break;
            case R.id.chide_detail_head_bottom_collect:
                if (RightsManager.isVisitor(v.getContext())) {
                    return;
                }
                isCollect = !isCollect;
                //请求点赞或者取消点赞
                if (isCollect) {
                    doCollect(cid);
                    //收藏数改变
                    collects++;
                    mIsCollect.setSelected(true);
                    mCollect.setTextColor(getResources().getColor(R.color.chide_detail_tip));
//                    mCollects.setText("" + collects);
                } else {
                    cancelCollect(cid);
                    mIsCollect.setSelected(false);
                    mCollect.setTextColor(getResources().getColor(R.color.chide_detail_tip_normal));
                    collects--;
//                    mCollects.setText("" + collects);
                }

                break;
        }
    }

    @Override
    public void onBackClick() {
        super.onBackClick();
//        UIHelper.showMain(aty);
        aty.finish();
    }


    @Override
    public void onDestroy() {
//        KJLoger.debug("chidedetail:onDestroy");
        aty = null;
        layout = null;
        super.onDestroy();
    }

    private class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mStepData.size();
        }

        @Override
        public Object getItem(int position) {
            return mStepData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            /**
             * 这里只限定了 25个步骤 的显示
             */
            if (position > 25) {
                return null;
            }
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = android.view.View.inflate(aty, R.layout.item_list_chide_detail, null);
                holder.cover = (ImageView) convertView.findViewById(R.id.item_list_chide_detail_stepimg);
                holder.desc = (TextView) convertView.findViewById(R.id.item_list_chide_detail_desc);
                holder.step_chinese = (TextView) convertView.findViewById(R.id.item_list_chide_detail_step_chinese);
                holder.step_english = (TextView) convertView.findViewById(R.id.item_list_chide_detail_step_english);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final Step step = mStepData.get(position);
            holder.cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIHelper.showBigPicture(parent.getContext(), step.getPic().getPath());
                }
            });

            if (cid.equals("-1")) {
                //本地 提取json数据
                new KJBitmap.Builder().view(holder.cover).imageUrl(step.getPic().getLocalPath()).display();
            } else {
                //网络 请求数据 设置数据
                new KJBitmap.Builder().view(holder.cover).imageUrl(AppContext.HOST + step.getPic().getPath()).display();
            }
            holder.desc.setText(step.getDescription());
            holder.step_chinese.setText("第" + mStepChinese[position] + "步");
            holder.step_english.setText("STEP " + mStepEnglish[position]);
            return convertView;
        }
    }

    private class ViewHolder {
        ImageView cover;
        TextView step_chinese;
        TextView step_english;
        TextView desc;

    }


    private void doCollect(String cid) {
        NaidouApi.doCollectForChide(cid, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("收藏成功：" + t);
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
        NaidouApi.cancelLikeForChide(cid, new HttpCallBack() {
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
