package com.itspeed.naidou.app.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;

import com.itspeed.naidou.app.activity.LoginActivity;

import org.kymjs.kjframe.ui.KJActivityStack;
import org.kymjs.kjframe.utils.KJLoger;

/**
 * Created by jafir on 15/11/30.
 */
public class OfflineReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(final Context context, Intent intent) {


        KJLoger.debug("onReceive");
        AlertDialog.Builder dialog=new AlertDialog.Builder(context);
        dialog.setTitle("Warning");
        dialog.setMessage("此账号已在别处登录，您已被强制下线");
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                KJActivityStack.create().finishAllActivity();
                Intent intent = new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.show();

    }
}
