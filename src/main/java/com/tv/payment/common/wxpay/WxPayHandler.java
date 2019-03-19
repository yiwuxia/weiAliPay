package com.tv.payment.common.wxpay;

import com.tv.payment.common.PayHandler;
import com.tv.payment.dto.*;
import com.tv.payment.entity.Order;
import com.tv.payment.service.OrderService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/6.
 *
 *
 */
@Component
public class WxPayHandler extends PayHandler{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    @Value("#{configProperties['mobile_appId']}")
    private String mobileAppId;

    @Value("#{configProperties['mobile_mchId']}")
    private String mobileMchId;

    @Value("#{configProperties['mobile_partnerKey']}")
    private String mobilePartnerKey;

    @Value("#{configProperties['appId']}")
    private String appId;

    @Value("#{configProperties['mch_id']}")
    private String mchId;

    @Value("#{configProperties['partnerKey']}")
    private String partnerKey;

    @Value("#{configProperties['wxUnifiedOrderUrl']}")
    private String wxUnifiedOrderUrl="https://api.mch.weixin.qq.com/pay/unifiedorder";

    @Value("#{configProperties['mobile_notify_url']}")
    private String mobile_notify_url;

    @Value("#{configProperties['notify_url']}")
    private String notify_url;

    public WxPaymentSecondResp createValuePayment(Order order, String tradeType) throws SDKRuntimeException {
        WxPaymentSecondReq wxPaymentSecondReq = new WxPaymentSecondReq();
        HashMap<String, String> parameters = new HashMap<String, String>();

        wxPaymentSecondReq.setBody(order.gettId());//Body

        Integer productFee = Integer.parseInt(order.getProductFee().trim())*100;//order中单位为元，发送给微信的接口中单位为分，数值需要*100
        logger.debug("getProductFee:"+order.getProductFee().trim()+".");


        wxPaymentSecondReq.setTotal_fee(Integer.toString(productFee));
//      wxPaymentSecondReq.setSpbill_create_ip(order.getBillCreateIp());
        wxPaymentSecondReq.setNotify_url(this.notify_url);
        wxPaymentSecondReq.setAppid(this.appId);
        wxPaymentSecondReq.setMch_id(this.mchId);
        wxPaymentSecondReq.setSpbill_create_ip("106.75.91.110");//线上环境
        if(tradeType.equals("APP"))
        {
            wxPaymentSecondReq.setSpbill_create_ip(order.getBillCreateIp());
            wxPaymentSecondReq.setAppid(this.mobileAppId);
            wxPaymentSecondReq.setMch_id(this.mobileMchId);
            wxPaymentSecondReq.setNotify_url(this.notify_url);
        }

        if(tradeType.equals("JSAPI"))
        {
            wxPaymentSecondReq.setOpenid(order.getOpenId());
            wxPaymentSecondReq.setNotify_url(this.mobile_notify_url);
            parameters.put("openid", wxPaymentSecondReq.getOpenid());
        }
        wxPaymentSecondReq.setNonce_str(order.getNoncestr());
        wxPaymentSecondReq.setAttach(order.getProductDesc());
        wxPaymentSecondReq.setOut_trade_no(order.getId());
        wxPaymentSecondReq.setTrade_type(tradeType);

        parameters.put("appid", wxPaymentSecondReq.getAppid());
        parameters.put("attach", wxPaymentSecondReq.getAttach());
        parameters.put("body", wxPaymentSecondReq.getBody());
        if(!isStringNull(wxPaymentSecondReq.getDevice_info()))
        {
            parameters.put("device_info", wxPaymentSecondReq.getDevice_info());
        }
        parameters.put("mch_id", wxPaymentSecondReq.getMch_id());
        parameters.put("nonce_str", wxPaymentSecondReq.getNonce_str());
        parameters.put("notify_url", wxPaymentSecondReq.getNotify_url());
        parameters.put("out_trade_no", wxPaymentSecondReq.getOut_trade_no());
//      parameters.put("sign_type", wxPaymentSecondReq.getSign_type());
        parameters.put("spbill_create_ip", wxPaymentSecondReq.getSpbill_create_ip());
        parameters.put("total_fee", wxPaymentSecondReq.getTotal_fee());
        parameters.put("trade_type", wxPaymentSecondReq.getTrade_type());

        if(tradeType.equals("APP"))
        {
            wxPaymentSecondReq.setSign(getMd5SignForApp(parameters));
        }
        else
        {
            wxPaymentSecondReq.setSign(getMd5Sign(parameters));
        }

        StringWriter sw = new StringWriter();
        String xmlString="";

        try {
            JAXBContext jc = JAXBContext.newInstance(com.tv.payment.dto.WxPaymentSecondReq.class);
            Marshaller m = jc.createMarshaller();

            m.marshal(wxPaymentSecondReq, sw);
            xmlString = sw.toString();
            logger.debug("xmlString:"+xmlString);
        } catch (JAXBException e) {
            logger.error(e.toString());
        }

        WxPaymentSecondResp  wxPaymentSecondResp= new WxPaymentSecondResp();
        String url = "";

        try {
           String result= this.post(this.wxUnifiedOrderUrl, xmlString);
            result = new String((result).getBytes("ISO-8859-1"),"UTF-8");

            JAXBContext jaxbContext = JAXBContext.newInstance(WxPaymentSecondResp.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            wxPaymentSecondResp = (WxPaymentSecondResp) jaxbUnmarshaller.unmarshal(new StringReader(result));

            logger.debug("result:"+result);
            logger.debug("wxPaymentSecondResp:"+wxPaymentSecondResp);
        } catch (Exception e) {
            logger.error(e.toString());
        }

        if(tradeType.equals("NATIVE"))
        {
            if("SUCCESS".indexOf(wxPaymentSecondResp.getReturn_code()) != -1 && "SUCCESS".indexOf(wxPaymentSecondResp.getResult_code()) != -1 )
            {
                url= wxPaymentSecondResp.getCode_url();
                order.setResult("00");//生成二维码成功
                orderService.updateOrder(order);
            }
        }else
        {
            if("SUCCESS".indexOf(wxPaymentSecondResp.getReturn_code()) != -1)
            {
                url=wxPaymentSecondResp.getPrepay_id();//prepay_id
                order.setResult("00");//
                orderService.updateOrder(order);
            }
        }
        return wxPaymentSecondResp;
    }


    public String getMd5Sign(HashMap<String, String> parameters) throws SDKRuntimeException {

        String unSignParaString = CommonUtil.FormatBizQueryParaMap(parameters,
                false);
        return MD5SignUtil.Sign(unSignParaString, this.partnerKey);
    }

    public String getMd5SignForApp(HashMap<String, String> parameters) throws SDKRuntimeException {

        String unSignParaString = CommonUtil.FormatBizQueryParaMap(parameters,
                false);
        return MD5SignUtil.Sign(unSignParaString, this.mobilePartnerKey);
    }

    public String getMd5SignCaseSensitive(HashMap<String, String> parameters) throws SDKRuntimeException {

        String unSignParaString = CommonUtil.FormatBizQueryParaMapCaseSensitive(parameters,
                false);
//        String paraString = CommonUtil.FormatBizQueryParaMap(parameters, true);
//        return paraString + "&sign="
//                + MD5SignUtil.Sign(unSignParaString, partnerKey);
        return MD5SignUtil.Sign(unSignParaString, this.partnerKey);
    }

    public String  post(String url, String xmlString) throws Exception{
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        String xml = xmlString;
        HttpEntity entity = new ByteArrayEntity(xml.getBytes("UTF-8"));
        post.setEntity(entity);
        HttpResponse response;
        response = client.execute(post);
        return EntityUtils.toString(response.getEntity());
    }

    /**
     * 比对微信支付notify时候的签名
     * @param WxPaymentNotifyReq
     * @return
     * @throws SDKRuntimeException
     */
    public boolean compareWxNotifySign(WxPaymentNotifyReq wxPaymentNotifyReq) throws SDKRuntimeException
    {
        HashMap<String, String> nativeObj = new HashMap<String, String>();

        nativeObj.put("return_code", wxPaymentNotifyReq.getReturn_code());//return_code

        if(!isStringNull(wxPaymentNotifyReq.getReturn_msg()))//return_msg
        {
            nativeObj.put("return_msg", wxPaymentNotifyReq.getReturn_msg());
        }
        if(!isStringNull(wxPaymentNotifyReq.getAppid()))//appid
        {
            nativeObj.put("appid", wxPaymentNotifyReq.getAppid());
        }
        if(!isStringNull(wxPaymentNotifyReq.getMch_id()))//mch_id
        {
            nativeObj.put("mch_id", wxPaymentNotifyReq.getMch_id());
        }
        if(!isStringNull(wxPaymentNotifyReq.getDevice_info()))//device_info
        {
            nativeObj.put("device_info", wxPaymentNotifyReq.getDevice_info());
        }
        if(!isStringNull(wxPaymentNotifyReq.getNonce_str()))//nonce_str
        {
            nativeObj.put("nonce_str", wxPaymentNotifyReq.getNonce_str());
        }
        if(!isStringNull(wxPaymentNotifyReq.getSign_type()))//sign_type
        {
            nativeObj.put("sign_type", wxPaymentNotifyReq.getSign_type());
        }
        if(!isStringNull(wxPaymentNotifyReq.getResult_code()))//result_code
        {
            nativeObj.put("result_code", wxPaymentNotifyReq.getResult_code());
        }
        if(!isStringNull(wxPaymentNotifyReq.getErr_code()))//err_code
        {
            nativeObj.put("err_code", wxPaymentNotifyReq.getErr_code());
        }
        if(!isStringNull(wxPaymentNotifyReq.getErr_code_des()))//err_code_des
        {
            nativeObj.put("err_code_des", wxPaymentNotifyReq.getErr_code_des());
        }
        if(!isStringNull(wxPaymentNotifyReq.getOpenid()))//openid
        {
            nativeObj.put("openid", wxPaymentNotifyReq.getOpenid());
        }
        if(!isStringNull(wxPaymentNotifyReq.getIs_subscribe()))//is_subscribe
        {
            nativeObj.put("is_subscribe", wxPaymentNotifyReq.getIs_subscribe());
        }
        if(!isStringNull(wxPaymentNotifyReq.getTrade_type()))//trade_type
        {
            nativeObj.put("trade_type", wxPaymentNotifyReq.getTrade_type());
        }
        if(!isStringNull(wxPaymentNotifyReq.getBank_type()))//bank_type
        {
            nativeObj.put("bank_type", wxPaymentNotifyReq.getBank_type());
        }
        if(!isDigitalNull(wxPaymentNotifyReq.getTotal_fee()))//total_fee
        {
            nativeObj.put("total_fee", String.valueOf(wxPaymentNotifyReq.getTotal_fee()));
        }
        if(!isDigitalNull(wxPaymentNotifyReq.getSettlement_total_fee()))//settlement_total_fee
        {
            nativeObj.put("settlement_total_fee", String.valueOf(wxPaymentNotifyReq.getSettlement_total_fee()));
        }
        if(!isStringNull(wxPaymentNotifyReq.getFee_type()))//fee_type
        {
            nativeObj.put("fee_type", wxPaymentNotifyReq.getFee_type());
        }
        if(!isDigitalNull(wxPaymentNotifyReq.getCash_fee()))//cash_fee
        {
            nativeObj.put("cash_fee", String.valueOf(wxPaymentNotifyReq.getCash_fee()));
        }
        if(!isStringNull(wxPaymentNotifyReq.getCash_fee_type()))//cash_fee_type
        {
            nativeObj.put("cash_fee_type", wxPaymentNotifyReq.getCash_fee_type());
        }
        if(!isDigitalNull(wxPaymentNotifyReq.getCoupon_fee()))//coupon_fee
        {
            nativeObj.put("coupon_fee", String.valueOf(wxPaymentNotifyReq.getCoupon_fee()));
        }
        if(!isDigitalNull(wxPaymentNotifyReq.getCoupon_count()))//coupon_count
        {
            nativeObj.put("coupon_count", String.valueOf(wxPaymentNotifyReq.getCoupon_count()));
        }
        if(!isDigitalNull(wxPaymentNotifyReq.getCoupon_type_$n()))//coupon_type_$n
        {
            nativeObj.put("coupon_type_$n", String.valueOf(wxPaymentNotifyReq.getCoupon_type_$n()));
        }
        if(!isStringNull(wxPaymentNotifyReq.getCoupon_id_$n()))//coupon_id_$n
        {
            nativeObj.put("coupon_id_$n", wxPaymentNotifyReq.getCoupon_id_$n());
        }
        if(!isDigitalNull(wxPaymentNotifyReq.getCoupon_fee_$n()))//coupon_fee_$n
        {
            nativeObj.put("coupon_fee_$n", String.valueOf(wxPaymentNotifyReq.getCoupon_fee_$n()));
        }
        if(!isStringNull(wxPaymentNotifyReq.getTransaction_id()))//transaction_id
        {
            nativeObj.put("transaction_id", wxPaymentNotifyReq.getTransaction_id());
        }
        if(!isStringNull(wxPaymentNotifyReq.getOut_trade_no()))//out_trade_no
        {
            nativeObj.put("out_trade_no", wxPaymentNotifyReq.getOut_trade_no());
        }
        if(!isStringNull(wxPaymentNotifyReq.getAttach()))//attach
        {
            nativeObj.put("attach", wxPaymentNotifyReq.getAttach());
        }
        if(!isStringNull(wxPaymentNotifyReq.getTime_end()))//time_end
        {
            nativeObj.put("time_end", wxPaymentNotifyReq.getTime_end());
        }


        String appSignature = "";
        try {
            appSignature= getMd5Sign(nativeObj);
            logger.debug("appSignature:"+appSignature);
            logger.debug("sign:"+wxPaymentNotifyReq.getSign());
        } catch (SDKRuntimeException e) {
            e.printStackTrace();
        }

        return appSignature.equalsIgnoreCase(wxPaymentNotifyReq.getSign());
    }


    public WxPaymentMobileReq  createWxPaymentMobileReq(Order order,String prepay_id, String orderId)
    {
        WxPaymentMobileReq wxPaymentMobileReq = new WxPaymentMobileReq();
        wxPaymentMobileReq.setTimeStamp(String.valueOf(new Date().getTime() / 1000));
        wxPaymentMobileReq.setNonceStr(CommonUtil.CreateNoncestr());
        wxPaymentMobileReq.setPackageStr("prepay_id=" + prepay_id);
        wxPaymentMobileReq.setSignType("MD5");
        wxPaymentMobileReq.setOrderId(orderId);

        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("appId", wxPaymentMobileReq.getAppId());
        parameters.put("nonceStr", wxPaymentMobileReq.getNonceStr());
        parameters.put("package", wxPaymentMobileReq.getPackageStr());
        parameters.put("signType", wxPaymentMobileReq.getSignType());
        parameters.put("timeStamp", wxPaymentMobileReq.getTimeStamp());

        try {
            wxPaymentMobileReq.setPaySign(getMd5SignCaseSensitive(parameters));
        } catch (SDKRuntimeException e) {
            e.printStackTrace();
        }

        logger.debug("wxPaymentMobileReq:"+wxPaymentMobileReq.toString());
        return  wxPaymentMobileReq;
    }

    public WxPaymentInternalResp toWxPaymentInternalResp(WxPaymentSecondResp wxPaymentSecondResp, String pay_type) throws SDKRuntimeException
    {
        WxPaymentInternalResp wxPaymentInternalResp = new WxPaymentInternalResp();
        wxPaymentInternalResp.setAppid(wxPaymentSecondResp.getAppid());
        wxPaymentInternalResp.setPartnerid(wxPaymentSecondResp.getMch_id());
        wxPaymentInternalResp.setPrepayid(wxPaymentSecondResp.getPrepay_id());
        wxPaymentInternalResp.setPackage_str("Sign=WXPay");
        wxPaymentInternalResp.setNoncestr(wxPaymentSecondResp.getNonce_str());
        wxPaymentInternalResp.setTimestamp(String.valueOf(new Date().getTime() / 1000));
        wxPaymentInternalResp.setOrderId(wxPaymentSecondResp.getOrderId());
        wxPaymentInternalResp.setCode_url(wxPaymentSecondResp.getCode_url());


        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("appid", wxPaymentInternalResp.getAppid());
        parameters.put("partnerid", wxPaymentInternalResp.getPartnerid());
        parameters.put("prepayid", wxPaymentInternalResp.getPrepayid());
        parameters.put("package", wxPaymentInternalResp.getPackage_str());
        parameters.put("noncestr", wxPaymentInternalResp.getNoncestr());
        parameters.put("timestamp", wxPaymentInternalResp.getTimestamp());

        if(pay_type.equals("app"))
        {
            wxPaymentInternalResp.setSign(getMd5SignForApp(parameters));
        }
        else
        {
            wxPaymentInternalResp.setSign(getMd5Sign(parameters));
        }

        logger.debug("wxPaymentInternalResp:"+wxPaymentInternalResp.toString());
        return wxPaymentInternalResp;
    }


    private boolean isStringNull(String value)
    {
        return (null == value);
        //return (null == value && "null".equals(value));
    }

    private boolean isDigitalNull(Integer i) {return null==i;}

}
