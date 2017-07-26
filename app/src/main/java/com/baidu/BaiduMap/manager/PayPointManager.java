package com.baidu.BaiduMap.manager;

import com.baidu.BaiduMap.data.PayPointData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/26.
 */

public class PayPointManager {
    private static PayPointManager payPointManager = null;

    public static PayPointManager getInstance() {
        if (payPointManager == null) {
            payPointManager = new PayPointManager();
        }
        return payPointManager;
    }

    public HashMap<String, Map<String, Object>> getPayPoint() {
        return PayPointData.getInstance().getG();
    }
}
