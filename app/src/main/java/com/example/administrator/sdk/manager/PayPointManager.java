package com.example.administrator.sdk.manager;

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

}
