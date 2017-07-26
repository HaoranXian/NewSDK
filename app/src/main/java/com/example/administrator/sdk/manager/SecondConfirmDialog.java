package com.example.administrator.sdk.manager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.TextUtils;

/**
 * Created by Administrator on 2017/7/26.
 */

public class SecondConfirmDialog {
    private static SecondConfirmDialog secondConfirmDialog = null;

    public static SecondConfirmDialog getInstance() {
        if (secondConfirmDialog == null) {
            secondConfirmDialog = new SecondConfirmDialog();
        }
        return secondConfirmDialog;
    }

    public void showDialog(Context context, String str, String price, final Handler normalPayCallBackHandler, DialogInterface.OnClickListener negativeButton) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (TextUtils.isEmpty(str)) {
            double priceShowValue = 0;
            try {
                priceShowValue = Double.parseDouble(price) / 100.00;
            } catch (Exception ignore) {
                builder.setMessage("您确定要支付" + price + "元吗？");
            }
            builder.setMessage("您确定要支付" + priceShowValue + "元吗？");
        } else {
            builder.setMessage(str);
        }
        final String p = price;
        builder.setTitle("提示");
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                PayCallBackHandler.getInstance().payCancel(normalPayCallBackHandler);
            }
        });
        if (null != negativeButton) {
            builder.setNegativeButton("确认", negativeButton);
        }
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                PayCallBackHandler.getInstance().payCancel(normalPayCallBackHandler);
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }
}
