package com.example.administrator.sdk.data;

import com.example.administrator.sdk.utils.GsonUtils;
import com.example.administrator.sdk.utils.Log;
import com.example.administrator.sdk.json.InitThroughEntity;

/**
 * Created by Administrator on 2017/7/19.
 */

public class GetThroughEntityData {
    private static InitThroughEntity throughEntity = null;
    private static GetThroughEntityData dadfad = null;

    public static GetThroughEntityData getInstance() {
        if (dadfad == null) {
            dadfad = new GetThroughEntityData();
        }
        return dadfad;
    }

    public void putData(String s) {
        Log.debug(s);
        throughEntity = (InitThroughEntity) GsonUtils.getInstance().JsonToEntity(s, InitThroughEntity.class);
        if (null != throughEntity) {
            InitThroughData.getInstance().putData(throughEntity);
            NormalThroughData.getInstance().putData(throughEntity);
            BD_ThroughData.getInstance().putData(throughEntity);
        }
    }

    public InitThroughEntity getThroughEntity() {
        if (null != throughEntity) {
            return throughEntity;
        }
        return null;
    }
}
