package com.example.administrator.sdk.HttpCenter;


import com.example.administrator.sdk.bean.AliAddrsBean;
import com.example.administrator.sdk.bean.ThoroughfareData;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;
import rx.Observer;

/**
 * Created by Administrator on 2017/7/4.
 */

public interface APIService {
    /**
     * 初始化网络请求
     *
     * @param option
     * @return
     */
    @GET("youxipj/sdkinit/PhoneAPIAction!init?")
//    Observable<String> getRequestContent(@QueryMap Map<String, String> option);
    Observable<String> getRequestContent(@QueryMap Map<String, String> option);
}
