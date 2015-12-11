package com.itspeed.naidou.app.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.itspeed.naidou.app.AppContext;
import com.itspeed.naidou.app.activity.LoginActivity;

import org.kymjs.kjframe.ui.KJActivityStack;

/**
 * Created by jafir on 15/11/30.
 */
public class RightsManager {


    public static boolean isVisitor(final Context context) {
        if (AppContext.isVisitor) {
            alertToLogin(context);
            return true;
        }
        return false;
    }

    public static void alertToLogin(final Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("温馨提示");
        dialog.setMessage("享受更好的服务，赶紧登录吧！");
        dialog.setCancelable(false);
        dialog.setPositiveButton("这就去", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                KJActivityStack.create().finishAllActivity();
                Intent intent = new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        dialog.setNegativeButton("再看看", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }


}
