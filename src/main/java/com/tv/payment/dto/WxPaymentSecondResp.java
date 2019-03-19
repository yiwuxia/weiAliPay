package com.tv.payment.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/9.
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WxPaymentSecondResp implements Serializable {

    private static final long serialVersionUID = 1438034018520194210L;

    @XmlElement
    private String return_code;//����״̬��

    @XmlElement
    private String return_msg;//������Ϣ

    @XmlElement
    private String appid;//�����˺�ID

    @XmlElement
    private String mch_id;//�̻���

    @XmlElement
    private String device_info;//�豸��

    @XmlElement
    private String nonce_str;//����ַ���

    @XmlElement
    private String sign;//ǩ��

    @XmlElement
    private String result_code;//ҵ����

    @XmlElement
    private String err_code;//�������

    @XmlElement
    private String err_code_des;//�����������

    @XmlElement
    private String trade_type;//��������
    @XmlElement
    private String prepay_id;//Ԥ֧�����׻Ự��ʶ
    @XmlElement
    private String code_url;//��ά������
    @XmlElement
    private String orderId;//��ά������

    public WxPaymentSecondResp() {
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
