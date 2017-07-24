package com.example.administrator.sdk.json;

import java.util.List;
import java.util.Objects;

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


    private String limit_msg_data;

    private String command;
    private String resultmsg;
    private String payType;


    private String timing;
    private String fix_msg;

    private List<Order> order;

    public String getLimit_msg_data() {
        return limit_msg_data;
    }

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
        private String otherNeedUrl;
        private Object sendParam;

        public static class SendParam {
            private String orderid;

            public String getOrderid() {
                return orderid;
            }
        }

        public Object getSendParam() {
            return sendParam;
        }

        public String getOtherNeedUrl() {
            return otherNeedUrl;
        }

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
