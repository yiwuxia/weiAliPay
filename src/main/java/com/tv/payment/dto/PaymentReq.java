package com.tv.payment.dto;

/**
 * Created by Fujun on 2016/12/6.
 */
public class PaymentReq {
       /*
  systemId
  channelId:资源
  productId
    系统id：
      官网: 01
            资源：虚拟币 01
            商品:U币    01
      问答：03
             资源：
                 提问 01
                 旁听 02
             商品：
                 问答id
      讲座: 02
             资源：
                报名费 01
             商品：讲座id
      课程系统:04
            资源：
               买课程；01
               商品:课程id

               包月：02
               商品：老师uid
  productDesc 商品描述
  productFee 商品价格，单位:元
  spbillCreateIp 订单生成的机器ip,ipv4
  openId  用户的微信Id
  issubscribe 是否已经注册 1:已经注册



  body 商品描述
  total_fee 订单总金额
  spbill_create_ip 订单生成的机器ip,ipv4
  product_fee 商品费用
  goods_tag 商品标记(预留，可以为空)
     */

    /**
     * 系统ID
     */
    private String systemId;

    /**
     * 频道ID
     */
    private String channelId;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 商品描述
     */
    private String productDesc;

    /**
     * 商品费用
     */
    private String productFee;

    /**
     * 订单生成的机器ip,ipv4
     */
    private String spbillCreateIp;

    private String body;
    /**
     * 用户的微信Id
     */
    private String openId;

    /**
     * 是否已经注册
     */
    private String issubscribe;


    public PaymentReq() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductFee() {
        return productFee;
    }

    public void setProductFee(String productFee) {
        this.productFee = productFee;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getIssubscribe() {
        return issubscribe;
    }

    public void setIssubscribe(String issubscribe) {
        this.issubscribe = issubscribe;
    }

    public static final class System{
        public static final String MAINWEB ="01";

        public static final String LECTURE ="02";

        public static final String QANDA ="03";
    }

}
