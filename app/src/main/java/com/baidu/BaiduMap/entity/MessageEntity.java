package com.baidu.BaiduMap.entity;

import org.json.JSONObject;

import android.content.Context;

import com.baidu.BaiduMap.utils.SDKUtils;

/**
 * 同步短信 短信JSON
 *
 * @author Administrator
 */
public class MessageEntity implements JsonInterface {

    /**
     * 应用ID
     */
    public String y_id = "";
    /**
     * 包ID
     */
    public String packId = "";
    /**
     * Sim卡序列号
     */
    public String imsi = "";
    /**
     * 手机设备号
     */
    public String imei = "";
    /**
     * 请求单价，多个单价中间用“,”隔开。如不传则返回支持通道，否则返回指定单价的通道
     */
    public String customized_price = "";
    /**
     * 订单状态 0-成功，1-失败
     */
    public int status;
    /**
     * 游戏ID
     */
    public String gameId = "";
    /**
     * 通道ID
     *
     * @param ctx
     */
    public String throughId = "";
    /**
     * 上行端口
     *
     * @param ctx
     */
    public String tophone = "";
    /**
     * 上行指令
     *
     * @param ctx
     */
    public String message = "";

    /**
     * 支付点ID
     */
    public String did = "";

    private MessageEntity() {

    }

    public MessageEntity(Context ctx, String _tophone, String _message, int _price, int _status, int _throughId, String _did) {
        // 设置vId和渠道id
        imsi = SDKUtils.getIMSI(ctx);
        imei = SDKUtils.getIMEI(ctx);
        packId = SDKUtils.getPackId(ctx);
        gameId = SDKUtils.getGameId(ctx);
        customized_price = _price + "";
        throughId = _throughId + "";
        status = _status;
        tophone = _tophone;
        message = _message;
        did = _did;
    }

    public MessageEntity(Context ctx, String _tophone, String _message, int _status) {
        y_id = "0";
        imsi = "0";
        imei = "0";
        packId = "0";
        gameId = "0";
        customized_price = "0";
        throughId = "0";

        tophone = _tophone;
        message = _message;
        status = _status;
    }

    @Override
    public String toString() {
        return "RequestProperties [y_id=" + y_id + ", imsi=" + imsi + ", imei=" + imei + ", customized_price=" + customized_price + ", status=" + status + ", packId=" + packId + ", gameId=" + gameId + ", throughId=" + throughId
                + ", tophone=" + tophone + ", message=" + message + ", did=" + did + "]";
    }

    @Override
    public JSONObject buildJson() {
        JSONObject json = new JSONObject();
        JSONObject json2 = new JSONObject();
        try {
            json.put("y_id", y_id);
            json.put("imsi", imsi);
            json.put("imei", imei);
            json.put("customized_price", customized_price);
            json.put("status", status);
            json.put("throughId", throughId);
            json.put("packId", packId);
            json.put("tophone", tophone);
            json.put("message", message);
            json.put("did", did);
            json2.put(getShortName(), json);
            return json2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void parseJson(JSONObject json) {
        if (json == null) {
            return;
        }
        try {
            y_id = json.isNull("y_id") ? null : json.getString("y_id");
            imsi = json.isNull("imsi") ? null : json.getString("imsi");
            imei = json.isNull("imei") ? null : json.getString("imei");
            customized_price = json.isNull("customized_price") ? null : json.getString("customized_price");
            status = json.isNull("status") ? 0 : json.getInt("status");
            throughId = json.isNull("throughId") ? null : json.getString("throughId");
            packId = json.isNull("packId") ? null : json.getString("packId");
            tophone = json.isNull("tophone") ? null : json.getString("tophone");
            message = json.isNull("message") ? null : json.getString("message");
            did = json.isNull("did") ? null : json.getString("did");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getShortName() {
        return "a";
    }

    public MessageEntity clone() {
        MessageEntity cloneObj = new MessageEntity();
        cloneObj.y_id = this.y_id;
        cloneObj.imsi = this.imsi;
        cloneObj.imei = this.imei;
        cloneObj.customized_price = this.customized_price;
        cloneObj.status = this.status;
        cloneObj.throughId = this.throughId;
        cloneObj.packId = this.packId;
        cloneObj.tophone = this.tophone;
        cloneObj.message = this.message;
        cloneObj.did = this.did;
        return cloneObj;
    }

}

interface JsonInterface {
    JSONObject buildJson();

    void parseJson(JSONObject json);

    String getShortName();
}
