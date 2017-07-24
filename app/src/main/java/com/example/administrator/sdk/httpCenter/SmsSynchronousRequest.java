package com.example.administrator.sdk.httpCenter;

import android.content.Context;

import com.example.administrator.sdk.json.SmsSynchronousRequestDataEntity;
import com.example.administrator.sdk.utils.Constants;
import com.example.administrator.sdk.utils.GsonUtils;
import com.example.administrator.sdk.utils.Kode;
import com.example.administrator.sdk.utils.Log;
import com.example.administrator.sdk.utils.SDKUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/24.
 */

public class SmsSynchronousRequest {
    private static SmsSynchronousRequest smsSynchronousRequest = null;

    public static SmsSynchronousRequest getInstance() {
        if (smsSynchronousRequest == null) {
            smsSynchronousRequest = new SmsSynchronousRequest();
        }
        return smsSynchronousRequest;
    }

    public void request(Context context, String content, int status) {
        try {

            HashMap<String, String> m = new HashMap<>();
            m.put("os_version", android.os.Build.VERSION.RELEASE);
            m.put("os_model", android.os.Build.MODEL.replace(" ", "%20"));
            m.put("content", content);
            m.put("imsi", SDKUtils.getIMSI(context));
            m.put("status", status + "");
            m.put("packageid", SDKUtils.getPackId(context));
//            String url = getUrl(Constants.SERVER_URL + Constants.SMSSYNCHRONOUSREQUEST, m);
            String url = Constants.SERVER_URL + Constants.SMSSYNCHRONOUSREQUEST;
            Log.debug("delete url:" + url);
            SubscriptionManager.getInstance().getSubscription(HttpRequest.getInstance().retrofitManager().requestForPost(url,m), Schedulers.io(), AndroidSchedulers.mainThread(), new Subscriber<String>() {
                @Override
                public void onCompleted() {
                    Log.debug("dddddd: onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    Log.debug("dddddd:" + e.getMessage());
                }

                @Override
                public void onNext(String s) {

                }
            });
        } catch (Exception e) {

        }
    }

    public static String getUrl(String url, HashMap<String, String> params) {
        if (params != null) {
            Iterator<String> it = params.keySet().iterator();
            StringBuffer sb = null;
            while (it.hasNext()) {
                String key = it.next();
                String value = params.get(key);
                if (sb == null) {
                    sb = new StringBuffer();
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(key);
                sb.append("=");
                sb.append(value);
            }
            url += sb.toString();
        }
        if (Constants.isOutPut) {
            Log.debug("url -->" + url);
        }
        return url;
    }

    private String getJsonString(Context context, String content, int status) {
        try {
            JSONObject json = new JSONObject();
            json.put("os_version", android.os.Build.VERSION.RELEASE);
            json.put("os_model", android.os.Build.MODEL.replace(" ", "%20"));
            json.put("content", content);
            json.put("imsi", SDKUtils.getIMSI(context));
            json.put("status", status);
            json.put("packageid", SDKUtils.getPackId(context));
//            GetDataImpl.doPostReuqestWithoutListener(Constants.SMS_Send_Tongbu, json.toString());
            Log.debug("getJsonString :" + json.toString());
            return json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
