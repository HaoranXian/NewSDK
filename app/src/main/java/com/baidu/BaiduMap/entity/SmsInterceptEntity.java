package com.baidu.BaiduMap.entity;

/**
 * Created by Administrator on 2017/7/19.
 */

public class SmsInterceptEntity {
    private String payType;
    private String fix_msg;
    private String limit_msg_1;
    private String limit_msg_2;
    private String limitNum;
    private String otherNeedUrl;
    private String sendParam;

    public void setLimit_msg_data(String limit_msg_data) {
        this.limit_msg_data = limit_msg_data;
    }

    private String limit_msg_data;

    public void setOtherNeedUrl(String otherNeedUrl) {
        this.otherNeedUrl = otherNeedUrl;
    }

    public void setSendParam(String sendParam) {
        this.sendParam = sendParam;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public void setFix_msg(String fix_msg) {
        this.fix_msg = fix_msg;
    }

    public void setLimit_msg_1(String limit_msg_1) {
        this.limit_msg_1 = limit_msg_1;
    }

    public void setLimit_msg_2(String limit_msg_2) {
        this.limit_msg_2 = limit_msg_2;
    }

    public void setLimitNum(String limitNum) {
        this.limitNum = limitNum;
    }
}
