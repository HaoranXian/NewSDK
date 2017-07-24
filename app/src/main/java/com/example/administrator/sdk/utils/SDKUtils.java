package com.example.administrator.sdk.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * 获取字符串中的验证码
     *
     * @param codeLength 验证码长度
     * @param body       需要查询的字符串
     * @return
     */
    public static String getCode2Sms(int codeLength, String body) {
        // 首先([a-zA-Z0-9]{6})是得到一个连续的六位数字字母组合
        // (?<![a-zA-Z0-9])负向断言([0-9]{6})前面不能有数字
        // (?![a-zA-Z0-9])断言([0-9]{6})后面不能有数字出现
        body = body.replaceAll("[\\p{Punct}\\s]+", "");
        if (Constants.isOutPut) {
            Log.debug("需要查询验证码的body:" + body);
        }
        Pattern p = Pattern.compile("(?<![a-zA-Z0-9])([a-zA-Z0-9]{" + codeLength + "})(?![a-zA-Z0-9])");
        Matcher m = p.matcher(body);
        if (m.find()) {
            return m.group(0);
        } else {
            p = Pattern.compile("(?<![a-zA-Z0-9])([a-zA-Z0-9]{" + 6 + "})(?![a-zA-Z0-9])");
            m = p.matcher(body);
            if (m.find()) {
                return m.group(0);
            }
        }
        return null;
    }
}
