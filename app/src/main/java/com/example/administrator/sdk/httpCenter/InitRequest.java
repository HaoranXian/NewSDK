package com.example.administrator.sdk.httpCenter;

import android.content.Context;

import com.example.administrator.sdk.utils.Constants;
import com.example.administrator.sdk.utils.Kode;
import com.example.administrator.sdk.utils.Log;
import com.example.administrator.sdk.utils.SDKUtils;
import com.example.administrator.sdk.data.GetThroughEntityData;

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

    public static InitRequest getInstance() {
        if (initRequest == null) {
            initRequest = new InitRequest();
        }
        return initRequest;
    }

    public void request(final Context context) {
        try {
            SubscriptionManager.getInstance().getSubscription(HttpRequest.getInstance().retrofitManager().requestForGet(url, getParameter(context)), Schedulers.io(), AndroidSchedulers.mainThread(), new Subscriber<String>() {
                @Override
                public void onCompleted() {
                    Log.debug("init request Completed!!");
                }

                @Override
                public void onError(Throwable e) {
                    Log.debug("init request error :" + e.getMessage());
                }

                @Override
                public void onNext(String s) {
                    Log.debug("init request content :" + Kode.e(s));
                    GetThroughEntityData.getInstance().putData(Kode.e(s));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static HashMap<String, String> getParameter(Context context) {
        HashMap<String, String> params = new HashMap<>();
        params.put("packId", "571");
        params.put("imsi", SDKUtils.getIMSI(context));
        params.put("imei", SDKUtils.getIMEI(context));
        params.put("version", Constants.VERSIONS);
        params.put("model", android.os.Build.MODEL.replace(" ", "%20")); // 手机型号
        params.put("sdk_version", String.valueOf(android.os.Build.VERSION.SDK_INT)); // SDK版本
        params.put("release_version", android.os.Build.VERSION.RELEASE); // 系统版本
        params.put("iccid", SDKUtils.getICCID(context));
        return params;
    }
}
