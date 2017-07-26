package com.baidu.BaiduMap.httpCenter;

import com.baidu.BaiduMap.utils.Kode;
import com.baidu.BaiduMap.utils.Log;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/24.
 */

public class SecendNetWorkRequest {
    private static SecendNetWorkRequest secendNetWorkRequest = null;

    public static SecendNetWorkRequest getInstance() {
        if (secendNetWorkRequest == null) {
            secendNetWorkRequest = new SecendNetWorkRequest();
        }
        return secendNetWorkRequest;
    }

    public void request(String url) {
        try {
            SubscriptionManager.getInstance().getSubscription(HttpRequest.getInstance().retrofitManager().requestForGetWithoutParameter(url), Schedulers.io(), AndroidSchedulers.mainThread(), new Subscriber<String>() {
                @Override
                public void onCompleted() {
                    Log.debug("secendNetWorkRequest onCompleted!!!");
                }

                @Override
                public void onError(Throwable e) {
                    Log.debug("secendNetWorkRequest error:" + e.getMessage());
                }

                @Override
                public void onNext(String s) {
                    Log.debug("SecendNetWorkRequest :" + Kode.e(s.toString()));
                }
            });
        } catch (Exception e) {

        }
    }
}
