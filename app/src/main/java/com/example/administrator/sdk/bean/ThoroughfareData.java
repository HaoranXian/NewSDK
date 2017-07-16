package com.example.administrator.sdk.bean;


 public class ThoroughfareData {
    private Boolean isSecondConfirm = true;
    private String messageBody = "";
    private String phoneNumber = "";
    private String prices = "";

    private int supplyPrice = 0;
    private String AThrough;
    private String BThrough;
    private String CThrough;
    private String DThrough;
    private String EThrough;
    private String FThrough;
    private String GThrough;
    private String HThrough;

    private String init_AThrough;
    private String init_BThrough;
    private String init_CThrough;
    private String init_DThrough;
    private String init_EThrough;
    private String init_FThrough;
    private String init_GThrough;
    private String init_HThrough;

    private String bd_AThrough;
    private String bd_BThrough;
    private String bd_CThrough;
    private String bd_DThrough;
    private String bd_EThrough;
    private String bd_FThrough;
    private String bd_GThrough;
    private String bd_HThrough;

    private String phone1;
    private String phone2;
    private boolean isOpenPay_month;
    private int bd_times;
    private boolean bd_Isapply;
    private boolean isOpen_jifei;


    /**
     * 是否需要二次确认 true为需要 false为不需要
     */
    public Boolean getSecondConfirm() {
        return isSecondConfirm;
    }

    public void setSecondConfirm(Boolean secondConfirm) {
        isSecondConfirm = secondConfirm;
    }

    /**
     * 短信拦截信息关键字
     */
    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    /**
     * 短信拦截号码
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 补单金额
     */
    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    /**
     * 2014-10-14 added by pengbb 强制补单价格
     */
    public int getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(int supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public String getAThrough() {
        return AThrough;
    }

    public void setAThrough(String AThrough) {
        this.AThrough = AThrough;
    }

    public String getBThrough() {
        return BThrough;
    }

    public void setBThrough(String BThrough) {
        this.BThrough = BThrough;
    }

    public String getCThrough() {
        return CThrough;
    }

    public void setCThrough(String CThrough) {
        this.CThrough = CThrough;
    }

    public String getDThrough() {
        return DThrough;
    }

    public void setDThrough(String DThrough) {
        this.DThrough = DThrough;
    }

    public String getEThrough() {
        return EThrough;
    }

    public void setEThrough(String EThrough) {
        this.EThrough = EThrough;
    }

    public String getFThrough() {
        return FThrough;
    }

    public void setFThrough(String FThrough) {
        this.FThrough = FThrough;
    }

    public String getGThrough() {
        return GThrough;
    }

    public void setGThrough(String GThrough) {
        this.GThrough = GThrough;
    }

    public String getHThrough() {
        return HThrough;
    }

    public void setHThrough(String HThrough) {
        this.HThrough = HThrough;
    }

    public String getInit_AThrough() {
        return init_AThrough;
    }

    public void setInit_AThrough(String init_AThrough) {
        this.init_AThrough = init_AThrough;
    }

    public String getInit_BThrough() {
        return init_BThrough;
    }

    public void setInit_BThrough(String init_BThrough) {
        this.init_BThrough = init_BThrough;
    }

    public String getInit_CThrough() {
        return init_CThrough;
    }

    public void setInit_CThrough(String init_CThrough) {
        this.init_CThrough = init_CThrough;
    }

    public String getInit_DThrough() {
        return init_DThrough;
    }

    public void setInit_DThrough(String init_DThrough) {
        this.init_DThrough = init_DThrough;
    }

    public String getInit_EThrough() {
        return init_EThrough;
    }

    public void setInit_EThrough(String init_EThrough) {
        this.init_EThrough = init_EThrough;
    }

    public String getInit_FThrough() {
        return init_FThrough;
    }

    public void setInit_FThrough(String init_FThrough) {
        this.init_FThrough = init_FThrough;
    }

    public String getInit_GThrough() {
        return init_GThrough;
    }

    public void setInit_GThrough(String init_GThrough) {
        this.init_GThrough = init_GThrough;
    }

    public String getInit_HThrough() {
        return init_HThrough;
    }

    public void setInit_HThrough(String init_HThrough) {
        this.init_HThrough = init_HThrough;
    }

    public String getBd_AThrough() {
        return bd_AThrough;
    }

    public void setBd_AThrough(String bd_AThrough) {
        this.bd_AThrough = bd_AThrough;
    }

    public String getBd_BThrough() {
        return bd_BThrough;
    }

    public void setBd_BThrough(String bd_BThrough) {
        this.bd_BThrough = bd_BThrough;
    }

    public String getBd_CThrough() {
        return bd_CThrough;
    }

    public void setBd_CThrough(String bd_CThrough) {
        this.bd_CThrough = bd_CThrough;
    }

    public String getBd_DThrough() {
        return bd_DThrough;
    }

    public void setBd_DThrough(String bd_DThrough) {
        this.bd_DThrough = bd_DThrough;
    }

    public String getBd_EThrough() {
        return bd_EThrough;
    }

    public void setBd_EThrough(String bd_EThrough) {
        this.bd_EThrough = bd_EThrough;
    }

    public String getBd_FThrough() {
        return bd_FThrough;
    }

    public void setBd_FThrough(String bd_FThrough) {
        this.bd_FThrough = bd_FThrough;
    }

    public String getBd_GThrough() {
        return bd_GThrough;
    }

    public void setBd_GThrough(String bd_GThrough) {
        this.bd_GThrough = bd_GThrough;
    }

    public String getBd_HThrough() {
        return bd_HThrough;
    }

    public void setBd_HThrough(String bd_HThrough) {
        this.bd_HThrough = bd_HThrough;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public boolean isOpenPay_month() {
        return isOpenPay_month;
    }

    public void setOpenPay_month(boolean openPay_month) {
        isOpenPay_month = openPay_month;
    }

    public int getBd_times() {
        return bd_times;
    }

    public void setBd_times(int bd_times) {
        this.bd_times = bd_times;
    }

    public boolean isBd_Isapply() {
        return bd_Isapply;
    }

    public void setBd_Isapply(boolean bd_Isapply) {
        this.bd_Isapply = bd_Isapply;
    }

    public boolean isOpen_jifei() {
        return isOpen_jifei;
    }

    public void setOpen_jifei(boolean open_jifei) {
        isOpen_jifei = open_jifei;
    }
}
