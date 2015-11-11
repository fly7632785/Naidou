package com.itspeed.naidou.app.fragment.setting;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.activity.SelectActivity;
import com.itspeed.naidou.app.activity.SimpleBackActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.fragment.TitleBarSupportFragment;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.KJLoger;

/**
 * Created by jafir on 15/9/28.
 * 设置里面 编辑个人信息  fragment
 */
public class EditInfoFragment  extends TitleBarSupportFragment{

    private SimpleBackActivity aty;
    private View layout;
    @BindView(id = R.id.edit_portrait,click = true)
    private ImageView mPortrait;
    @BindView(id = R.id.edit_motto)
    private EditText mMotto;
    @BindView(id = R.id.edit_nickname)
    private EditText mNickname;
    @BindView(id = R.id.edit_email)
    private EditText mEmail;

    /** 选择文件 */
    public static final int TO_SELECT_PHOTO = 1;
    /** 图片路径 */
    private String picPath = null;

    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layout = View.inflate(aty, R.layout.frag_editinfo,null);
        KJLoger.debug("fragment_inflaterView");
        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aty = (SimpleBackActivity) getActivity();
        KJLoger.debug("fragment_onCreate");
    }
    @Override
    public void onStart() {
        super.onStart();
        onChange();
        KJLoger.debug("fragment_onStart");
    }
    public void onChange() {
        setTitleType(TitleBarActivity.TitleBarType.Titlebar2);
        setBackImage(R.drawable.selector_title_back);
        setTitle("编辑信息");
        setMenuImage(null);
    }

    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()){
            case R.id.edit_portrait:
                Intent intent = new Intent(aty,SelectActivity.class);
                intent.putExtra(SelectActivity.KEY_PHOTO_PATH,"avatar.jpeg");
                intent.putExtra(SelectActivity.KEY_X_RATE,1);//x比例
                intent.putExtra(SelectActivity.KEY_Y_RATE,1);//y比例
                intent.putExtra(SelectActivity.KEY_WIDTH,100);//宽
                intent.putExtra(SelectActivity.KEY_HEIGHT,100);//高
                startActivityForResult(intent,TO_SELECT_PHOTO);
            break;
        }
    }

    @Override
    public void onBackClick() {
        super.onBackClick();
        aty.finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO) {
            picPath = data.getStringExtra(SelectActivity.KEY_RETURN_PHOTO_PATH);
            Bitmap bm = BitmapFactory.decodeFile(picPath);
            if(bm !=null && mPortrait != null) {
                mPortrait.setImageBitmap(bm);
            }
        }
    }


}
