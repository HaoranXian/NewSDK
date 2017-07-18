package com.example.administrator.sdk.HttpCenter;


import com.example.administrator.sdk.bean.AliAddrsBean;
import com.example.administrator.sdk.bean.ThoroughfareData;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;
import rx.Observer;

/**
 * Created by Administrator on 2017/7/4.
 */

public interface APIService {
    @GET()
    Observable<String> requestForGet(@Url String url, @QueryMap Map<String, String> parameter);

    @FormUrlEncoded
    @POST()
    Observable<String> requestForPost(@Url String url, @QueryMap Map<String, String> parameter);
}
