package com.tv.payment.dto;

public class JsapiReq {

    private String appId;

    private String jsapi_ticket;

    private String url;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getJsapi_ticket() {
        return jsapi_ticket;
    }

    public void setJsapi_ticket(String jsapi_ticket) {
        this.jsapi_ticket = jsapi_ticket;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JsapiReq() {
    }


    @Override
    public String toString() {
        return "JsapiReq{" +
                "appId='" + appId + '\'' +
                ", jsapi_ticket='" + jsapi_ticket + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
