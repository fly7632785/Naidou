package com.itspeed.naidou.app.view.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itspeed.naidou.R;

/**
 * Created by jafir on 16/1/21.
 */
public class PickLableView extends RelativeLayout implements View.OnClickListener {


    private ImageView right;
    private ImageView left;
    private TextView text;

    private String[] content;
    private int index = 0;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        text.setText(content[index]);
    }

    private onPickViewListener l;

    public PickLableView(Context context) {
        super(context);
        init(context);
    }

    public PickLableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public PickLableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }




    private void init(Context context) {
        View view = View.inflate(context, R.layout.view_pick_lable, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        right = (ImageView) view.findViewById(R.id.view_pick_table_right);
        left = (ImageView) view.findViewById(R.id.view_pick_table_left);
        text = (TextView) view.findViewById(R.id.view_pick_table_text);
        right.setOnClickListener(this);
        left.setOnClickListener(this);
        view.setOnClickListener(this);
        this.addView(view);


    }


    public void setViewClickListener(onPickViewListener l) {
        this.l = l;
    }


    public  void setContent(String[] content){
        this.content = content;
        if(content.length!=0){
            text.setText(content[0]);
        }
    }

    public void setTextColor(int color){
        text.setTextColor(color);
    }

    public void setText(String s){
        text.setText(s);
    }

    public void setRightImg(int img){
        right.setImageResource(img);
    }
    public void setLeftImg(int img){
        left.setImageResource(img);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.view_pick_table_left:
                leftClick();
//                l.onLeftClick();
                break;
            case R.id.view_pick_table_right:
                rightClick();
//                l.onRightClick();
                break;
            case R.id.view_pick_table:
                if(l != null) {
                    l.onViewClick(this);
                }
                break;
        }
    }

    private void leftClick() {
        if(index>= 1 && content.length!= 0){
            index--;
            text.setText(content[index]);
        }
    }
    private void rightClick() {

        if(index < content.length-1 && content.length!= 0){
            index++;
            text.setText(content[index]);
        }
    }


    public  interface onPickViewListener {
//        void onRightClick();
//        void onLeftClick();
        void onViewClick(View view);
    }

}
