package com.tv.payment.dto;

public class JsapiDto {

    private String noncestr;

    private String jsapi_ticket;

    private String timestamp;

    private String url;

    public JsapiDto() {
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getJsapi_ticket() {
        return jsapi_ticket;
    }

    public void setJsapi_ticket(String jsapi_ticket) {
        this.jsapi_ticket = jsapi_ticket;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "JsapiDto{" +
                "noncestr='" + noncestr + '\'' +
                ", jsapi_ticket='" + jsapi_ticket + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
