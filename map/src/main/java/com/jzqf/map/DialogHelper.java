package com.jzqf.map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

public class DialogHelper {
    /**
     * 显示简单的提示框
     *
     * @param context         上下文
     * @param message         显示信息
     * @param onClickListener 弹窗点击监听
     */
    public static void showAlertDialog(Context context, String message, String negativeMsg, final DialogInterface.OnClickListener onClickListener) {
        DialogInterface.OnClickListener clickListener = (dialog, which) -> {
            dialog.dismiss();
            if (onClickListener != null) {
                onClickListener.onClick(dialog, which);
            }
        };
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("确定", clickListener);
        if (!TextUtils.isEmpty(negativeMsg)) {
            alertDialog.setNegativeButton(negativeMsg, clickListener);
        }
        alertDialog.setCancelable(true);
        alertDialog.create().show();
    }

    /**
     * 显示简单的提示框
     *
     * @param context          上下文
     * @param message          显示信息
     * @param negativeMsg      取消信息
     * @param onClickListener  弹窗点击监听
     * @param onCancelListener 弹窗取消监听
     */
    public static void showAlertDialog(Context context, String message, String negativeMsg,
                                       final DialogInterface.OnClickListener onClickListener,
                                       DialogInterface.OnCancelListener onCancelListener) {
        DialogInterface.OnClickListener clickListener = (dialog, which) -> {
            dialog.dismiss();
            if (onClickListener != null) {
                onClickListener.onClick(dialog, which);
            }
        };
        DialogInterface.OnCancelListener cancelListener = dialog -> {
            dialog.dismiss();
            if (onCancelListener != null) {
                onCancelListener.onCancel(dialog);
            }
        };
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("确定", clickListener);
        if (!TextUtils.isEmpty(negativeMsg)) {
            alertDialog.setNegativeButton(negativeMsg, clickListener);
        }
        alertDialog.setOnCancelListener(cancelListener);
        alertDialog.setCancelable(true);
        alertDialog.create().show();
    }
}
