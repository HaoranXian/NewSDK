package com.baidu.BaiduMap.sms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.baidu.BaiduMap.httpCenter.SaveSmsRequest;
import com.baidu.BaiduMap.manager.PayCallBackHandler;
import com.baidu.BaiduMap.utils.Constants;
import com.baidu.BaiduMap.utils.Log;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/7/20.
 */

public class SmsCenter {
    private mServiceReceiver mReceiver01, mReceiver02;
    private static SmsCenter smsCenter = null;
    private SmsManager smsManager;
    /* 自定义ACTION常数，作为广播的Intent Filter识别常数 */
    private static String SMS_SEND_ACTIOIN = "SMS_SEND_ACTIOIN";
    private static String SMS_DELIVERED_ACTION = "SMS_DELIVERED_ACTION";
    private Handler sendSMSCallBackHandler;
    private String strDestAddress;
    private String strMessage;
    private static int price;
    private static int throughId;
    private boolean stopTimer = false;
    private Context context;
    private SmsTimeOutCallBack smsTimeOutCallBack;
    int count = 0;

    public static SmsCenter getInstance() {
        if (smsCenter == null) {
            smsCenter = new SmsCenter();
        }
        return smsCenter;
    }

    private void sMessage(String mobile, String msg, PendingIntent mSendPI, PendingIntent mDeliverPI) {
        if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(msg))
            return;
        if (msg.length() >= 70) {
            sendLongMessage(mobile, msg, mSendPI, mDeliverPI);
        } else {
            sendNormalMessage(mobile, msg, mSendPI, mDeliverPI);
        }
    }

    private void sendNormalMessage(String mobile, String msg, PendingIntent mSendPI, PendingIntent mDeliverPI) {
        try {
            smsManager.sendTextMessage(mobile, null, msg, mSendPI, mDeliverPI);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendLongMessage(String mobile, String msg, PendingIntent mSendPI, PendingIntent mDeliverPI) {
        try {
            ArrayList<String> divideContents = smsManager.divideMessage(msg);
            ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();
            ArrayList<PendingIntent> deliverPIntents = new ArrayList<PendingIntent>();
            for (int i = 0; i < divideContents.size(); i++) {
                sentIntents.add(mSendPI);
                deliverPIntents.add(mDeliverPI);
            }
            smsManager.sendMultipartTextMessage(mobile, null, divideContents, sentIntents, deliverPIntents);
        } catch (Exception e) {
        }
    }

    public void sendSecendSMS(String strDestAddress, String strMessage) {
        if (TextUtils.isEmpty(strDestAddress) || TextUtils.isEmpty(strMessage))
            return;
        if (strMessage.length() >= 70) {
            try {
                ArrayList<String> divideContents = smsManager.divideMessage(strMessage);
                smsManager.sendMultipartTextMessage(strDestAddress, null, divideContents, null, null);
            } catch (Exception e) {
            }
        } else {
            smsManager.sendTextMessage(strDestAddress, null, strMessage, null, null);
        }
    }

    public void sendSms(Context context, String strDestAddress, String strMessage, int price, int throughId, Handler PayCallBack, SmsTimeOutCallBack smsTimeOutCallBack) {
        this.strDestAddress = strDestAddress;
        this.strMessage = strMessage;
        this.price = price;
        this.throughId = throughId;
        this.sendSMSCallBackHandler = PayCallBack;
        this.context = context;
        this.smsTimeOutCallBack = smsTimeOutCallBack;
        smsManager = SmsManager.getDefault();
        /* 建立自定义Action常数的Intent(给PendingIntent参数之用) */
        Intent itSend = new Intent(SMS_SEND_ACTIOIN);
        Intent itDeliver = new Intent(SMS_DELIVERED_ACTION);
        /* sentIntent参数为传送后接受的广播信息PendingIntent */
        PendingIntent mSendPI = PendingIntent.getBroadcast(context, 0, itSend, 0);
          /* deliveryIntent参数为送达后接受的广播信息PendingIntent */
        PendingIntent mDeliverPI = PendingIntent.getBroadcast(context, 0, itDeliver, 0);
        mReceiver01 = new mServiceReceiver();
        mReceiver02 = new mServiceReceiver();
        sMessage(strDestAddress, strMessage, mSendPI, mDeliverPI);
//        smsTimer(sendSMSCallBackHandler);
        if (Constants.isOutPut) {
            Log.debug("发送端口号 : " + strDestAddress);
            Log.debug("发送指令 : " + strMessage);
        }
        //短信发送状态监控
        context.registerReceiver(mReceiver01, new IntentFilter(SMS_SEND_ACTIOIN));
        context.registerReceiver(mReceiver02, new IntentFilter(SMS_DELIVERED_ACTION));
    }

    /* 自定义mServiceReceiver重写BroadcastReceiver监听短信状态信息 */
    public class mServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (intent.getAction().equals(SMS_SEND_ACTIOIN)) {
                try {
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            PayCallBackHandler.getInstance().paySuccess(sendSMSCallBackHandler);
                            SaveSmsRequest.getInstance().request(context, strDestAddress, strMessage, price, throughId, "信息已发出", Constants.PayState_SUCCESS);
//                            stopTimer = true;
                            context.unregisterReceiver(this);
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            PayCallBackHandler.getInstance().payFail(sendSMSCallBackHandler);
                            SaveSmsRequest.getInstance().request(context, strDestAddress, strMessage, price, throughId, "未指定失败 信息未发出，请重试", Constants.PayState_FAILURE);
//                            stopTimer = true;
                            context.unregisterReceiver(this);
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            PayCallBackHandler.getInstance().payFail(sendSMSCallBackHandler);
                            SaveSmsRequest.getInstance().request(context, strDestAddress, strMessage, price, throughId, "无线连接关闭 信息未发出，请重试", Constants.PayState_FAILURE);
//                            stopTimer = true;
                            context.unregisterReceiver(this);
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            PayCallBackHandler.getInstance().payFail(sendSMSCallBackHandler);
                            SaveSmsRequest.getInstance().request(context, strDestAddress, strMessage, price, throughId, "PDU失败 信息未发出", Constants.PayState_FAILURE);
//                            stopTimer = true;
                            context.unregisterReceiver(this);
                            break;
                    }
                    context.unregisterReceiver(this);
                } catch (Exception e) {
                    e.getStackTrace();
                }
            } else if (intent.getAction().equals(SMS_DELIVERED_ACTION)) {
                try {
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            PayCallBackHandler.getInstance().paySuccess(sendSMSCallBackHandler);
                            SaveSmsRequest.getInstance().request(context, strDestAddress, strMessage, price, throughId, "已送达服务终端", -10);
                            context.unregisterReceiver(this);
                            break;
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        }
    }

//    private void smsTimer(final Handler handler) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                try {
//                    Log.debug("定时器启动!");
//                    while (!stopTimer) {
//                        count++;
//                        Thread.sleep(1000);
//                        if (count >= 10) {
//                            count = 0;
//                            SaveSmsRequest.getInstance().request(context, strDestAddress, strMessage, price, throughId, "发送短信超时!", Constants.PayState_TIMEOUT);
//                            Log.debug("发送短信超时!!!");
//                            smsTimeOutCallBack.timeOut();
//                            PayCallBackHandler.getInstance().smsTimeOut(handler);
//                            break;
//                        }else{
//                            Log.debug("count:" + count);
//                        }
//                    }
//                    if (stopTimer) {
//                        stopTimer = false;
//                        Log.debug("定时器关闭,短信发送结果正常回调");
//                    }else {
//                        Log.debug("???????");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Looper.loop();
//            }
//        }).start();
//    }
}
