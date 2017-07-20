package com.example.administrator.sdk.json;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */

public class RequestThroughCallBackEntity {
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

    private List<Order> order;

    public String getThroughId() {
        return throughId;
    }

    public String getState() {
        return state;
    }

    public String getLimitNum() {
        return limitNum;
    }

    public String getType() {
        return type;
    }

    public String getLimit_msg_1() {
        return limit_msg_1;
    }

    public String getThroughName() {
        return throughName;
    }

    public String getPrice() {
        return price;
    }

    public String getLimit_msg_2() {
        return limit_msg_2;
    }

    public List<Order> getOrder() {
        return order;
    }

    public String getCommand() {
        return command;
    }

    public String getResultmsg() {
        return resultmsg;
    }

    public String getPayType() {
        return payType;
    }

    public String getTiming() {
        return timing;
    }

    public String getFix_msg() {
        return fix_msg;
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

        public String getUporder() {
            return uporder;
        }

        public String getCommand() {
            return command;
        }

        public String getSendport() {
            return sendport;
        }

        public String getNumber() {
            return number;
        }
    }
}
