package com.tv.payment.dto;

/**
 * Created by Administrator on 2017/1/17.
 */
public class WxPaymentMobileReq {

     private String appId="wx90f65cb2256662ea";

     private String timeStamp;

     private String nonceStr;

     private String packageStr;

     private String signType;

     private String paySign;

    private String orderId;

    public WxPaymentMobileReq() {
    }

    public WxPaymentMobileReq(String appId, String timeStamp, String nonceStr, String packageStr, String signType, String paySign) {
        this.appId = appId;
        this.timeStamp = timeStamp;
        this.nonceStr = nonceStr;
        this.packageStr = packageStr;
        this.signType = signType;
        this.paySign = paySign;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackageStr() {
        return packageStr;
    }

    public void setPackageStr(String packageStr) {
        this.packageStr = packageStr;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "WxPaymentMobileReq{" +
                "appId='" + appId + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", packageStr='" + packageStr + '\'' +
                ", signType='" + signType + '\'' +
                ", paySign='" + paySign + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
