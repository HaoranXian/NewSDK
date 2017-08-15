package com.baidu.BaiduMap;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.baidu.BaiduMap.utils.Log;

import java.util.HashMap;

public class MainActivity extends Activity {
    static HashMap<String, String> params = new HashMap<String, String>();
    final static String TAG = "MainActivity";
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(getApplicationContext(), BMapManager.getInstance().g().toString(), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BMapManager.getInstance().SDKInitializer(this, "1500", 16, "", "撞他一个亿", "1001", "", new android.os.Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                    }
                },
                new android.os.Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                    }
                });
    }

    public void Init(View view) {
        BMapManager.getInstance().SDKInitializer(this, "1500", 16, "", "易镜第二帅测试专用", "1001", "", new android.os.Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Log.debug("init status :" + msg.what);
                    }
                },
                new android.os.Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Log.debug("pay result :" + msg.what);
                    }
                });
    }

    public void Pay(View view) {
        BMapManager.getInstance().BaiduMap(this, "2000", 16, "", "易镜第二帅测试专用", "1001", "", new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.debug(msg.what);
            }
        });
    }

    public void PayPoint(View view) {
        handler.sendEmptyMessage(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BMapManager.getInstance().s(this);
    }

    @Override
    protected void onDestroy() {
        BMapManager.getInstance().close(this);
        super.onDestroy();
    }
}
