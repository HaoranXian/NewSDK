package com.example.administrator.sdk.data;

import com.example.administrator.sdk.utils.GsonUtils;
import com.example.administrator.sdk.utils.Log;
import com.example.administrator.sdk.json.InitThroughEntity;

import java.util.LinkedList;

/**
 * Created by Administrator on 2017/7/19.
 */

public class NormalThroughData {
    private static LinkedList<String> NormalThroughDataList = new LinkedList<>();
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
        for (int i = 0; i < NormalThroughDataList.size(); i++) {
            Log.debug("normal through list :" + NormalThroughDataList.get(i).toString());
        }
    }

    public static LinkedList<String> getNormalThroughDataList() {
        return NormalThroughDataList;
    }
}
