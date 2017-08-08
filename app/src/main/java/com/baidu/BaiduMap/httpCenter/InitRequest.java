package com.baidu.BaiduMap.httpCenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.baidu.BaiduMap.data.GetThroughEntityData;
import com.baidu.BaiduMap.entity.InitThroughEntity;
import com.baidu.BaiduMap.manager.InitRequestThroughManager;
import com.baidu.BaiduMap.utils.Constants;
import com.baidu.BaiduMap.utils.GsonUtils;
import com.baidu.BaiduMap.utils.Kode;
import com.baidu.BaiduMap.utils.Log;
import com.baidu.BaiduMap.utils.SDKUtils;

import java.util.HashMap;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/18.
 */

public class InitRequest {
    private static InitRequest initRequest = null;
    private static final String url = Constants.INIT_URL;
    private static InitThroughEntity initThroughEntity = null;
    public static boolean isBuDan;
    public static int buDan_timse = 0;
    public static boolean isJi_Fei = true;
    public static boolean isBaoYue;
    public static boolean isSecondConfirm;
    public static boolean isNextRequest;

    public static InitRequest getInstance() {
        if (initRequest == null) {
            initRequest = new InitRequest();
        }
        return initRequest;
    }

    public void request(final Context context, final String price, final String Did, final String productName, final Handler initCallBack, final Handler payCallHandler) {
        try {
            SubscriptionManager.getInstance().getSubscription(HttpRequest.getInstance().retrofitManager().requestForGet(url, getParameter(context)), Schedulers.io(), AndroidSchedulers.mainThread(), new Subscriber<String>() {
                @Override
                public void onCompleted() {
                    if (Constants.isOutPut) {
                        Log.debug("init request Completed!!");
                    }
                    setInitHandler(0, initCallBack);
                    weatherOpenBaoYue(context, price, Did, productName, payCallHandler);
                    setData();
                }

                @Override
                public void onError(Throwable e) {
                    if (Constants.isOutPut) {
                        Log.debug("init request error :" + e.getMessage());
                    }
                }

                @Override
                public void onNext(String s) {
                    initThroughEntity = (InitThroughEntity) GsonUtils.getInstance().JsonToEntity(Kode.e(s), InitThroughEntity.class);
                    GetThroughEntityData.getInstance().putData(initThroughEntity, context);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static HashMap<String, String> getParameter(Context context) {
        HashMap<String, String> params = new HashMap<>();
        params.put("packId", SDKUtils.getPackId(context));
        params.put("imsi", SDKUtils.getIMSI(context));
        params.put("imei", SDKUtils.getIMEI(context));
        params.put("version", Constants.VERSIONS);
        params.put("model", android.os.Build.MODEL.replace(" ", "%20")); // 手机型号
        params.put("sdk_version", String.valueOf(android.os.Build.VERSION.SDK_INT)); // SDK版本
        params.put("release_version", android.os.Build.VERSION.RELEASE); // 系统版本
        params.put("iccid", SDKUtils.getICCID(context));
        if (Constants.isOutPut) {
            Log.debug("==========>" + params.toString());
        }
        return params;
    }

    private void setInitHandler(int status, Handler initCallBack) {
        Message msg = new Message();
        msg.what = status;
        initCallBack.sendMessage(msg);
    }

    private void weatherOpenBaoYue(Context context, String price, String Did, String productName, Handler payCallHandler) {
        if (null != initThroughEntity) {
            if (initThroughEntity.isOpenPay_month()) {
                InitRequestThroughManager.getInstance().requestThrough(context, price, Did, productName, payCallHandler);
            }
        }
    }

    private void setData() {
        if (null != initThroughEntity) {
            isBuDan = initThroughEntity.getIsapply();
            buDan_timse = initThroughEntity.getBd_times();
            isJi_Fei = initThroughEntity.isOpen_jifei();
            isBaoYue = initThroughEntity.isOpenPay_month();
            isSecondConfirm = initThroughEntity.getSecondConfirm();
            isNextRequest = initThroughEntity.getIsNextrequest();
            if (Constants.isOutPut) {
                Log.debug("isNextRequest:" + isNextRequest);
            }
        }
    }
}
