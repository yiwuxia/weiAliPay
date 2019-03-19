package com.tv.payment.dto;

/**
 * Created by Fujun on 2016/12/5.
 */
//����Ajax����ķ�������
public class PaymentResult<T> {

    private boolean success;

    private T data;

    private String msg;

    public PaymentResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public PaymentResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getmsg() {
        return msg;
    }

    public void setmsg(String msg) {
        this.msg = msg;
    }
}
