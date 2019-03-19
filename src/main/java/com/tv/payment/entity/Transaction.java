package com.tv.payment.entity;

import java.util.Date;

public class Transaction {
    private Integer id;

    private String outTradeNo;

    private String returnCode;

    private String returnMsg;

    private String appid;

    private String mchId;

    private String deviceInfo;

    private String nonceStr;

    private String sign;

    private String signType;

    private String resultCode;

    private String errCode;

    private String errCodeDes;

    private String openid;

    private String isSubscribe;

    private String tradeType;

    private String bankType;

    private Long totalFee;

    private Long settlementTotalFee;

    private String feeType;

    private String transactionId;

    private String attach;

    private String timeEnd;

    private String tradeState;

    private Date reqTime;

    public Transaction() {
    }

    public Transaction(String outTradeNo, String returnCode, String returnMsg, String appid, String mchId, String deviceInfo, String nonceStr, String sign, String signType, String resultCode, String errCode, String errCodeDes, String openid, String isSubscribe, String tradeType, String bankType, Long totalFee, Long settlementTotalFee, String feeType, String transactionId, String attach, String timeEnd, String tradeState, Date reqTime) {
        this.outTradeNo = outTradeNo;
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
        this.appid = appid;
        this.mchId = mchId;
        this.deviceInfo = deviceInfo;
        this.nonceStr = nonceStr;
        this.sign = sign;
        this.signType = signType;
        this.resultCode = resultCode;
        this.errCode = errCode;
        this.errCodeDes = errCodeDes;
        this.openid = openid;
        this.isSubscribe = isSubscribe;
        this.tradeType = tradeType;
        this.bankType = bankType;
        this.totalFee = totalFee;
        this.settlementTotalFee = settlementTotalFee;
        this.feeType = feeType;
        this.transactionId = transactionId;
        this.attach = attach;
        this.timeEnd = timeEnd;
        this.tradeState = tradeState;
        this.reqTime = reqTime;
    }

    public Integer getId() {
        return id;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public String getAppid() {
        return appid;
    }

    public String getMchId() {
        return mchId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public String getSignType() {
        return signType;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public String getOpenid() {
        return openid;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public String getTradeType() {
        return tradeType;
    }

    public String getBankType() {
        return bankType;
    }

    public Long getTotalFee() {
        return totalFee;
    }

    public Long getSettlementTotalFee() {
        return settlementTotalFee;
    }

    public String getFeeType() {
        return feeType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAttach() {
        return attach;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public String getTradeState() {
        return tradeState;
    }

    public Date getReqTime() {
        return reqTime;
    }

    public void setReqTime(Date reqTime) {
        this.reqTime = reqTime;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public void setTotalFee(Long totalFee) {
        this.totalFee = totalFee;
    }

    public void setSettlementTotalFee(Long settlementTotalFee) {
        this.settlementTotalFee = settlementTotalFee;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }
}