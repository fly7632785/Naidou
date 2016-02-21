package com.itspeed.naidou.app.activity.publish;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;
import com.itspeed.naidou.app.activity.SelectActivity;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.StringUtils;

import java.io.File;

/**
 * Created by jafir on 16/1/18.
 */
public class StepAddStepDetail extends BasePublishActivity {


    private static final int TO_SELECT_PHOTO = 2;
    @BindView(id = R.id.publish_add_step_detail_img, click = true)
    private ImageView mImg;
    @BindView(id = R.id.publish_add_step_detail_desc)
    private TextView mDesc;
    private int position;
    private String picPath;
    private boolean hasImg = false;
    private boolean isUpload = false;
    private String desc;
    private String path = "0";
    private int id = -1;

    @Override
    public void setRootView() {
        setContentView(R.layout.aty_publish_add_step_detail);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvTitle.setText("详细步骤");
        mTvRight.setText("完成");

    }

    @Override
    public void initData() {
        super.initData();
        position = getIntent().getIntExtra("position", 0);
        desc = getIntent().getStringExtra("desc");
        picPath = getIntent().getStringExtra("path");
        if(!desc.equals("菜谱描述...")) {
            mDesc.setText(desc);
        }
        Bitmap bm = BitmapFactory.decodeFile(picPath);
        if (bm != null && mImg != null && !picPath.equals("0")) {
            hasImg = true;
            mImg.setImageBitmap(bm);
        }
    }




    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        if(v.getId() == R.id.publish_add_step_detail_img){
            toSelectPhoto();
        }
    }


    /**
     * 这里去跳转到 选择图片的对话框
     */
    private void toSelectPhoto() {
        Intent intent = new Intent(aty, SelectActivity.class);
        intent.putExtra(SelectActivity.KEY_PHOTO_NAME, "step"+position+".jpeg");
        intent.putExtra(SelectActivity.KEY_X_RATE, 4);//x比例
        intent.putExtra(SelectActivity.KEY_Y_RATE, 3);//y比例
        intent.putExtra(SelectActivity.KEY_WIDTH, 640);//宽
        intent.putExtra(SelectActivity.KEY_HEIGHT, 480);//高
        startActivityForResult(intent, TO_SELECT_PHOTO);
    }

    @Override
    protected void onBackClick() {
        super.onBackClick();
        //退出
        this.finish();
    }


    @Override
    protected void onRightTextClick() {
        super.onRightTextClick();
        done();
    }

    private void done() {
        if(isUpload){
            return;
        }
        Intent intent = getIntent();
        intent.putExtra("position",position);
        if (hasImg) {
            intent.putExtra("id",id);
            intent.putExtra("path",path);
            intent.putExtra("localPath", picPath);
        }else {
            intent.putExtra("id",-1);
            intent.putExtra("path","0");
            intent.putExtra("localPath", "0");
        }
        if (!StringUtils.isEmpty(mDesc.getText().toString())){
            intent.putExtra("desc",mDesc.getText().toString());
        }
        if(!hasImg && StringUtils.isEmpty(mDesc.getText().toString())){
            ViewInject.toast("图片和描述至少有一个");
            return;
        }
        setResult(RESULT_OK, intent);
        this.finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO) {
             picPath = data.getStringExtra(SelectActivity.KEY_RETURN_PHOTO_PATH);
            Bitmap bm = BitmapFactory.decodeFile(picPath);
            if (bm != null && mImg != null) {
                uploadPic(bm);
                hasImg = true;
            }
        }
    }


    private void uploadPic(final Bitmap bm) {
        isUpload = true;
        File file = new File(SelectActivity.IMG_PATH, "step"+position+".jpeg");
        KJLoger.debug("文件路径：" + file.getAbsolutePath());
        KJLoger.debug("文件是否存在：" + file.exists() + "大小：" + file.length());
        NaidouApi.upload(file, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                KJLoger.debug("upload:" + t);
                if (Response.getSuccess(t)) {
                    id = Response.getPictureId(t);
                    path = Response.getPictureUrl(t);
                    //// TODO: 16/1/23
                    isUpload = false;
                    mImg.setImageBitmap(bm);
                }
            }
        });
    }

}
