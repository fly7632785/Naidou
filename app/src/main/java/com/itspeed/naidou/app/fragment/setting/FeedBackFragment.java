package com.itspeed.naidou.app.fragment.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.api.NaidouApi;
import com.itspeed.naidou.api.Response;
import com.itspeed.naidou.app.activity.SimpleBackActivity;
import com.itspeed.naidou.app.activity.TitleBarActivity;
import com.itspeed.naidou.app.fragment.TitleBarSupportFragment;

import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;

/**
 * Created by jafir on 15/9/28.
 * 设置里面 意见反馈  fragment
 */
public class FeedBackFragment extends TitleBarSupportFragment {

    private SimpleBackActivity aty;
    private View layout;
    @BindView(id = R.id.feedback_send, click = true)
    private ImageView mSend;
    @BindView(id = R.id.feedback_content)
    private EditText mContent;
    @BindView(id = R.id.feedback_joingroup, click = true)
    private TextView mJoinGroup;


    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layout = View.inflate(aty, R.layout.frag_feedback, null);
        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aty = (SimpleBackActivity) getActivity();
        onChange();
    }

    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
        if (v.getId() == R.id.feedback_send) {
            send();
        } else if (v.getId() == R.id.feedback_joingroup) {
            joinQQGroup("L-rONp1ZfP-Dj5TTfI6OnMnCf56pXcwk");
        }
    }

    private void send() {
        String content = mContent.getText().toString().trim();
        if (content.equals("")) {
            ViewInject.toast("内容不能为空哦");
            return;
        }
        NaidouApi.feedBack(content, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);

                if (Response.getSuccess(t)) {
                    ViewInject.toast("感谢亲的反馈,我们一定会改进的哦");
                    aty.finish();
                }
            }
        });


    }

    /****************
     * 发起添加群流程。群号：&quot;奶豆母婴&quot; 用户反馈群(545576196) 的 key 为： L-rONp1ZfP-Dj5TTfI6OnMnCf56pXcwk
     * 调用 joinQQGroup(L-rONp1ZfP-Dj5TTfI6OnMnCf56pXcwk) 即可发起手Q客户端申请加群 &quot;奶豆母婴&quot; 用户反馈群(545576196)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            ViewInject.toast("未安装手Q或安装的版本不支持");
            return false;
        }
    }


    @Override
    public void onChange() {
        super.onChange();
        setTitleType(TitleBarActivity.TitleBarType.Titlebar2);
        setBackImage(R.drawable.selector_title_back);
        setTitle("意见反馈");
        setMenuImage(null);
    }


    @Override
    public void onBackClick() {
        super.onBackClick();
        aty.finish();
    }

    @Override
    public void onDestroy() {
        aty = null;
        layout = null;
        super.onDestroy();
    }
}
