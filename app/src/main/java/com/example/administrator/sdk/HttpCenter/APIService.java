package com.example.administrator.sdk.HttpCenter;


import com.example.administrator.sdk.bean.AliAddrsBean;

import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/7/4.
 */

public interface APIService {
    /**
     * 初始化网络请求
     * @param option
     * @return
     */
    @POST("/youxipj/sdkinit/PhoneAPIAction!init")
    Observable<AliAddrsBean> getRequestContent(@QueryMap Map<String, String> option);
}
