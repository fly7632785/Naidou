package com.itspeed.naidou.app.fragment.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.activity.SimpleBackActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.fragment.TitleBarSupportFragment;
import com.itspeed.naidou.app.util.UIHelper;
import com.itspeed.naidou.model.bean.FoodMaterial;
import com.itspeed.naidou.model.bean.Step;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.utils.DensityUtils;
import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;

/**
 * Created by jafir on 10/19/15.
 * 吃的的详情fragment
 * 里面是用一个webview来展示的网页
 */
public class ChideDetailFragment extends TitleBarSupportFragment {

    private SimpleBackActivity aty;
    private View layout;

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

    private GridViewAdapter mGridAdapter;
    private String[] mStepChinese = new String[]{"一","二","三","四","五","六","七","八","九","十","十一","十三","十四"};
    private String[] mStepEnglish = new String[]{"ONE","TWO","THREE","FOUR","FIVE","SIX","SEVEN","EIGHT","NINE","TEN","ELEVEN","TWELVE","THIRTEEN","FOURTEEN"};


    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layout = View.inflate(aty, R.layout.frag_chide_detail,null);
        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aty = (SimpleBackActivity) getActivity();
        KJLoger.debug("onCreate");
        onChange();
    }


    @Override
    protected void initData() {
        super.initData();
        KJLoger.debug("initData");

        cid = aty.getIntent().getStringExtra("cid");
        initHead();
        mListView = (ListView) layout.findViewById(R.id.chide_detail_list);
        mListView.addHeaderView(head);
        //请求数据 设置数据
        for (int i = 0; i < 10; i++) {
            mStepData.add(new Step());
        }
        mListAdapter = new ListViewAdapter();
        mListView.setAdapter(mListAdapter);
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
        mTime = (TextView) head.findViewById(R.id.chide_detail_head_time);
        mDesc = (TextView) head.findViewById(R.id.chide_detail_head_desc);
//        mGridview = (GridView) head.findViewById(R.id.chide_detail_head_gridview);
        mGridLayout = (GridLayout) head.findViewById(R.id.chide_detail_head_gridlayout);
        mIsLike.setOnClickListener(this);
        mIsCollect.setOnClickListener(this);
        mAvatar.setOnClickListener(this);

        //有了数据之后就设置数据
        for (int i = 0; i < 14; i++) {
            mFoodMaterialData.add(new FoodMaterial());
            View view = View.inflate(aty,R.layout.item_gridview_chide_detail_head,null);
            view.setLayoutParams(new FrameLayout.LayoutParams(DensityUtils.getScreenW(aty)/2, ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView food = (TextView) view.findViewById(R.id.item_gridview_chide_detail_head_food);
            TextView weight = (TextView) view.findViewById(R.id.item_gridview_chide_detail_head_weight);
//            food.setText(mFoodMaterialData.get(i).getFood());
//            weight.setText(mFoodMaterialData.get(i).getWeight());
            mGridLayout.addView(view);
        }
//        mGridAdapter = new GridViewAdapter();
//        mGridview.setAdapter(mGridAdapter);

    }

    @Override
    public void onChange() {
        super.onChange();
        setTitleType(TitleBarActivity.TitleBarType.Titlebar3);
        setBackImage(R.drawable.selector_title_back);
        setTitle("");
        setMenuImage(null);
    }

    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()){
            case R.id.chide_detail_head_avatar:
                UIHelper.showZone(aty,uid);
                break;
            case R.id.layout_islike:

                break;
            case R.id.layout_iscollect:

                break;
        }
    }

    @Override
    public void onBackClick() {
        super.onBackClick();
        aty.finish();
    }

    @Override
    public void onDestroy() {
        aty = null;
        layout= null;
        super.onDestroy();
    }

    private class GridViewAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mFoodMaterialData.size();
        }

        @Override
        public Object getItem(int position) {
            return mFoodMaterialData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(aty,R.layout.item_gridview_chide_detail_head,null);
            TextView food = (TextView) view.findViewById(R.id.item_gridview_chide_detail_head_food);
            TextView weight = (TextView) view.findViewById(R.id.item_gridview_chide_detail_head_weight);
            FoodMaterial fm = mFoodMaterialData.get(position);
//            food.setText(fm.getFood());
//            weight.setText(fm.getWeight());
            return view;
        }
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
        public View getView(int position, View convertView, ViewGroup parent) {
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

            Step step = mStepData.get(position);
            new KJBitmap.Builder().view(holder.cover).imageUrl(step.getImg()).display();
//            holder.desc.setText(step.getDescribe());
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

}
