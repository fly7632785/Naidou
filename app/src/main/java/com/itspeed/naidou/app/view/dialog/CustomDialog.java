package com.itspeed.naidou.app.view.dialog;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by jafir on 15/10/13.
 */
public class CustomDialog extends Dialog {


    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    protected CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
