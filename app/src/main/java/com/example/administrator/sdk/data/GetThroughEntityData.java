package com.example.administrator.sdk.data;

import com.example.administrator.sdk.utils.GsonUtils;
import com.example.administrator.sdk.utils.Log;
import com.example.administrator.sdk.entity.InitThroughEntity;

/**
 * Created by Administrator on 2017/7/19.
 */

public class GetThroughEntityData {
    private static InitThroughEntity initThroughEntity = null;
    private static GetThroughEntityData getThroughEntityData = null;

    public static GetThroughEntityData getInstance() {
        if (getThroughEntityData == null) {
            getThroughEntityData = new GetThroughEntityData();
        }
        return getThroughEntityData;
    }

    public void putData(InitThroughEntity initThroughEntity) {

        if (null != initThroughEntity) {
            InitThroughData.getInstance().putData(initThroughEntity);
            NormalThroughData.getInstance().putData(initThroughEntity);
            BD_ThroughData.getInstance().putData(initThroughEntity);
            PayPointData.getInstance().putData(initThroughEntity);
        }
    }

//    public InitThroughEntity getThroughEntity() {
//        if (null != throughEntity) {
//            return throughEntity;
//        }
//        return null;
//    }
}
