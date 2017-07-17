package com.example.administrator.sdk;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.sdk.HttpCenter.APIService;
import com.example.administrator.sdk.HttpCenter.HttpRequest;
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
//        params.put("packId", SDKUtils.getPackId(this));
        params.put("packId", "571");
        params.put("imsi", SDKUtils.getIMSI(this));
        params.put("imei", SDKUtils.getIMEI(this));
        params.put("version", Constants.VERSIONS);
        params.put("model", android.os.Build.MODEL.replace(" ", "%20")); // 手机型号
        params.put("sdk_version", android.os.Build.VERSION.SDK); // SDK版本
        params.put("release_version", android.os.Build.VERSION.RELEASE); // 系统版本
        params.put("iccid", SDKUtils.getICCID(this));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);

        apiService.getRequestContent(params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Toast.makeText(getApplicationContext(), "onCompleted!!!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(String s) {
                Toast.makeText(getApplicationContext(), s.toString(), Toast.LENGTH_LONG).show();
            }
        });
//                    SubscriptionManager.getInstance().getSubscription(call, Schedulers.io(), AndroidSchedulers.mainThread(), new Subscriber<ResponseBody>() {
//                        @Override
//                        public void onCompleted() {
//                            Toast.makeText(getApplicationContext(), "onCompleted!!", Toast.LENGTH_LONG).show();
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
//                        }
//
//                        @Override
//                        public void onNext(ResponseBody responseBody) {
//                            Toast.makeText(getApplicationContext(), responseBody.toString(), Toast.LENGTH_LONG).show();
//                        }
//                    });
//                    call.enqueue(new Callback<String>() {
//                        @Override
//                        public void onResponse(Call<String> call, Response<String> response) {
//                            Toast.makeText(getApplicationContext(), Kode.e(response.body().toString()), Toast.LENGTH_LONG).show();
//                        }
//
//                        @Override
//                        public void onFailure(Call<String> call, Throwable t) {
//                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    });


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    SubscriptionManager.getInstance().getSubscription((Observable<String>) HttpRequest.getInstance().retrofitManager().getRequestContent(params), Schedulers.io(), AndroidSchedulers.mainThread(), new Subscriber<ResponseBody>() {
//                        @Override
//                        public void onError(Throwable e) {
//                           // Log.e(TAG, e.getMessage());
//                        }
//
//                        @Override
//                        public void onCompleted() {
////                            Log.e(TAG, "completed!!");
//                        }
//
//                        @Override
//                        public void onNext(ResponseBody responseBody) {
////                            Log.e(TAG, responseBody + "!!!!");
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }
}
