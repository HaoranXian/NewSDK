package com.example.administrator.sdk.Utils;

import android.content.Context;
import android.widget.Toast;

public class Log {
    // false 隐藏输出
    // true 打开输出
    public static boolean D = Constants.isOutPut;
    private static final String TAG = "PaySDK";

    public static void debug(Object host, Object msg) {
        if (D) {
            String tag = host.getClass().getSimpleName();
            android.util.Log.d(tag, msg != null ? msg.toString() : "null");
        }
    }

    public static void debug(String tag, Object msg) {
        if (D) {
            android.util.Log.d(tag, msg != null ? msg.toString() : "null");
        }
    }

    public static void debug(Object msg) {
        if (D) {
            android.util.Log.d(TAG, msg != null ? msg.toString() : "null");
        }
    }

    public static void toast(Context mContext, String msg) {
        if (D) {
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        }
    }

    public static void info(Object msg) {
        android.util.Log.d(TAG, msg != null ? msg.toString() : "null");
    }

}
