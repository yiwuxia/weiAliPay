package com.tv.payment.common.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.tv.payment.common.PayHandler;
import com.tv.payment.dto.AliPaymentInternalResp;
import com.tv.payment.entity.Order;
import com.tv.payment.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/15.
 */
@Component
public class AliPayHandler extends PayHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("#{configProperties['ali_appId']}")
    private String aliAppId;

    @Value("#{configProperties['ali_app_private_key']}")
    private String aliAppPrivateKey;

    @Value("#{configProperties['alipay_public_key']}")
    private String aliPayPublicKey;

    @Value("#{configProperties['aliNotifyResultUrl']}")
    private String aliNotifyResultUrl;

    @Autowired
    private OrderService orderService;

    public AliPaymentInternalResp createAppPayment(Order order) throws AlipayApiException
    {
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", aliAppId, aliAppPrivateKey, "json", "utf-8", aliPayPublicKey, "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayResponse response;
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(order.getProductDesc());
        model.setSubject(order.getProductDesc());
        model.setOutTradeNo(order.getId());
        model.setTimeoutExpress("30m");
        model.setTotalAmount(order.getProductFee());
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(aliNotifyResultUrl);
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            response = alipayClient.sdkExecute(request);
            logger.debug("Ali Pay response is :" + response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。

            order.setResult("00");//
            orderService.updateOrder(order);
        } catch (AlipayApiException e) {
            throw e;
        }
        AliPaymentInternalResp aliPaymentInternalResp = new AliPaymentInternalResp();
        aliPaymentInternalResp.setAliOrderInfo(response.getBody());

        return aliPaymentInternalResp;
    }

    public AliPaymentInternalResp createNativePayment(Order order) throws AlipayApiException
    {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", aliAppId, aliAppPrivateKey, "json",  "utf-8", aliPayPublicKey, "RSA2");                //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl("");
        alipayRequest.setNotifyUrl(aliNotifyResultUrl);//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\""+order.getId()+"\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":"+order.getProductFee()+"," +
                "    \"subject\":\""+order.getProductDesc()+"\"," +
                "    \"body\":\""+order.getProductDesc()+"\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088221727930054\"" +
                "    }" +
                "  }");//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        AliPaymentInternalResp aliPaymentInternalResp = new AliPaymentInternalResp();
        aliPaymentInternalResp.setAliOrderInfo(form);

        return aliPaymentInternalResp;
    }

    /**
     *
     * @param params
     * @return
     * @throws AlipayApiException
     */
    public boolean rsaCheckV1(Map params) throws AlipayApiException
    {
        return AlipaySignature.rsaCheckV1(params, aliPayPublicKey, "utf-8", "RSA2");
    }
}
