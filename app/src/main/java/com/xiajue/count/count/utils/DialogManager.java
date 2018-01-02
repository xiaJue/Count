package com.xiajue.count.count.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.xiajue.count.count.R;

/**
 * xiaJue 2017/9/16创建
 */
public class DialogManager {

    public static void showInquiry(Context context, String title, DialogInterface.OnClickListener
            okListener, boolean... isTouchClose) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setPositiveButton(context.getString(R.string.ok), okListener);
        dialog.setNegativeButton(context.getString(R.string.cancel), null);
        if (isTouchClose != null && isTouchClose.length > 0) {
            dialog.setCancelable(isTouchClose[0]);
        }
        dialog.show();
    }

    public static void showInquiry(Context context, String title, DialogInterface
            .OnClickListener
            okListener, DialogInterface.OnClickListener cancelListener, boolean... isTouchClose) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setPositiveButton(context.getString(R.string.ok), okListener);
        dialog.setNegativeButton(context.getString(R.string.cancel), cancelListener);
        if (isTouchClose != null && isTouchClose.length > 0) {
            dialog.setCancelable(isTouchClose[0]);
        }
        dialog.show();
    }

    public static void showInquiry(Context context, String title, String[] buttonTexts,
                                   DialogInterface.OnClickListener okListener,
                                   DialogInterface.OnClickListener cancelListener, boolean...
                                           isTouchClose) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setPositiveButton(buttonTexts[0], okListener);
        dialog.setNegativeButton(buttonTexts[1], cancelListener);
        if (isTouchClose != null && isTouchClose.length > 0) {
            dialog.setCancelable(isTouchClose[0]);
        }
        dialog.show();
    }
}
