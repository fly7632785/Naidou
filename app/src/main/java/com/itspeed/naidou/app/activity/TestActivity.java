package com.itspeed.naidou.app.activity;

import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.itspeed.naidou.R;
import com.itspeed.naidou.model.bean.CookBook;
import com.itspeed.naidou.model.bean.JsonBean.CookbookListData;
import com.itspeed.naidou.model.bean.JsonBean.Entity;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

import java.util.ArrayList;

/**
 * Created by jafir on 11/2/15.
 */
public class TestActivity extends KJActivity{

    @BindView(id = R.id.test_text)
    private TextView textView;
    @BindView(id = R.id.textView2)
    private TextView textView2;



    String jsos = "{\"status\":true,\"isSuccess\":true,\"errorCode\":0,\"message\":\"获取吃的成功\",\"data\":{\"pages\":1,\"currentPage\":1,\"count\":10,\"list\":[{\"cId\":1,\"title\":\"标题\",\"time\":\"12213132\",\"isLike\":true,\"isCollect\":true,\"cover720\":\"url\"},{\"cId\":1,\"title\":\"标题\",\"time\":\"12213132\",\"isLike\":true,\"isCollect\":true,\"cover720\":\"url\"},{\"cId\":1,\"title\":\"标题\",\"time\":\"12213132\",\"isLike\":true,\"isCollect\":true,\"cover720\":\"url\"}]}}";
    String jsod = "{\"status\":true,\"isSuccess\":true,\"errorCode\":0,\"message\":\"获取聊的成功\",\"data\":{\"pages\":1,\"currentPage\":1,\"count\":10,\"list\":[{\"title\":\"标题\",\"时间\":\"123123\",\"conten\":\"内容\",\"countOfScan\":123123},{\"title\":\"标题\",\"时间\":\"123123\",\"conten\":\"内容\",\"countOfScan\":123123},{\"title\":\"标题\",\"时间\":\"123123\",\"conten\":\"内容\",\"countOfScan\":123123}]}}";


    private String cb ="{\"cId\":1,\"title\":\"标题\",\"time\":\"12213132\",\"isLike\":true,\"isCollect\":true,\"cover720\":\"url\"}";
    @Override
    public void setRootView() {
        setContentView(R.layout.aty_test);
    }


    @Override
    public void initData() {
        super.initData();

        //获取返回数据的基础类型  通用属性都有  data属性还是还是json 如果需要解析 再次解析
        Entity entity = JSON.parseObject(jsos,Entity.class);
        //继续解析data  data里面有pages currentPage count maxCount  还有list数组
        CookbookListData data1 = JSON.parseObject(entity.getData().toString(), CookbookListData.class);
        ArrayList<CookBook> cookBooks = data1.getList();
        CookBook cookBook1 = cookBooks.get(0);

        textView.setText(cookBook1+"\n\n"+entity.toString());
        textView2.setText(data1.toString()+"\n\n"+cookBooks.toString()+"\n\n");


    }



}
