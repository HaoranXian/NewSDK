package com.example.administrator.sdk.HttpCenter;

import android.content.Context;

import com.example.administrator.sdk.Utils.Constants;
import com.example.administrator.sdk.Utils.Kode;
import com.example.administrator.sdk.Utils.Log;
import com.example.administrator.sdk.Utils.SDKUtils;
import com.example.administrator.sdk.bean.ThoroughfareData;
import com.google.gson.Gson;

import java.util.HashMap;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/18.
 */

public class InitRequest {
    private static InitRequest initRequest = null;
    private static final String init_Url = Constants.INIT_URL;

    public static InitRequest getInstance() {
        if (initRequest == null) {
            initRequest = new InitRequest();
        }
        return initRequest;
    }

    public void request(final Context context) {
        try {
            HttpRequest.getInstance().retrofitManager().requestForGet(init_Url, getParameter(context)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
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
                    //解析数据
                    Log.debug("init request content :" + Kode.e(s));
                    Gson gson = new Gson();
                    ThoroughfareData thoroughfareData = gson.fromJson(Kode.e(s), ThoroughfareData.class);
                    Log.debug("======>ThroughareData:" + thoroughfareData.getRows().get(0).dname);
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
