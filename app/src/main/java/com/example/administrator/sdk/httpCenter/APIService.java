package com.example.administrator.sdk.httpCenter;


import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2017/7/4.
 */

public interface APIService {
    @GET()
    Observable<String> requestForGet(@Url String url, @QueryMap Map<String, String> parameter);

    @GET()
    Observable<String> requestForGetWithoutParameter(@Url String url);

    @POST()
    Observable<String> requestForPost(@Url String url, @Body RequestBody route);
}
