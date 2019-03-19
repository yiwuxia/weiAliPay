package com.tv.payment.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * Created by Fujun on 2016/12/6.
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@JsonPropertyOrder({"AppId", "Package", "TimeStamp", "NonceStr","RetCode","RetErrMsg","AppSignature","SignMethod"})
public class WxPackageResponse implements Serializable{

    private static final long serialVersionUID = 1237078326797917L;

    private String AppId;

    private String Package;

    private String TimeStamp;

    private String NonceStr;

    private String RetCode;

    private String RetErrMsg;

    private String AppSignature;

    private String SignMethod;

    public WxPackageResponse() {
    }

    @XmlElement(name="AppId")
    public String getAppId() {
        return AppId;
    }

    @XmlElement(name="Package")
    public String getPackage() {
        return Package;
    }

    @XmlElement(name="TimeStamp")
    public String getTimeStamp() {
        return TimeStamp;
    }

    @XmlElement(name="NonceStr")
    public String getNonceStr() {
        return NonceStr;
    }

    @XmlElement(name="RetCode")
    public String getRetCode() {
        return RetCode;
    }

    @XmlElement(name="RetErrMsg")
    public String getRetErrMsg() {
        return RetErrMsg;
    }

    @XmlElement(name="AppSignature")
    public String getAppSignature() {
        return AppSignature;
    }

    @XmlElement(name="SignMethod")
    public String getSignMethod() {
        return SignMethod;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }

    public void setPackage(String aPackage) {
        Package = aPackage;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public void setNonceStr(String nonceStr) {
        NonceStr = nonceStr;
    }

    public void setRetCode(String retCode) {
        RetCode = retCode;
    }

    public void setRetErrMsg(String retErrMsg) {
        RetErrMsg = retErrMsg;
    }

    public void setAppSignature(String appSignature) {
        AppSignature = appSignature;
    }

    public void setSignMethod(String signMethod) {
        SignMethod = signMethod;
    }

    @Override
    public String toString() {
        return "WxPackageResponse{" +
                "AppId='" + AppId + '\'' +
                ", Package='" + Package + '\'' +
                ", TimeStamp='" + TimeStamp + '\'' +
                ", NonceStr='" + NonceStr + '\'' +
                ", RetCode='" + RetCode + '\'' +
                ", RetErrMsg='" + RetErrMsg + '\'' +
                ", AppSignature='" + AppSignature + '\'' +
                ", SignMethod='" + SignMethod + '\'' +
                '}';
    }
}
