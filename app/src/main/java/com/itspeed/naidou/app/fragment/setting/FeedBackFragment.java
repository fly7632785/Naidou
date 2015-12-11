package com.itspeed.naidou.app.fragment.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

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
public class FeedBackFragment extends TitleBarSupportFragment{

    private SimpleBackActivity aty;
    private View layout;
    @BindView(id = R.id.feedback_send,click = true)
    private ImageView mSend;
    @BindView(id= R.id.feedback_content)
    private EditText mContent;



    @Override
    protected View inflaterView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layout = View.inflate(aty, R.layout.frag_feedback,null);
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
        if(v.getId() == R.id.feedback_send){
            send();
        }
    }

    private void send() {
        String content= mContent.getText().toString().trim();
        if(content.equals("")){
            ViewInject.toast("内容不能为空哦");
            return;
        }
        NaidouApi.feedBack(content, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);

                if(Response.getSuccess(t)){
                    ViewInject.toast("感谢亲的反馈,我们一定会改进的哦");
                    aty.finish();
                }
            }
        });

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
        layout= null;
        super.onDestroy();
    }
}
