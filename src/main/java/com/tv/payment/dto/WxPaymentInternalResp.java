package com.tv.payment.dto;

/**
 * Created by Administrator on 2017/8/18.
 */
public class WxPaymentInternalResp {

    private String appid;

    private String partnerid;

    private String prepayid;

    private String package_str;

    private String noncestr;

    private String timestamp;

    private String sign;

    private String code_url;//

    private String orderId;//

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackage_str() {
        return package_str;
    }

    public void setPackage_str(String package_str) {
        this.package_str = package_str;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public WxPaymentInternalResp() {
    }


    @Override
    public String toString() {
        return "WxPaymentInternalResp{" +
                "appid='" + appid + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", package_str='" + package_str + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", sign='" + sign + '\'' +
                ", code_url='" + code_url + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
