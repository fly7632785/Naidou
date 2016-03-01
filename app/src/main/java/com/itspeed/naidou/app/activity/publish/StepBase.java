package com.itspeed.naidou.app.activity.publish;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;
import com.itspeed.naidou.app.activity.SelectActivity;
import com.itspeed.naidou.app.util.UIHelper;
import com.itspeed.naidou.model.bean.JsonBean.Pic;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.StringUtils;

import java.io.File;

/**
 * Created by jafir on 16/1/18.
 * 发布菜谱的基础步骤
 * 标题 描述 封面
 *
 */
public class StepBase extends BasePublishActivity {


    private static final int TO_SELECT_PHOTO = 110;
    @BindView(id = R.id.publish_base_title)
    private EditText mTitle;
    @BindView(id = R.id.publish_base_desc)
    private EditText mDesc;
    @BindView(id = R.id.publish_base_cover, click = true)
    private ImageView mCover;


    private boolean hasCover;

    private boolean isModify;
    @Override
    public void setRootView() {
        setContentView(R.layout.aty_publish_base);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvTitle.setText("菜谱");
        isModify = getIntent().getBooleanExtra("isModify",false);
        if(isModify){
            mTvRight.setText("完成");
        }
    }

    @Override
    public void initData() {
        super.initData();


        if(!cookBook.getTitle().equals("")){
            mTitle.setText(cookBook.getTitle());
        }
        if(!cookBook.getDescription().equals("")){
            mDesc.setText(cookBook.getDescription());
        }
        //如果没有封面的话 默认这个值是0
        if(!cookBook.getCoverPic().getLocalPath().equals("0")){
            Bitmap bm = BitmapFactory.decodeFile(cookBook.getCoverPic().getLocalPath());
            if (bm != null && mCover != null) {
                mCover.setImageBitmap(bm);
            }
        }
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.publish_base_cover:
                toSelectPhoto();
                break;
        }
    }

    /**
     * 这里去跳转到 选择图片的对话框
     */
    private void toSelectPhoto() {
        Intent intent = new Intent(aty, SelectActivity.class);
        intent.putExtra(SelectActivity.KEY_PHOTO_NAME, "cover.jpeg");
        intent.putExtra(SelectActivity.KEY_X_RATE, 4);//x比例
        intent.putExtra(SelectActivity.KEY_Y_RATE, 3);//y比例
        intent.putExtra(SelectActivity.KEY_WIDTH, 640);//宽
        intent.putExtra(SelectActivity.KEY_HEIGHT, 480);//高
        startActivityForResult(intent, TO_SELECT_PHOTO);
    }

    @Override
    protected void onRightTextClick() {
        super.onRightTextClick();
        done();
    }

    private void done() {
        if (StringUtils.isEmpty(mTitle.getText())) {
            ViewInject.toast("请填写菜谱名称");
            return;
        }

        cookBook.setTitle(mTitle.getText().toString());

        if (!hasCover) {
            ViewInject.toast("请选择封面");
            return;
        }


        if (StringUtils.isEmpty(mDesc.getText())) {
            ViewInject.toast("请填写菜谱描述");
            return;
        }
        cookBook.setDescription(mDesc.getText().toString());
        setCookbook(cookBook);
        if(!isModify) {
            UIHelper.showPublishBaseInfo(this);
            this.finish();
        }else {
            this.finish();
        }
//        KJLoger.debug(getCookbook().toString());
    }

    @Override
    protected void onBackClick() {
        super.onBackClick();
        //退出
        this.finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO) {
            String picPath = data.getStringExtra(SelectActivity.KEY_RETURN_PHOTO_PATH);
            Bitmap bm = BitmapFactory.decodeFile(picPath);
            if (bm != null && mCover != null) {
                uploadPic(bm,picPath);
            }
        }
    }


    private void uploadPic(final Bitmap bm, final String  localPath) {
        File file = new File(SelectActivity.IMG_PATH, "cover.jpeg");
        KJLoger.debug("文件路径：" + file.getAbsolutePath());
        KJLoger.debug("文件是否存在：" + file.exists() + "大小：" + file.length());
        NaidouApi.upload(file, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("upload:" + t);
                if (Response.getSuccess(t)) {
                    Pic pic = new Pic();
                    int id = Response.getPictureId(t);
                    String url = Response.getPictureUrl(t);
                    pic.setId(id);
                    pic.setPath(url);
                    pic.setLocalPath(localPath);
                    cookBook.setCoverPic(pic);
                    mCover.setImageBitmap(bm);
                    hasCover = true;
                }
            }
        });
    }
}
