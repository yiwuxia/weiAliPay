package com.tv.payment.dto;

import org.springframework.beans.factory.annotation.Value;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/9.
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"appid", "mch_id", "device_info", "nonce_str","sign","body","attach","out_trade_no","total_fee","spbill_create_ip","notify_url","trade_type","openid"})
public class WxPaymentSecondReq implements Serializable {
    private static final long serialVersionUID = 1438034018530184210L;

    @XmlElement
    @Value("#{configProperties['appId']}")
    private String appid;//�����˺�ID

    @XmlElement
    @Value("#{configProperties['mch_id']}")
    private String mch_id;//�̻���

    @XmlElement
    private String device_info;//�豸��

    @XmlElement
    private String nonce_str;//����ַ���

    @XmlElement
    private String sign;//ǩ��

/*    @XmlElement
    private String sign_type="MD5";//ǩ�����ͣ�Ĭ��ΪMD5��֧��HMAC-SHA256��MD5��*/

    @XmlElement
    private String body;//��Ʒ����:������򿪵���վ��ҳtitle�� -��Ʒ����(���÷���)

/*    @XmlElement
    private String detail;//��Ʒ����*/

    @XmlElement
    private String attach;//��������,�ڲ�ѯAPI��֧��֪ͨ��ԭ�����أ�����Ϊ�Զ������ʹ�á�

    @XmlElement
    private String out_trade_no;//�̻�������

//    @XmlElement
//    private String fee_type;//��۱���

    @XmlElement
    private String total_fee;//��۽��(���÷���)

    @XmlElement
    private String spbill_create_ip;//�ն�IP(���÷���)

/*    @XmlElement
    private String time_start;//������ʼʱ��

    @XmlElement
    private String time_expire;//���׽���ʱ��

    @XmlElement
    private String goods_tag;//��Ʒ���*/

    @XmlElement
    @Value("#{configProperties['notify_url']}")
    private String notify_url;//֪ͨ��ַ

    @XmlElement
    private String trade_type;//��������:JSAPI--���ں�֧����NATIVE--ԭ��ɨ��֧����APP--app֧��

/*    @XmlElement
    private String product_id;//��ƷID

    @XmlElement
    private String limit_pay;//ָ��֧����ʽ*/

    @XmlElement
    private String openid;//�û���ʶ


    public WxPaymentSecondReq() {
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

/*    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }*/

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Override
    public String toString() {
        return "WxPaymentSecondReq{" +
                "appid='" + appid + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", device_info='" + device_info + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", sign='" + sign + '\'' +
                ", body='" + body + '\'' +
                ", attach='" + attach + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", total_fee='" + total_fee + '\'' +
                ", spbill_create_ip='" + spbill_create_ip + '\'' +
                ", notify_url='" + notify_url + '\'' +
                ", trade_type='" + trade_type + '\'' +
                ", openid='" + openid + '\'' +
                '}';
    }
}
