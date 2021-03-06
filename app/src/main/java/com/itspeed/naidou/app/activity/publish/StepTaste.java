package com.itspeed.naidou.app.activity.publish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.AppConstant;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;

/**
 * Created by jafir on 16/1/22.
 * 菜谱基本属性 口味选择
 */
public class StepTaste extends BasePublishActivity {

    @BindView(id = R.id.publish_proce_grid)
    private GridView mGridView;

    private int currentIndex;

    @Override
    public void setRootView() {

        setContentView(R.layout.aty_publish_proce);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvTitle.setText("口味");
        mTvRight.setVisibility(View.GONE);
        mImgConfirm.setVisibility(View.VISIBLE);

    }


    @Override
    protected void onBackClick() {
        super.onBackClick();
        this.finish();
    }

    @Override
    protected void onConfirmClick() {
        super.onConfirmClick();
        if (currentIndex == -1) {
            ViewInject.toast("请选择");
            return;
        }
        Intent intent = getIntent();
        intent.putExtra("proceIndex", currentIndex);
        this.setResult(RESULT_OK, intent);
        this.finish();
    }

    /**
     * 因为每个按钮的背景颜色是不同的
     * 而且是没有规律的，所以只能用数组
     * 组合出不同的背景
     */
    private int orderColor[] = new int[]{
            R.drawable.selector_proce_lable4, R.drawable.selector_proce_lable4,
            R.drawable.selector_proce_lable2, R.drawable.selector_proce_lable3,

            R.drawable.selector_proce_lable3, R.drawable.selector_proce_lable3,
            R.drawable.selector_proce_lable1, R.drawable.selector_proce_lable4,

            R.drawable.selector_proce_lable2, R.drawable.selector_proce_lable1,
            R.drawable.selector_proce_lable4, R.drawable.selector_proce_lable3,

            R.drawable.selector_proce_lable1, R.drawable.selector_proce_lable3,
            R.drawable.selector_proce_lable2, R.drawable.selector_proce_lable2,

            R.drawable.selector_proce_lable3, R.drawable.selector_proce_lable4,
            R.drawable.selector_proce_lable4, R.drawable.selector_proce_lable3,

            R.drawable.selector_proce_lable2, R.drawable.selector_proce_lable1,
            R.drawable.selector_proce_lable3, R.drawable.selector_proce_lable1,

            R.drawable.selector_proce_lable4, R.drawable.selector_proce_lable3,
            R.drawable.selector_proce_lable3, R.drawable.selector_proce_lable1,

            R.drawable.selector_proce_lable4, R.drawable.selector_proce_lable1
    };


    @Override
    public void initData() {
        super.initData();
        currentIndex = getIntent().getIntExtra("proceIndex", 0);
        mGridView.setNumColumns(3);
        mGridView.setAdapter(new MyProceGridViewAdapter());

    }

    private class MyProceGridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return AppConstant.taste.length;
        }

        @Override
        public Object getItem(int position) {
            return AppConstant.taste[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = View.inflate(parent.getContext(), R.layout.item_gridview_taste, null);
            final TextView textView = (TextView) view.findViewById(R.id.item_gridview_taste_lable);
            textView.setText(AppConstant.taste[position]);
            textView.setBackgroundResource(orderColor[position]);
            if(position==currentIndex){
                textView.setSelected(true);
                textView.setTextColor(getResources().getColor(R.color.white));
            }
            textView.setOnClickListener(new MyClickListener(position));
            return view;
        }
    }


    class MyClickListener implements View.OnClickListener {

        private int position;

        public MyClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            TextView textView = (TextView) v;
            textView.setSelected(!textView.isSelected());
            //如果是选中的话 就是修改子体颜色
            if(textView.isSelected()){
                textView.setTextColor(getResources().getColor(R.color.white));
            }
            //设置 index
            currentIndex = position;


            /**
             * 遍历所有的按钮
             * 如果按下的按钮不是  选中的按钮 则把状态设置为没有选中
             * 再把字体颜色设置为 默认颜色
             */
            for (int i = 0; i < mGridView.getChildCount(); i++) {
                if (!textView.toString().equals(((LinearLayout) mGridView.getChildAt(i)).getChildAt(0).toString())) {
                        ((LinearLayout) mGridView.getChildAt(i)).getChildAt(0).setSelected(false);
                    ((TextView)((LinearLayout) mGridView.getChildAt(i)).getChildAt(0)).setTextColor(getResources().getColor(R.color.normal_dark_font));
                    }
            }
        }
    }

}

