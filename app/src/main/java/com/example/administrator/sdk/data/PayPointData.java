package com.example.administrator.sdk.data;

import android.content.Context;
import android.text.TextUtils;
import com.example.administrator.sdk.entity.InitThroughEntity;
import com.example.administrator.sdk.utils.GsonUtils;
import com.example.administrator.sdk.utils.SDKUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/26.
 */

public class PayPointData {
    private static PayPointData payPointData = null;
    private static HashMap<String, Map<String, Object>> g = new HashMap<>();

    public static PayPointData getInstance() {
        if (payPointData == null) {
            payPointData = new PayPointData();
        }
        return payPointData;
    }

    public void putData(InitThroughEntity t, Context context) {
        String rows = GsonUtils.getInstance().EntityToJson(t.getRows());
        payPoint(rows, context);
    }

    private void payPoint(String s, Context context) {
        JSONObject oj;
        try {
            oj = new JSONObject(s.toString());
            JSONArray jsonArray = oj.getJSONArray("rows");
            for (int i = 0; i < jsonArray.length(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                JSONObject json = jsonArray.getJSONObject(i);
                String imsi = SDKUtils.getIMSI(context);
                if (imsi.equals("")) {
                    return;
                }
                if (!TextUtils.isEmpty(imsi)) {
                    if ((imsi.startsWith("46000")) || (imsi.startsWith("46002")) || (imsi.startsWith("46007"))) { // 移动
                        map.put("price", json.getString("yprice"));
                    }
                    if (imsi.startsWith("46001")) { // 联通
                        map.put("price", json.getString("lprice"));
                    }
                    if (imsi.startsWith("46003")) { // 电信
                        map.put("price", json.getString("dprice"));
                    }
                }
                map.put("dname", json.get("dname"));
                map.put("isopen", json.get("isopen"));
                g.put(json.getString("did"), map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Map<String, Object>> getG() {
        if (null != g && g.size() > 0) {
            return g;
        }
        return null;
    }
}
