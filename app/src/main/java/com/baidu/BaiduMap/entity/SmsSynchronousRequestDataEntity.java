package com.baidu.BaiduMap.entity;

/**
 * Created by Administrator on 2017/7/24.
 */

public class SmsSynchronousRequestDataEntity {
    private String os_version;
    private String os_model;
    private String content;
    private String imsi;
    private String status;
    private String packageid;
    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getOs_model() {
        return os_model;
    }

    public void setOs_model(String os_model) {
        this.os_model = os_model;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPackageid() {
        return packageid;
    }

    public void setPackageid(String packageid) {
        this.packageid = packageid;
    }
}
