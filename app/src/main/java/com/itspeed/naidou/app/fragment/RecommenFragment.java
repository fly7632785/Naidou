package com.itspeed.naidou.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.activity.MainActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.adapter.ChideAdapter;
import com.itspeed.naidou.app.view.switch3d.Image3DSwitchView;
import com.itspeed.naidou.app.view.switch3d.Image3DView;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;

/**
 * Created by jafir on 15/9/1.
 */
public class RecommenFragment extends TitleBarSupportFragment {

    MainActivity aty;
    private View layout;
    private Image3DSwitchView imageSwitchView;
    private Image3DView [] image3DViews = new Image3DView[5];

    @Override
    public void onChange() {
        super.onChange();
        setTitleType(TitleBarActivity.TitleBarType.Titlebar2);
        setTitle("推荐");
        setBackImage(null);
        setMenuImage(null);
    }


    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()){
            case R.id.image1:
                ViewInject.toast("点击了1");
                break;
            case R.id.image2:
                ViewInject.toast("点击了2");
                break;
            case R.id.image3:
                ViewInject.toast("点击了3");
                break;
            case R.id.image4:
                ViewInject.toast("点击了4");
                break;
            case R.id.image5:
                ViewInject.toast("点击了5");
                break;

        }
    }

    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        aty = (MainActivity) getActivity();
        onChange();
        layout = View.inflate(aty, R.layout.frag_recommend, null);
        return layout;
    }

    @Override
    protected void initData() {
        super.initData();

        imageSwitchView = (Image3DSwitchView) layout.findViewById(R.id.image_switch_view);
        image3DViews[0] = (Image3DView) layout.findViewById(R.id.image1);
        image3DViews[1] = (Image3DView) layout.findViewById(R.id.image2);
        image3DViews[2] = (Image3DView) layout.findViewById(R.id.image3);
        image3DViews[3] = (Image3DView) layout.findViewById(R.id.image4);
        image3DViews[4] = (Image3DView) layout.findViewById(R.id.image5);

        for(int i=0;i<image3DViews.length;i++){
            image3DViews[i].setOnClickListener(this);
            new KJBitmap().display(image3DViews[i],ChideAdapter.img[i]);
        }

        imageSwitchView.setOnImageSwitchListener(new Image3DSwitchView.OnImageSwitchListener() {
            @Override
            public void onImageSwitch(int currentImage) {
                KJLoger.debug("current image is " + currentImage);
            }
        });
        imageSwitchView.setCurrentImage(1);
    }


    @Override
    public void onDestroy() {
        imageSwitchView.clear();
    }
}
