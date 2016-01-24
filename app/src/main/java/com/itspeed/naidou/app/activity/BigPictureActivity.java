package com.itspeed.naidou.app.activity;


import android.os.Bundle;
import android.view.ViewGroup;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.AppContext;
import com.squareup.picasso.Picasso;

import org.kymjs.kjframe.KJActivity;

import uk.co.senab.photoview.PhotoView;

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
        Picasso.with(this).load(AppContext.HOST + url).error(R.mipmap.default_bg).into(imageView);
        setContentView(imageView);
    }
}
