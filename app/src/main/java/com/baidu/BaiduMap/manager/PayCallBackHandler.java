package com.baidu.BaiduMap.manager;

import android.os.Handler;
import android.os.Message;

import com.baidu.BaiduMap.utils.Constants;

/**
 * Created by Administrator on 2017/7/26.
 */

public class PayCallBackHandler extends Handler {
    private static PayCallBackHandler payCallBackHandler = null;

    public static PayCallBackHandler getInstance() {
        if (payCallBackHandler == null) {
            payCallBackHandler = new PayCallBackHandler();
        }
        return payCallBackHandler;
    }

    public void paySuccess(Handler payHandler) {
        setPayCallBackHandler(Constants.PayState_SUCCESS, payHandler);
    }

    public void payFail(Handler payHandler) {
        setPayCallBackHandler(Constants.PayState_FAILURE, payHandler);
    }

    public void payCancel(Handler payHandler) {
        setPayCallBackHandler(Constants.PayState_CANCEL, payHandler);
    }

    public void smsTimeOut(Handler payHandler) {
        setPayCallBackHandler(Constants.PayState_TIMEOUT, payHandler);
    }

    private void setPayCallBackHandler(int status, Handler setHandler) {
        Message msg = new Message();
        msg.what = status;
        setHandler.sendMessage(msg);
    }
}
