package com.itspeed.naidou.app.activity.publish;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.itspeed.naidou.R;
import com.itspeed.naidou.app.adapter.StepRecylerAdapter;
import com.itspeed.naidou.app.util.UIHelper;
import com.itspeed.naidou.model.bean.JsonBean.Pic;
import com.itspeed.naidou.model.bean.Step;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;

/**
 * Created by jafir on 16/1/18.
 */
public class StepAddStep extends BasePublishActivity {


    private static final int RQ_STEP = 1;
    @BindView(id  = R.id.publish_add_step_recyclerview)
    private RecyclerView mRecyclerView;

    @BindView(id = R.id.publish_add_step_more_l,click = true)
    private RelativeLayout mMoreStep;
    private ArrayList<Step> list = new ArrayList<>();
    private StepRecylerAdapter mAdapter;

    @Override
    public void setRootView() {
        setContentView(R.layout.aty_publish_add_step);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvTitle.setText("步骤");
        mTvRight.setText("完成");

    }

    @Override
    public void initData() {
        super.initData();
        Step step = new Step();
        step.setDescription("菜谱描述...");
        list.add(step);

        final LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new StepRecylerAdapter(list);
        mAdapter.setOnItemClickListener(new StepRecylerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                UIHelper.showPublishAddStepDetail(aty, position, RQ_STEP, list.get(position).getDescription(), list.get(position).getPic().getLocalPath());
            }
        });
        mAdapter.setOnItemLongClickListener(new StepRecylerAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                //delete
                AlertDialog.Builder builder = new AlertDialog.Builder(aty);
                builder.setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAdapter.delete(position);
                    }
                }).setTitle("删除吗？").create().show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }


    private void done() {
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        if (v.getId() ==  R.id.publish_add_step_more_l){
            Step step = new Step();
            step.setDescription("菜谱描述...");
            mAdapter.addData(step);
        }
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

        if(resultCode != RESULT_OK){
            return;
        }

        if(requestCode == RQ_STEP){
            KJLoger.debug("获取到本地图片");
            String path = data.getStringExtra("path");
            String desc = data.getStringExtra("desc");
            int position = data.getIntExtra("position", -1);
            Pic pic = new Pic();
            pic.setLocalPath(path);
            list.get(position).setPic(pic);
            list.get(position).setDescription(desc);
            mAdapter.refresh(position);

        }
    }
}
