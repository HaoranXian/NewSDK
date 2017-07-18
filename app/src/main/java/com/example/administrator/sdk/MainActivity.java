package com.example.administrator.sdk;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.sdk.HttpCenter.APIService;
import com.example.administrator.sdk.HttpCenter.HttpRequest;
import com.example.administrator.sdk.HttpCenter.InitRequest;
import com.example.administrator.sdk.HttpCenter.SubscriptionManager;
import com.example.administrator.sdk.Utils.Constants;
import com.example.administrator.sdk.Utils.Kode;
import com.example.administrator.sdk.Utils.SDKUtils;
import com.example.administrator.sdk.bean.AliAddrsBean;
import com.example.administrator.sdk.bean.ThoroughfareData;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    public void Init(View view){
        InitRequest.getInstance().request(this);
    }
}
