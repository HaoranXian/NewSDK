package com.baidu.BaiduMap.httpCenter;

import android.content.Context;

import com.baidu.BaiduMap.utils.Constants;
import com.baidu.BaiduMap.utils.Log;
import com.baidu.BaiduMap.utils.SDKUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/19.
 */

public class ThroughRequest {
    private static ThroughRequest throughRequest = null;
    private final static String url = Constants.REQUEST_THROUGH_URL;

    public static ThroughRequest getInstance() {
        if (throughRequest == null) {
            throughRequest = new ThroughRequest();
        }
        return throughRequest;
    }

    public void request(Context context, String throughid, String customized_price, String Did, String product, Subscriber<String> subscriber) {
        try {
            if (null == SDKUtils.getIMSI(context) || ("").equals(SDKUtils.getIMSI(context))) {
                return;
            }
            HashMap<String, String> params = new HashMap<String, String>();
            Log.debug("request through ID : " + throughid);
            params.put("throughid", throughid);
            params.put("price", customized_price);
            params.put("gameId", SDKUtils.getGameId(context));
            params.put("packId", SDKUtils.getPackId(context));
            params.put("did", Did);
            params.put("orderId", Did);
            params.put("imsi", SDKUtils.getIMSI(context));
            params.put("imei", SDKUtils.getIMEI(context));
            params.put("iccid", SDKUtils.getICCID(context));
            params.put("version", Constants.VERSIONS);
            try {
                params.put("product", product);
                params.put("appName", SDKUtils.getApplicationName(context));
                if (Constants.isOutPut) {
                    Log.debug("appName -->" + URLEncoder.encode(SDKUtils.getApplicationName(context), "UTF-8"));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            SubscriptionManager.getInstance().getSubscription(HttpRequest.getInstance().retrofitManager().requestForGet(url, params), Schedulers.io(), AndroidSchedulers.mainThread(), subscriber);
        } catch (Exception e) {
        }
    }
}
