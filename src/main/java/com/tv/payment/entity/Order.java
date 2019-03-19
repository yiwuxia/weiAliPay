package com.tv.payment.entity;

import java.util.Date;

/**
 * Created by len on 2016/12/5.
 */
public class Order {

    private String id;

    private String tId;

    private String system;

    private String channel;

    private String product;

    private String productDesc;

    private String productFee;

    private String fee;

    private String billCreateIp;

    private String noncestr;

    private String openId;

    private String isSubscriber;

    private Date reqTime;

    private Date respTime;

    private String result;

    public Order() {
    }

    public Order(String id, String tId, String system, String channel, String product,
                 String productDesc, String productFee, String fee, String billCreateIp, String noncestr, String openId,
                 String isSubscriber, Date reqTime) {
        this.id =id;
        this.tId = tId;
        this.system = system;
        this.channel = channel;
        this.product = product;
        this.productDesc = productDesc;
        this.productFee = productFee;
        this.fee = fee;
        this.billCreateIp = billCreateIp;
        this.noncestr = noncestr;
        this.openId = openId;
        this.isSubscriber = isSubscriber;
        this.reqTime = reqTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductFee() {
        return productFee;
    }

    public void setProductFee(String productFee) {
        this.productFee = productFee;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getBillCreateIp() {
        return billCreateIp;
    }

    public void setBillCreateIp(String billCreateIp) {
        this.billCreateIp = billCreateIp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getIsSubscriber() {
        return isSubscriber;
    }

    public void setIsSubscriber(String isSubscriber) {
        this.isSubscriber = isSubscriber;
    }

    public Date getReqTime() {
        return reqTime;
    }

    public void setReqTime(Date reqTime) {
        this.reqTime = reqTime;
    }

    public Date getRespTime() {
        return respTime;
    }

    public void setRespTime(Date respTime) {
        this.respTime = respTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", tId='" + tId + '\'' +
                ", system='" + system + '\'' +
                ", channel='" + channel + '\'' +
                ", product='" + product + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", productFee='" + productFee + '\'' +
                ", fee='" + fee + '\'' +
                ", billCreateIp='" + billCreateIp + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", openId='" + openId + '\'' +
                ", isSubscriber='" + isSubscriber + '\'' +
                ", reqTime=" + reqTime +
                ", respTime=" + respTime +
                ", result='" + result + '\'' +
                '}';
    }
}
