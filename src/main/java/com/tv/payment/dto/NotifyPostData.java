package com.tv.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * Created by Fujun on 2016/12/6.
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@JsonPropertyOrder({"OpenId", "AppId", "IsSubscribe", "TimeStamp","NonceStr","AppSignature","SignMethod"})
public class NotifyPostData implements Serializable {

    private static final long serialVersionUID = 14380340185301324L;

    @XmlAttribute
    private String OpenId;

    @XmlAttribute
    private String AppId;

    @XmlAttribute
    private String IsSubscribe;

    @XmlAttribute
    private String TimeStamp;

    @XmlAttribute
    private String NonceStr;

    @XmlAttribute
    private String AppSignature;

    @XmlAttribute
    private String SignMethod;

    public NotifyPostData() {
    }

    @XmlElement(name="OpenId")
    public String getOpenId() {
        return this.OpenId;
    }

    @XmlElement(name="AppId")
    public String getAppId() {
        return this.AppId;
    }

    @XmlElement(name="IsSubscribe")
    public String getIsSubscribe() {
        return this.IsSubscribe;
    }

    @XmlElement(name="TimeStamp")
    public String getTimeStamp() {
        return this.TimeStamp;
    }

    @XmlElement(name="NonceStr")
    public String getNonceStr() {
        return this.NonceStr;
    }

    @XmlElement(name="AppSignature")
    public String getAppSignature() {
        return this.AppSignature;
    }

    @XmlElement(name="SignMethod")
    public String getSignMethod() {
        return this.SignMethod;
    }

    public void setOpenId(String OpenId) {
        this.OpenId = OpenId;
    }

    public void setAppId(String AppId) {
        this.AppId = AppId;
    }

    public void setIsSubscribe(String IsSubscribe) {
        this.IsSubscribe = IsSubscribe;
    }

    public void setTimeStamp(String TimeStamp) {
        this.TimeStamp = TimeStamp;
    }

    public void setNonceStr(String NonceStr) {
        this.NonceStr = NonceStr;
    }

    public void setAppSignature(String AppSignature) {
        this.AppSignature = AppSignature;
    }

    public void setSignMethod(String SignMethod) {
        this.SignMethod = SignMethod;
    }

    @JsonIgnore
    @Override
    public String toString() {
        return "WxPackageRequest{" +
                "OpenId='" + OpenId + '\'' +
                ", AppId='" + AppId + '\'' +
                ", IsSubscribe='" + IsSubscribe + '\'' +
                ", TimeStamp='" + TimeStamp + '\'' +
                ", NonceStr='" + NonceStr + '\'' +
                ", AppSignature='" + AppSignature + '\'' +
                ", SignMethod='" + SignMethod + '\'' +
                '}';
    }
}
