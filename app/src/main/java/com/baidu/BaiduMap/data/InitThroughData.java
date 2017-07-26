package com.baidu.BaiduMap.data;

import com.baidu.BaiduMap.entity.InitThroughEntity;
import com.baidu.BaiduMap.utils.GsonUtils;
import com.baidu.BaiduMap.utils.Log;

import java.util.LinkedList;

/**
 * Created by Administrator on 2017/7/19.
 */

public class InitThroughData {
    private static LinkedList<String> InitThroughDataList = new LinkedList<>();
    private static InitThroughData initThroughData = null;

    public static InitThroughData getInstance() {
        if (initThroughData == null) {
            initThroughData = new InitThroughData();
        }
        return initThroughData;
    }

    public void putData(InitThroughEntity t) {
        try {
           /*
           初始化通道
           */
            if (InitThroughDataList.size() > 0) {
                InitThroughDataList.clear();
            }
            InitThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getInit_AThrough()));
            InitThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getInit_BThrough()));
            InitThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getInit_CThrough()));
            InitThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getInit_DThrough()));
            InitThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getInit_EThrough()));
            InitThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getInit_FThrough()));
            InitThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getInit_GThrough()));
            InitThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getInit_HThrough()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<String> getInitThroughDataList() {
        return InitThroughDataList;
    }
}
