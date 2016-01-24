package com.itspeed.naidou.app.fragment.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
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
import com.itspeed.naidou.app.util.ReleaseResource;
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

import java.util.ArrayList;

/**
 * Created by jafir on 10/19/15.
 * 吃的的详情fragment
 * 里面是用一个webview来展示的网页
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
    private ImageView mIsLike;
    private ImageView mIsCollect;
    private TextView mUsername;
    private TextView mTime;
    private TextView mTitle;
    private TextView mDesc;
    private TextView mLikes;
    private TextView mCollects;
//    private GridView mGridview;

    private GridLayout mGridLayout;
    private ListView mListView;
    private ListViewAdapter mListAdapter;

    private ArrayList<FoodMaterial> mFoodMaterialData = new ArrayList<>();
    private ArrayList<Step> mStepData = new ArrayList<>();

    private String[] mStepChinese = new String[]{"一","二","三","四","五","六","七","八","九","十","十一","十三","十四"};
    private String[] mStepEnglish = new String[]{"ONE","TWO","THREE","FOUR","FIVE","SIX","SEVEN","EIGHT","NINE","TEN","ELEVEN","TWELVE","THIRTEEN","FOURTEEN"};
    private boolean isLike;
    private boolean isCollect;
    private int  likes;
    private int  collects;


    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layout = View.inflate(aty, R.layout.frag_chide_detail,null);
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
        initHead();
        mListView = (ListView) layout.findViewById(R.id.chide_detail_list);
        mListView.setClickable(false);
        mListView.addHeaderView(head);
        //请求数据 设置数据

        requestData();

        mListAdapter = new ListViewAdapter();
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mStepData != null) {
                    String[] urls = new String[mStepData.size()];
                    String[] descs = new String[mStepData.size()];
                    for (int i = 0; i < mStepData.size(); i++) {
                        urls[i] = AppContext.HOST1+mStepData.get(i).getPic().getPath();
                        descs[i] = mStepData.get(i).getDescription();
                    }
                    for (int i = 0; i < urls.length; i++) {
                        KJLoger.debug("urls"+urls[i]);
                        KJLoger.debug("descs"+descs[i]);
                    }
//                    KJLoger.debug("okpositon:"+position);
                    //因为有header所以要减去1
                    UIHelper.showPictrueScan(parent.getContext(), position-1,urls,descs);
                }
            }
        });
    }

    private void requestData(){
        NaidouApi.getCookbook(cid, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("getCookbook:"+t);
                if(Response.getSuccess(t)){
                    mLoadLayout.setVisibility(View.GONE);
                   CookBook cookBook = Response.getChideDetail(t);
                    KJLoger.debug("cookBookDetail:" + cookBook.toString());
                    setData(cookBook);
                }

            }
        });
    }

    private void setData(CookBook cookBook){
        uid = cookBook.getFromWhoId();

        new KJBitmap.Builder().imageUrl(AppContext.HOST+cookBook.getFromWhoAvatar()).view(mAvatar).display();
        new KJBitmap.Builder().imageUrl(AppContext.HOST+cookBook.getCover()).view(mCover).display();
        mTime.setText(StringUtils.friendlyTime(TimeUtil.msToDate(cookBook.getTime())));
        mLikes.setText("" + cookBook.getLikedCount());
        mCollects.setText("" + cookBook.getCollectCount());
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

        //有了数据之后就设置数据
        for (int i = 0; i < mFoodMaterialData.size(); i++) {
            View view = View.inflate(aty,R.layout.item_gridview_chide_detail_head,null);
            view.setLayoutParams(new FrameLayout.LayoutParams(DensityUtils.getScreenW(aty)/2, ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView food = (TextView) view.findViewById(R.id.item_gridview_chide_detail_head_food);
            TextView weight = (TextView) view.findViewById(R.id.item_gridview_chide_detail_head_weight);
            food.setText(mFoodMaterialData.get(i).getFood());
            weight.setText(mFoodMaterialData.get(i).getWeight());
            mGridLayout.addView(view);
        }

        mStepData = cookBook.getSteps();

        mListAdapter.notifyDataSetChanged();
    }


    private void initHead() {
        head = View.inflate(aty,R.layout.chide_detail_head,null);
        mCover = (ImageView) head.findViewById(R.id.chide_detail_head_cover);
        mAvatar = (ImageView) head.findViewById(R.id.chide_detail_head_avatar);
        View layout = head.findViewById(R.id.chide_detail_head_layout_like_collect);
        mIsLike = (ImageView) layout.findViewById(R.id.layout_islike);
        mIsCollect = (ImageView) layout.findViewById(R.id.layout_iscollect);
        mLikes = (TextView) layout.findViewById(R.id.layout_likes);
        mCollects = (TextView) layout.findViewById(R.id.layout_collects);
        mUsername = (TextView) head.findViewById(R.id.chide_detail_head_username);
        mTitle = (TextView) head.findViewById(R.id.chide_detail_head_title);
        mTime = (TextView) head.findViewById(R.id.chide_detail_head_time);
        mDesc = (TextView) head.findViewById(R.id.chide_detail_head_desc);
        mGridLayout = (GridLayout) head.findViewById(R.id.chide_detail_head_gridlayout);
        mIsLike.setOnClickListener(this);
        mIsCollect.setOnClickListener(this);
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

        if(cid == null){
            return;
        }
        switch (v.getId()){
            case R.id.chide_detail_head_avatar:
                UIHelper.showZone(aty,uid);
                break;
            case R.id.layout_islike:
                if(RightsManager.isVisitor(v.getContext())) {
                    return;
                }
                isLike = !isLike;
                //请求点赞或者取消点赞
                if(isLike){
                    doLike(cid);
                    mIsLike.setSelected(true);
                    //点赞数改变
                    likes++;
                    mLikes.setText(""+likes);

                }else {
                    cancelLike(cid);
                    mIsLike.setSelected(false);
                    //点赞数改变
                    likes--;
                    mLikes.setText(""+likes);
                }
                break;
            case R.id.layout_iscollect:
                if(RightsManager.isVisitor(v.getContext())) {
                    return;
                }
                isCollect = !isCollect;
                //请求点赞或者取消点赞
                if(isCollect){
                    doCollect(cid);
                    //收藏数改变
                    collects++;
                    mIsCollect.setSelected(true);
                    mCollects.setText(""+collects);
                }else {
                    cancelCollect(cid);
                    mIsCollect.setSelected(false);
                    collects--;
                    mCollects.setText(""+collects);
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
        ReleaseResource.recyclerImg(mAvatar, mCover, mIsCollect, mIsLike);
        layout= null;
        super.onDestroy();
    }

    private class ListViewAdapter extends BaseAdapter{

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
            ViewHolder holder = null;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = android.view.View.inflate(aty, R.layout.item_list_chide_detail, null);
                holder.cover = (ImageView) convertView.findViewById(R.id.item_list_chide_detail_stepimg);
                holder.desc = (TextView) convertView .findViewById(R.id.item_list_chide_detail_desc);
                holder.step_chinese = (TextView) convertView .findViewById(R.id.item_list_chide_detail_step_chinese);
                holder.step_english = (TextView) convertView .findViewById(R.id.item_list_chide_detail_step_english);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            final Step step = mStepData.get(position);
            holder.cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIHelper.showBigPicture(parent.getContext(),step.getPic().getPath());
                }
            });

            new KJBitmap.Builder().view(holder.cover).imageUrl(AppContext.HOST+step.getPic().getPath()).display();
            holder.desc.setText(step.getDescription());
            holder.step_chinese.setText("第"+mStepChinese[position]+"步");
            holder.step_english.setText("STEP "+mStepEnglish[position]);
            return convertView;
        }
    }

    private class ViewHolder{
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
