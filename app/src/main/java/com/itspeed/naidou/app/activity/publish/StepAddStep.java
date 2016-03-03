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
import com.itspeed.naidou.app.manager.ActivityManager;
import com.itspeed.naidou.app.helper.UIHelper;
import com.itspeed.naidou.model.bean.JsonBean.Pic;
import com.itspeed.naidou.model.bean.Step;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.KJActivityStack;
import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by jafir on 16/1/18.
 * 添加步骤
 * <p/>
 * 进入有两种模式
 * 1、直接进入 添加
 * 2、从总览进入 修改 如果是这种要判断是否为从总览进入 然后进行不同的操作模式
 * 进入之后都会去检查本地数据是否存在 如果存在则要填充数据
 */
public class StepAddStep extends BasePublishActivity {


    private static final int RQ_STEP = 1;
    @BindView(id = R.id.publish_add_step_recyclerview)
    private RecyclerView mRecyclerView;

    @BindView(id = R.id.publish_add_step_more_l, click = true)
    private RelativeLayout mMoreStep;
    private ArrayList<Step> list = new ArrayList<>();
    private StepRecylerAdapter mAdapter;
    private boolean isModify;

    @Override
    public void setRootView() {
        setContentView(R.layout.aty_publish_add_step);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvTitle.setText("步骤");
        mTvRight.setText("完成");
        //是否 需要修改  因为如果从总览里面进入 则为修改模式
        isModify = getIntent().getBooleanExtra("isModify", false);

    }

    @Override
    public void initData() {
        super.initData();
        if (cookBook.getSteps().size() != 0) {
            list = cookBook.getSteps();
        } else {
            Step step = new Step();
            step.setDescription("菜谱描述...");
            list.add(step);

        }
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new StepRecylerAdapter(list);
        mAdapter.setOnItemClickListener(new StepRecylerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                KJLoger.debug("click:" + position);
                UIHelper.showPublishAddStepDetail(aty, position, RQ_STEP, list.get(position).getDescription(), list.get(position).getPic().getLocalPath());
            }
        });
        mAdapter.setOnItemLongClickListener(new StepRecylerAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                KJLoger.debug("longClick:" + position);
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


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        if (v.getId() == R.id.publish_add_step_more_l) {
            Step step = new Step();
            step.setDescription("菜谱描述...");
            mAdapter.addData(step);
            mRecyclerView.scrollToPosition(list.size() - 1);
        }
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

        /**
         * 这里不能动态的 根据list的size作为 循环边界，因为 如果remove了
         * size就会变
         * 所以 只能用iterator
         */
//        KJLoger.debug("list："+list.toString());

        Iterator<Step> iterator = list.iterator();
        while (iterator.hasNext()) {
            Step step = iterator.next();
            if ((step.getPic().getLocalPath().equals("0") || step.getPic().getPath().equals("0"))
                    && step.getDescription().equals("菜谱描述...")
                    ) {
                iterator.remove();
            }
        }
//        KJLoger.debug("listsieze:"+list.size());
        cookBook.setSteps(list);
        setCookbook(cookBook);
        getCookbook();
        mAdapter.notifyDataSetChanged();
        if (!isModify) {
            UIHelper.showPublishAll(this);
            KJLoger.debug("duos:" + KJActivityStack.create().getCount());
            // TODO: 16/3/1 这里需要把前面的三个 activity给删除掉
            ActivityManager.getScreenManager().popAllActivityExceptOne(StepAddStep.class);
//            ViewInject.toast(ActivityManager.getScreenManager().getCount()+"ge");
        }
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        /**
         * 这里是 每次添加图片之后 是否成功的返回
         * 如果成功 则填充 图片然后更新listview
         */
        if (requestCode == RQ_STEP) {
//            KJLoger.debug("获取到本地图片");
            String localPath = data.getStringExtra("localPath");
            String path = data.getStringExtra("path");
            int id = data.getIntExtra("id", -1);
            String desc = data.getStringExtra("desc");
            int position = data.getIntExtra("position", -1);
            Pic pic = new Pic();
            pic.setPath(path);
            pic.setId(id);
            pic.setLocalPath(localPath);
//            KJLoger.debug("llll"+list.toString());
            list.get(position).setPic(pic);
            list.get(position).setDescription(desc);
            mAdapter.refresh(position);

        }
    }
}
