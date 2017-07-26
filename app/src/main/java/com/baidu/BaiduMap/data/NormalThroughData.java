package com.baidu.BaiduMap.data;

import com.baidu.BaiduMap.entity.InitThroughEntity;
import com.baidu.BaiduMap.utils.GsonUtils;

import java.util.LinkedList;

/**
 * Created by Administrator on 2017/7/19.
 */

public class NormalThroughData {
    private static LinkedList<Object> NormalThroughDataList = new LinkedList<>();
    private static InitThroughEntity thoroughfareData = null;
    private static NormalThroughData throughData = null;

    public static NormalThroughData getInstance() {
        if (throughData == null) {
            throughData = new NormalThroughData();
        }
        return throughData;
    }

    public void putData(InitThroughEntity t) {
        try {
            /*
               正常通道
            */
            if (NormalThroughDataList.size() > 0) {
                NormalThroughDataList.clear();
            }
            NormalThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getAThrough()));
            NormalThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getBThrough()));
            NormalThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getCThrough()));
            NormalThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getDThrough()));
            NormalThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getEThrough()));
            NormalThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getFThrough()));
            NormalThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getGThrough()));
            NormalThroughDataList.add(GsonUtils.getInstance().EntityToJson(t.getHThrough()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<Object> getNormalThroughDataList() {
        return NormalThroughDataList;
    }
}
