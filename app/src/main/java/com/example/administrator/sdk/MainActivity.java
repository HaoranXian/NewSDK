package com.example.administrator.sdk;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.administrator.sdk.httpCenter.InitRequest;
import com.example.administrator.sdk.manager.RequestThroughManager;
import com.example.administrator.sdk.sms.SmsCenter;

import java.util.HashMap;

import okhttp3.logging.HttpLoggingInterceptor;

public class MainActivity extends Activity {
    static HashMap<String, String> params = new HashMap<String, String>();
    final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i(TAG, "retrofitBack = " + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    public void Init(View view) {
        InitRequest.getInstance().request(this);
//        SmsCenter.getInstance().sendSms(this, "13570591718", "00000");
    }

    public void Pay(View view) {
        RequestThroughManager.getInstance().requestThrough(this, "2000", "");
    }
}
