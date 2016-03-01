package com.itspeed.naidou.app.activity;


import android.os.Bundle;
import android.view.ViewGroup;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.AppContext;
import com.squareup.picasso.Picasso;

import org.kymjs.kjframe.KJActivity;

import uk.co.senab.photoview.PhotoView;

/**
 * 大图类，用于图片的放大显示
 */
public class BigPictureActivity extends KJActivity {

    private PhotoView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setRootView() {
        String url = getIntent().getStringExtra("bigImgUrl");
        imageView = new PhotoView(this);
        imageView.setBackgroundResource(R.color.black);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //通过传递过来的 url加上域名 然后显示出来
        Picasso.with(this).load(AppContext.HOST + url).error(R.mipmap.default_bg).into(imageView);
        setContentView(imageView);
    }
}
