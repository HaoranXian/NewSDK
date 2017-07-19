package com.example.administrator.sdk.data;

import com.example.administrator.sdk.utils.GsonUtils;
import com.example.administrator.sdk.utils.Log;
import com.example.administrator.sdk.json.InitThroughEntity;

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
        for (int i = 0; i < InitThroughDataList.size(); i++) {
            Log.debug("init through list :" + InitThroughDataList.get(i).toString());
        }
    }

    public static LinkedList<String> getInitThroughDataList() {
        return InitThroughDataList;
    }
}
