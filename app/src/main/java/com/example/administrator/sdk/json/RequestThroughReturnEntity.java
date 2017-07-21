package com.example.administrator.sdk.json;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */

public class RequestThroughReturnEntity {
    private String throughId;
    private String state;
    private String limitNum;
    private String type;
    private String limit_msg_1;
    private String throughName;
    private String price;
    private String limit_msg_2;
    private String command;
    private String resultmsg;
    private String payType;
    private String timing;
    private String fix_msg;
    private static List<Order> order;

    public String getThroughId() {
        return throughId;
    }

    public void setThroughId(String throughId) {
        this.throughId = throughId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(String limitNum) {
        this.limitNum = limitNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLimit_msg_1() {
        return limit_msg_1;
    }

    public void setLimit_msg_1(String limit_msg_1) {
        this.limit_msg_1 = limit_msg_1;
    }

    public String getThroughName() {
        return throughName;
    }

    public void setThroughName(String throughName) {
        this.throughName = throughName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLimit_msg_2() {
        return limit_msg_2;
    }

    public void setLimit_msg_2(String limit_msg_2) {
        this.limit_msg_2 = limit_msg_2;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getResultmsg() {
        return resultmsg;
    }

    public void setResultmsg(String resultmsg) {
        this.resultmsg = resultmsg;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getFix_msg() {
        return fix_msg;
    }

    public void setFix_msg(String fix_msg) {
        this.fix_msg = fix_msg;
    }

    public static List<Order> getOrder() {
        return order;
    }

    public static void setOrder(List<Order> order) {
        RequestThroughReturnEntity.order = order;
    }

    public static class Order {
        private String price;
        private String uporder;
        private String command;
        private String sendport;
        private String number;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getUporder() {
            return uporder;
        }

        public void setUporder(String uporder) {
            this.uporder = uporder;
        }

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public String getSendport() {
            return sendport;
        }

        public void setSendport(String sendport) {
            this.sendport = sendport;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }

}
