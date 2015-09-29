package com.itspeed.naidou.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.activity.MainActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.util.UIHelper;

import org.kymjs.kjframe.ui.BindView;

/**
 * Created by jafir on 15/9/1.
 */
public class WodeFragment extends TitleBarSupportFragment{

    private static final int REQUEST_PICK_PHOTO = 111;
    MainActivity aty;
    private View view;


    @BindView(id = R.id.wode_cookbook_layout,click = true)
    private LinearLayout ly_mycookbook;
    @BindView(id = R.id.wode_message_layout,click = true)
    private LinearLayout ly_myMessage;
    @BindView(id = R.id.wode_collect_layout,click = true)
    private LinearLayout ly_myCollect;

//    @BindView(id = R.id.button,click = true)
//    private Button toChoose;
//    @BindView(id = R.id.gridView)
//    private GridView gridView;
//    @BindView(id = R.id.imageView2)
//    private ImageView imageView;
//    private ImageGridAdapter mAdaper = null;

    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        aty = (MainActivity) getActivity();
        view = View.inflate(aty, R.layout.frag_wode,null);
        onChange();
        return view;
    }

    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
//        if(v.getId() == R.id.button){
//            //***改成应用的包名
//            Intent intent = new Intent("com.itspeed.naidou.action.CHOSE_PHOTOS");
////指定图片最大选择数
//            intent.putExtra(Constant.EXTRA_PHOTO_LIMIT, 5);
//            startActivityForResult(intent, REQUEST_PICK_PHOTO);
//        }


        switch (v.getId()){
            case R.id.wode_cookbook_layout:
                UIHelper.showMyCookbook(aty);

                break;
            case R.id.wode_collect_layout:
                break;
            case R.id.wode_message_layout:
                break;
        }

    }

    @Override
    public void onChange() {
        super.onChange();
        setTitleType(TitleBarActivity.TitleBarType.Titlebar2);
        setTitle("我的");
        setBackImage(null);
        setMenuImage(R.drawable.selector_title_setting);
    }


    @Override
    protected void initData() {
        super.initData();
//                int numColumns = (getResources().getDisplayMetrics().widthPixels - DisplayUtils.dip2px(12, aty)) / DisplayUtils.dip2px(116, aty);
//        gridView.setNumColumns(numColumns);
//        mAdaper = new ImageGridAdapter(aty);
//        gridView.setAdapter(mAdaper);

//        ImageView icon = new ImageView(aty); // Create an icon
//        icon.setImageResource(R.mipmap.ic_launcher);
//        FloatingActionButton actionButton = new FloatingActionButton.Builder(aty)
//                .setContentView(icon)
//                .build();
//
//        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(aty);
//// repeat many times:
//        ImageView itemIcon = new ImageView(aty);
//        itemIcon.setImageResource(R.mipmap.icn_1);
//        SubActionButton button1 = itemBuilder.setContentView(itemIcon).build();
//        ImageView itemIcon1 = new ImageView(aty);
//        itemIcon1.setImageResource(R.mipmap.icn_2);
//        SubActionButton button2 = itemBuilder.setContentView(itemIcon1).build();
//        ImageView itemIcon2 = new ImageView(aty);
//        itemIcon2.setImageResource(R.mipmap.icn_3);
//        SubActionButton button3 = itemBuilder.setContentView(itemIcon2).build();
//        ImageView itemIcon3 = new ImageView(aty);
//        itemIcon3.setImageResource(R.mipmap.icn_4);
//        SubActionButton button4 = itemBuilder.setContentView(itemIcon3).build();
//
//        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(aty)
//                .addSubActionView(button1)
//                .addSubActionView(button2)
//                .addSubActionView(button3)
//                .addSubActionView(button4)
//                .attachTo(actionButton)
//                .build();



    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode != Activity.RESULT_OK) {
//            return;
//        }
//
//        switch (requestCode) {
//            case REQUEST_PICK_PHOTO:
//                ArrayList<String> images = data.getStringArrayListExtra(Constant.EXTRA_PHOTO_PATHS);
//                mAdaper.swapDatas(images);
//                break;
//        }
//    }


    @Override
    public void onMenuClick() {
        super.onMenuClick();
        UIHelper.showSetting(aty);
    }
}
