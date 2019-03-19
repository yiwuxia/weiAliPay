package com.tv.payment.dto;

/**
 * Created by Administrator on 2017/8/18.
 */
public class AliPaymentInternalResp {

    private String orderId;

    private String aliOrderInfo;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAliOrderInfo() {
        return aliOrderInfo;
    }

    public void setAliOrderInfo(String aliOrderInfo) {
        this.aliOrderInfo = aliOrderInfo;
    }

    public AliPaymentInternalResp() {
    }

    @Override
    public String toString() {
        return "AliPaymentInternalResp{" +
                "orderId='" + orderId + '\'' +
                ", aliOrderInfo='" + aliOrderInfo + '\'' +
                '}';
    }
}
