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
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.app.AppContext;
import com.itspeed.naidou.app.activity.SelectActivity;
import com.itspeed.naidou.app.activity.SimpleBackActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.fragment.TitleBarSupportFragment;
import com.itspeed.naidou.model.bean.User;
import com.squareup.picasso.Picasso;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.KJLoger;

import java.io.File;

/**
 * Created by jafir on 15/9/28.
 * 设置里面 编辑个人信息  fragment
 */
public class EditInfoFragment  extends TitleBarSupportFragment{

    private SimpleBackActivity aty;
    private View layout;
    @BindView(id = R.id.edit_portrait,click = true)
    private ImageView mAvatar;
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
    /** 图片上传后 返回来的id **/
    private int avatarId;

    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        aty = (SimpleBackActivity) getActivity();
        layout = View.inflate(aty, R.layout.frag_editinfo,null);
        onChange();
        return layout;
    }


    @Override
    protected void initData() {
        super.initData();
        setData(AppContext.user);
    }

    /**
     * 设置数据显示数据
     * @param user
     */
    private void setData(User user) {
        if(user != null) {
            //设置全局user信息
            AppContext.user = user;
            //显示数据
            Picasso.with(aty).load("http://139.129.29.84/" + user.getAvatar()).placeholder(R.mipmap.portrait).into(mAvatar);
            mNickname.setText(user.getNickname());
            mMotto.setText(user.getMotto());
            mEmail.setText(null);
        }
    }


    public void onChange() {
        setTitleType(TitleBarActivity.TitleBarType.Titlebar2);
        setBackImage(R.drawable.selector_title_back);
        setTitle("编辑信息");
        setMenuImage(R.drawable.selector_title_confirm);
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
    public void onMenuClick() {
        super.onMenuClick();
        upload();

    }

    /**
     * 上传图片（头像）   成功后获取服务器返回的图片ID
     * 然后 上传修改后的信息
     */
    private void upload() {

        File file  = new File(SelectActivity.IMG_PATH,"avatar.jpeg");
        KJLoger.debug("文件路径："+file.getAbsolutePath());
        KJLoger.debug("文件是否存在："+file.exists()+"大小："+file.length());
        NaidouApi.upload(file, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("upload:" + t);
                //解析返回来的图片在服务器的ID
                avatarId = 1;
                complete();
            }
        });




    }

    /**
     * 上传修改信息
     */
    private void complete() {
        final String nickname = mNickname.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        final String motto = mMotto.getText().toString().trim();
        NaidouApi.editInfo(AppContext.UID,nickname, email, motto,avatarId, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("complete:"+t);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO) {
            picPath = data.getStringExtra(SelectActivity.KEY_RETURN_PHOTO_PATH);
            KJLoger.debug("成功返回：picpath:"+picPath);
            Bitmap bm = BitmapFactory.decodeFile(picPath);
            if(bm !=null && mAvatar != null) {
                mAvatar.setImageBitmap(bm);
            }
        }
    }

    @Override
    public void onDestroy() {
        aty = null;
        layout = null;
        mAvatar = null;
        mEmail = null;
        mMotto = null;
        mNickname = null;
        super.onDestroy();
    }
}
