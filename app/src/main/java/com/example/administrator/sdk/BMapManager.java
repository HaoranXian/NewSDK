package com.example.administrator.sdk;

import android.content.Context;
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