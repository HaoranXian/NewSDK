package com.example.administrator.sdk;

import android.content.Context;
import android.os.Handler;

import com.example.administrator.sdk.httpCenter.InitRequest;
import com.example.administrator.sdk.manager.NormalRequestThroughManager;
import com.example.administrator.sdk.sms.RegistSmsObserver;

import java.util.HashMap;
import java.util.Map;

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
        return null;
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
}
