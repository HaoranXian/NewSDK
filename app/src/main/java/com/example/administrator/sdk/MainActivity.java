package com.example.administrator.sdk;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.util.HashMap;

public class MainActivity extends Activity {
    static HashMap<String, String> params = new HashMap<String, String>();
    final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BMapManager.getInstance().SDKInitializer(this);
    }

    public void Init(View view) {
        BMapManager.getInstance().SDKInitializer(this);
//        SmsCenter.getInstance().sendSms(this, "13570591718", "00000");
    }

    public void Pay(View view) {
        BMapManager.getInstance().BaiduMap(this, "2000", "","易镜第二帅测试专用");
    }

    @Override
    protected void onResume() {
        super.onResume();
        BMapManager.getInstance().s(this);
    }
}
