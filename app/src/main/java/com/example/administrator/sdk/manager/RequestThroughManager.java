package com.example.administrator.sdk.manager;

import android.content.Context;

import com.example.administrator.sdk.data.NormalThroughData;
import com.example.administrator.sdk.httpCenter.ThroughRequest;
import com.example.administrator.sdk.utils.Kode;
import com.example.administrator.sdk.utils.Log;

import org.json.JSONObject;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/7/19.
 */

public class RequestThroughManager {
    private static RequestThroughManager requestThroughManager = null;

    public static RequestThroughManager getInstance() {
        if (requestThroughManager == null) {
            requestThroughManager = new RequestThroughManager();
        }
        return requestThroughManager;
    }

    public void requestThrough(Context context, String price, String Did) {
        int i = 0;
        try {
            JSONObject json = new JSONObject(NormalThroughData.getNormalThroughDataList().get(i).toString());
            ThroughRequest.getInstance().request(context, json.getString("id"), price, Did, new Subscriber<String>() {
                @Override
                public void onCompleted() {
                    Log.debug("request through completed!!!");
                }

                @Override
                public void onError(Throwable e) {
                    Log.debug("request through error : " + e.getMessage().toString());
                }

                @Override
                public void onNext(String s) {
                    Log.debug("request through successed : " + Kode.e(s));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
