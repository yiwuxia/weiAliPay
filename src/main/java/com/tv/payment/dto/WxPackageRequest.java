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
@JsonPropertyOrder({"OpenId", "AppId", "IsSubscribe", "ProductId","TimeStamp","NonceStr","AppSignature","SignMethod"})
public class WxPackageRequest implements Serializable {

    private static final long serialVersionUID = 1438034018530184210L;

    @XmlAttribute
    private String OpenId;

    @XmlAttribute
    private String AppId;

    @XmlAttribute
    private String IsSubscribe;

    @XmlAttribute
    private String ProductId;

    @XmlAttribute
    private String TimeStamp;

    @XmlAttribute
    private String NonceStr;

    @XmlAttribute
    private String AppSignature;

    @XmlAttribute
    private String SignMethod;

    public WxPackageRequest() {
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

    @XmlElement(name="ProductId")
    public String getProductId() {
        return this.ProductId;
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

    public void setProductId(String ProductId) {
        this.ProductId = ProductId;
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
                ", ProductId='" + ProductId + '\'' +
                ", TimeStamp='" + TimeStamp + '\'' +
                ", NonceStr='" + NonceStr + '\'' +
                ", AppSignature='" + AppSignature + '\'' +
                ", SignMethod='" + SignMethod + '\'' +
                '}';
    }
}
