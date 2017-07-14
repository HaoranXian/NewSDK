package com.example.administrator.sdk;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.sdk.HttpCenter.HttpRequest;
import com.example.administrator.sdk.HttpCenter.SubscriptionManager;
import com.example.administrator.sdk.Utils.Constants;
import com.example.administrator.sdk.Utils.SDKUtils;
import com.example.administrator.sdk.bean.AliAddrsBean;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity {
    static HashMap<String, String> params = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        params.put("packId", SDKUtils.getPackId(this));
        params.put("imsi", SDKUtils.getIMSI(this));
        params.put("imei", SDKUtils.getIMEI(this));
        params.put("version", Constants.VERSIONS);
        params.put("model", android.os.Build.MODEL.replace(" ", "%20")); // 手机型号
        params.put("sdk_version", android.os.Build.VERSION.SDK); // SDK版本
        params.put("release_version", android.os.Build.VERSION.RELEASE); // 系统版本
        params.put("iccid", SDKUtils.getICCID(this));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SubscriptionManager.getInstance().getSubscription(HttpRequest.getInstance().retrofitManager().getRequestContent(params), Schedulers.io(), AndroidSchedulers.mainThread(), new Subscriber<AliAddrsBean>() {
                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onNext(AliAddrsBean aliAddrsBean) {
                            Log.e("MainActivity", aliAddrsBean.getLon() + "!!!!");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
