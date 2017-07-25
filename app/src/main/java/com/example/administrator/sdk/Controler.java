package com.example.administrator.sdk;

import android.content.Context;

import com.example.administrator.sdk.httpCenter.InitRequest;
import com.example.administrator.sdk.manager.NormalRequestThroughManager;
import com.example.administrator.sdk.sms.RegistSmsObserver;

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
    public void i(Context context) {
        InitRequest.getInstance().request(context);
    }

    /*
    支付
     */
    public void p(Context context, String price, String Did, String productName) {
        NormalRequestThroughManager.getInstance().requestThrough(context, price, Did, productName);
    }

    /*
    获取计费点
     */
    public void g() {

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
