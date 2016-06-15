package com.itspeed.naidou.app.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.itspeed.naidou.app.activity.LoginActivity;
import com.itspeed.naidou.app.activity.MainActivity;
import com.itspeed.naidou.app.activity.SimpleBackActivity;
import com.itspeed.naidou.app.domain.SimpleBackPage;

import org.json.JSONObject;
import org.kymjs.kjframe.utils.KJLoger;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by jafir on 15/11/30.
 */
public class MyJpushReceiver extends BroadcastReceiver {
    private static final String TAG = "MyJpushReceiver";
    private static final String LOGIN = "login";
    private static final String MAIN = "main";
    private static final String COOKBOOD_DETAIL = "cookbook_detail";

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        
        Bundle bundle = intent.getExtras();
        KJLoger.debug( "onReceive - " + intent.getAction() );

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            KJLoger.debug("JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            KJLoger.debug("接受到推送下来的自定义消息");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            KJLoger.debug( "接受到推送下来的通知");

            receivingNotification(context,bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            KJLoger.debug("用户点击打开了通知");
            openNotification(context,bundle);

        } else {
            KJLoger.debug(  "Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle){
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        KJLoger.debug(" title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        KJLoger.debug("message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        KJLoger.debug( "extras : " + extras);
    }

    private void openNotification(Context context, Bundle bundle){
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        KJLoger.debug("extras:"+extras);
        String myValue = "";
        String cid = "";
        try {
            JSONObject extrasJson = new JSONObject(extras);
            myValue = extrasJson.optString("mykey");
            //到时候 推送的时候 要按 mykey:cookbook_detail,cid:XXX的格式推送
            cid = extrasJson.optString("cid");
        } catch (Exception e) {
            KJLoger.debug("Unexpected: extras is not a valid json", e);
            return;
        }
        if (LOGIN.equals(myValue)) {
            KJLoger.debug("LOGIN: LOGIN ");
            Intent mIntent = new Intent(context, LoginActivity.class);
            mIntent.putExtras(bundle);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);
        } else if (MAIN.equals(myValue)){
            KJLoger.debug("MAIN: MAIN ");
            Intent mIntent = new Intent(context, MainActivity.class);
            mIntent.putExtras(bundle);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);
        }else if (COOKBOOD_DETAIL.equals(myValue)) {
            KJLoger.debug("COOKBOOD_DETAIL");
            Intent mIntent = new Intent(context, SimpleBackActivity.class);
            mIntent.putExtras(bundle);
            Bundle cidBundle = new Bundle();
            cidBundle.putString("cid",cid);
            KJLoger.debug("reciver cid:"+cid);
            mIntent.putExtra(SimpleBackActivity.DATA_KEY,cidBundle);
            mIntent.putExtra(SimpleBackActivity.CONTENT_KEY, SimpleBackPage.CHIDE_DETAIL.getValue());
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);
        }
    }
}
