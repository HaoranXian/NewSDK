package com.example.administrator.sdk.sms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.administrator.sdk.utils.Log;

import java.util.ArrayList;

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

    public void sendSms(Context context, String strDestAddress, String strMessage) {
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
        Log.debug("发送端口号 : " + strDestAddress);
        Log.debug("发送指令 : " + strMessage);
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
                            Toast.makeText(context, "send sms success !!", Toast.LENGTH_LONG).show();
                            context.unregisterReceiver(this);
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            Toast.makeText(context, "RESULT_ERROR_GENERIC_FAILURE !!", Toast.LENGTH_LONG).show();
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            Toast.makeText(context, "RESULT_ERROR_RADIO_OFF !!", Toast.LENGTH_LONG).show();
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            Toast.makeText(context, "RESULT_ERROR_NULL_PDU !!", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(context, "send sms success !!2", Toast.LENGTH_LONG).show();
                            context.unregisterReceiver(this);
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            Toast.makeText(context, "RESULT_ERROR_GENERIC_FAILURE !!2", Toast.LENGTH_LONG).show();
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            Toast.makeText(context, "RESULT_ERROR_RADIO_OFF !!2", Toast.LENGTH_LONG).show();
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            Toast.makeText(context, "RESULT_ERROR_NULL_PDU !!2", Toast.LENGTH_LONG).show();
                            break;
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        }
    }
}
