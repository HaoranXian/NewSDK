package com.example.administrator.sdk.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;

/**
 * Created by Administrator on 2017/7/14.
 */

public class SDKUtils {
    public static String getPackId(Context ctx) {
        // if (Log.D) {
        // return "86";
        // }
        String gameId = null;
        ApplicationInfo appinfo = null;
        try {
            appinfo = ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
            if (appinfo != null) {
                Bundle metaData = appinfo.metaData;
                if (metaData != null) {
                    gameId = metaData.get("Y-PAY-PID").toString();
                    return gameId;
                }
            }
        } catch (Exception e) {
            if (Log.D)
                e.printStackTrace();
            return gameId;
        }
        return gameId;
    }

    /**
     * 读取手机唯一标识
     *
     * @param ctx
     * @return
     */
    public static String getIMSI(final Context ctx) {
        String imsi = "";
        try {
            TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
            imsi = tm.getSubscriberId();
        } catch (Exception e) {

        }
        return imsi;
    }

    /**
     * 获取iccid
     */
    public static String getICCID(Context ctx) {
        String iccid = "";
        try {
            TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
            iccid = tm.getSimSerialNumber(); // 取出ICCID
        } catch (Exception e) {

        }
        return iccid;
    }

    /**
     * 获取手机设备号
     *
     * @param ctx
     * @return
     */
    public static String getIMEI(final Context ctx) {
        String imei = "";
        try {
            TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
            imei = tm.getDeviceId();
        } catch (Exception e) {

        }
        return imei;
    }

    public static String getGameId(Context ctx) {
        String gameId = null;
        ApplicationInfo appinfo = null;
        try {
            appinfo = ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
            if (appinfo != null) {
                Bundle metaData = appinfo.metaData;
                if (metaData != null) {
                    gameId = metaData.get("Y-PAY-GAMEID").toString();
                    return gameId;
                }
            }
        } catch (Exception e) {
            if (Log.D)
                e.printStackTrace();
            return gameId;
        }
        return gameId;
    }

}
