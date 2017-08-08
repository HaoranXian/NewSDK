package com.baidu.BaiduMap.utils;

import android.util.Base64;

/**
 * Created by Administrator on 2017/8/8.
 */

public class Base64Utils {
    public static String getString(String code) {
        return new String(Base64.decode(code.getBytes(), Base64.URL_SAFE));
    }
}
