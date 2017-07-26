package com.baidu.BaiduMap.utils;

import java.io.File;

public final class Constants {
    /**
     * 易镜科技服务器
     */
    public static final String SERVER_URL = "http://120.76.74.206:8080/";

    // 海琛服务器
//    public static final String SERVER_URL = "http://192.168.1.103:8080/";
    public final static boolean isOutPut = true;// 是否输出
    private static String URL = "/youxipj/sdkinit/PhoneAPIAction!";
    public final static String INIT_URL = URL + "init?";
    public final static String REQUEST_THROUGH_URL = URL + "QueryThourgh?";
    public final static String SMS_SYNCHRONOUS_REQUEST_RUL = "youxipj/Others/othersNeed!sdk_delete_msg";
    // SDK包版本号
    public static final String VERSIONS = "3.2.11";
    public static final String isRequest = "PAY_REQUEST";// "vgp_id"
    public final static int PayState_SUCCESS = 0;// 付费成功
    public final static int PayState_FAILURE = 1;// 付费失败
    public final static int PayState_CANCEL = 2;// 付费取消

}
