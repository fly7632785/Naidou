package com.itspeed.naidou.app.activity.publish;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.itspeed.naidou.R;
import com.itspeed.naidou.model.bean.CookBook;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.utils.FileUtils;
import org.kymjs.kjframe.utils.KJLoger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by jafir on 16/1/18.
 *
 * 菜谱发布的基类
 *
 */
public abstract class BasePublishActivity extends KJActivity {


    public TextView mTvTitle;
    //titleBar 左边后退按钮
    public ImageView mImgBack;

    //titleBar 右边的text
    public TextView mTvRight;


    public ImageView mImgConfirm;

    protected CookBook cookBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        mImgBack = (ImageView) findViewById(R.id.publish_title_back);
        mImgConfirm = (ImageView) findViewById(R.id.publish_title_right_confirm);

        mTvRight = (TextView) findViewById(R.id.publish_title_right);
        mTvTitle = (TextView) findViewById(R.id.publish_title_title);

        mImgBack.setOnClickListener(this);
        mImgConfirm.setOnClickListener(this);
        mTvRight.setOnClickListener(this);

        KJLoger.debug("2222");

    }


    @Override
    public void initData() {
        super.initData();
        KJLoger.debug("4444");
        cookBook = getCookbook();
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.publish_title_back:
                onBackClick();
                break;
            case R.id.publish_title_right_confirm:
                onConfirmClick();
                break;
            case R.id.publish_title_right:
                onRightTextClick();
                break;
            default:
                break;
        }
    }




    /**
     * 在发布界面  每次都是去本地获取菜谱
     * 然后进行 步骤操作后 更新
     * 储存
     *
     * @return
     */
    protected CookBook getCookbook() {
        File file = FileUtils.getSaveFile("craft", "publish_file.txt");
        char[] buffer = new char[1024];
        StringBuilder builder = new StringBuilder();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            try {

                while (fileReader.read(buffer) != -1) {
                    builder.append(buffer);
                }

                CookBook c1 = JSON.parseObject(builder.toString().trim(), CookBook.class);
                KJLoger.debug("jsonString:" + builder.toString().trim());
                KJLoger.debug("cookbook:" + c1.toString());
                return c1;


            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 把步骤操作后的菜谱重新存到本地 整合数据
     */
    protected void setCookbook(CookBook cookbook) {

        String s = JSON.toJSONString(cookbook);
        File file = FileUtils.getSaveFile("craft", "publish_file.txt");
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(s.trim());
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * 给子类去实现，右边文字的点击（举报）
     */
    protected void onRightTextClick() {
    }

    /**
     * 左边图标的点击
     */
    protected void onBackClick() {
    }

    /**
     * 右边图标的点击
     */
    protected void onConfirmClick() {
    }


}
