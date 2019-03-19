package com.tv.payment.dto;

/**
 * Created by Fujun on 2016/12/6.
 */
public class PaymentResp {

    private String payUrl;

    private String orderId;

    public PaymentResp(String payUrl, String orderId) {
        this.payUrl = payUrl;
        this.orderId = orderId;
    }

    public PaymentResp() {
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
