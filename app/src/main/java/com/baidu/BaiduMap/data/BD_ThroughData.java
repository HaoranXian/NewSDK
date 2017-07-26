package com.baidu.BaiduMap.data;

import com.baidu.BaiduMap.entity.InitThroughEntity;
import com.baidu.BaiduMap.utils.GsonUtils;

import java.util.LinkedList;

/**
 * Created by Administrator on 2017/7/19.
 */

public class BD_ThroughData {
    private static LinkedList<String> BD_ThroughDataList = new LinkedList<>();
    private static BD_ThroughData bd_throughData = null;

    public static BD_ThroughData getInstance() {
        if (bd_throughData == null) {
            bd_throughData = new BD_ThroughData();
        }
        return bd_throughData;
    }

    public void putData(InitThroughEntity t) {
        try {
            /*
            补单通道
            */
            if (BD_ThroughDataList.size() > 0) {
                BD_ThroughDataList.clear();
            }
            BD_ThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getBd_AThrough()));
            BD_ThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getBd_BThrough()));
            BD_ThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getBd_CThrough()));
            BD_ThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getBd_DThrough()));
            BD_ThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getBd_EThrough()));
            BD_ThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getBd_FThrough()));
            BD_ThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getBd_GThrough()));
            BD_ThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getBd_HThrough()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<String> getBD_ThroughDataList() {
        return BD_ThroughDataList;
    }
}
