package com.itspeed.naidou.app.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;
import com.itspeed.naidou.app.AppContext;
import com.itspeed.naidou.app.activity.MainActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.fragment.TitleBarSupportFragment;
import com.itspeed.naidou.app.util.RightsManager;
import com.itspeed.naidou.app.helper.UIHelper;
import com.itspeed.naidou.model.bean.User;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.SystemTool;

/**
 * Created by jafir on 15/9/1.
 * 我的 fragment
 */
public class WodeFragment extends TitleBarSupportFragment {

    private static final int REQUEST_PICK_PHOTO = 111;
    MainActivity aty;
    private View view;


    @BindView(id = R.id.wode_cookbook_layout, click = true)
    private LinearLayout ly_mycookbook;
    //    @BindView(id = R.id.wode_message_layout,click = true)
//    private LinearLayout ly_myMessage;
    @BindView(id = R.id.wode_collect_layout, click = true)
    private LinearLayout ly_myCollect;
    @BindView(id = R.id.wode_follow_layout, click = true)
    private LinearLayout ly_follow;

    @BindView(id = R.id.wode_portrait, click = true)
    private ImageView mAvatar;
    @BindView(id = R.id.wode_count_of_naidou)
    private TextView mCoins;
    @BindView(id = R.id.wode_count_of_follow)
    private TextView mFollow;
    @BindView(id = R.id.wode_nickname)
    private TextView mNickname;
    @BindView(id = R.id.wode_motto)
    private TextView mMotto;

    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        aty = (MainActivity) getActivity();
        view = View.inflate(aty, R.layout.frag_wode, null);
        onChange();
        return view;
    }

    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);

        /**
         * 检测是否是游客模式 排除非用户点击
         */
        if (RightsManager.isVisitor(aty)) {
            return;
        }

        switch (v.getId()) {

            case R.id.wode_cookbook_layout:

                UIHelper.showMyCookbook(aty);

                break;
            case R.id.wode_collect_layout:

                UIHelper.showMyCollect(aty);
                break;
//            case R.id.wode_message_layout:
//                break;

            case R.id.wode_follow_layout:
                UIHelper.showFollow(aty);
                break;

            case R.id.wode_portrait:
                ViewInject.toast("请在设置里面编辑个人资料哦");
//                UIHelper.showEditInfo(this,1);
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
//        requestData();
    }

    @Override
    protected void initData() {
        super.initData();
        //一开始AppStart的时候 获取了本地的用户信息 保存到了 APPLICATION 里面
        //从全局user里面获取 显示信息
        setUserInfo(AppContext.user);

    }


    /**
     * 每次回来都去更新数据 看看是否如奶否关注人数等变化
     */
    @Override
    public void onStart() {
        super.onStart();
        requestData();
    }

    /**
     * 请求数据
     * 首先检查是否有网络
     * 有：从网络获取数据
     * 没有：从本地缓存获取
     */
    public void requestData() {
        if (!SystemTool.checkNet(aty)) {
            String data = getFromLocal("localUserInfo", "localUserInfo.txt");
            KJLoger.debug("localdataUser:" + data);
            if (data != null && !data.equals("")) {
                setData(data);
            }
        } else {
            NaidouApi.getMyInfo(new HttpCallBack() {
                @Override
                public void onSuccess(String t) {
                    super.onSuccess(t);
                    KJLoger.debug("getmyInfo:" + t);
                    setData(t);
                    writeToLocal(t,"localUserInfo", "localUserInfo.txt");
                }
            });
        }
    }



    /**
     * 设置数据显示数据
     */

    private void setData(String t) {
        if (Response.getSuccess(t)) {
            User user = Response.getUserInfo(t);
            KJLoger.debug("user:" + user.toString());
            setUserInfo(user);
        }
    }

    /**
     * 设置显示user用户信息
     * @param user
     */
    private void setUserInfo(User user) {
        if (user != null) {
            //设置全局user信息
            AppContext.user = user;
            //显示数据
            //头像     本地的avatar.jpeg
            //设置 头像缓存时间30天
//            HttpConfig httpConfig = new HttpConfig();
//            httpConfig.cacheTime = 30*3600;
//            new KJBitmap.Builder().imageUrl(AppContext.HOST + user.getAvatar()).view(mAvatar).errorBitmapRes(R.mipmap.portrait).display(new KJBitmap(httpConfig,new BitmapConfig()));
//            KJLoger.debug("cachepath:" + HttpConfig.CACHEPATH + "");
//            KJLoger.debug("cachetime:" + httpConfig.cacheTime);
            new KJBitmap().displayWithErrorBitmap(mAvatar, AppContext.userAvatarPath, R.mipmap.default_avatar);
            mCoins.setText(user.getCoins() + "");
            mFollow.setText(user.getFollowCount() + "");
            mNickname.setText(user.getNickname());
            mMotto.setText(user.getMotto());
        }
    }


//    /**
//     * 写入
//     * @param t
//     */
//    private void writeToLocal(String t) {
//
//        //只储存  最新一页的缓存数据
//        File file = FileUtils.getSaveFile("localUserInfo", "localUserInfo.txt");
//        FileWriter writer = null;
//        try {
//            writer = new FileWriter(file);
//            writer.write(t.trim());
//            writer.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//    private String getFromLocal() {
//        File file = FileUtils.getSaveFile("localUserInfo", "localUserInfo.txt");
//        char[] buffer = new char[1024];
//        StringBuilder builder = new StringBuilder();
//        String data;
//        BufferedReader br = null;
//        FileReader fileReader = null;
//        try {
//            fileReader = new FileReader(file);
//            br = new BufferedReader(fileReader);
//            try {
//                while ((data = br.readLine()) != null) {
//                    builder.append(data);
//                }
//                return builder.toString();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fileReader.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }

    @Override
    public void onMenuClick() {
        super.onMenuClick();
        UIHelper.showSetting(aty);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 0) {
                //成功
                KJLoger.debug("onActivityResult:");
                requestData();
            }
        }
    }
}


