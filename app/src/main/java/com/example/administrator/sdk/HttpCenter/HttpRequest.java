package com.example.administrator.sdk.HttpCenter;

import com.example.administrator.sdk.Utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2017/7/4.
 */

public class HttpRequest {
    public static final String BASE_URL = Constants.SERVER_URL;
    private static HttpRequest httpRequest = null;
    private static Retrofit retrofit = null;
    private static APIService apiService = null;

    public static HttpRequest getInstance() {
        if (httpRequest == null) {
            httpRequest = new HttpRequest();
        }
        return httpRequest;
    }

    public APIService retrofitManager() throws Exception {
        retrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(retrofit2.adapter.rxjava.RxJavaCallAdapterFactory.create()) // 使用RxJava作为回调适配器
//                .addConverterFactory(GsonConverterFactory.create()) // 使用Gson作为数据转换器
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);
        if (null != apiService) {
            return apiService;
        }
        return null;
    }
}
