package com.tv.payment.web;

import com.alipay.api.AlipayApiException;
import com.tv.payment.common.DateUtils;
import com.tv.payment.common.alipay.AliPayHandler;
import com.tv.payment.dto.AliPaymentInternalResp;
import com.tv.payment.dto.PaymentResp;
import com.tv.payment.dto.PaymentResult;
import com.tv.payment.dto.PaymentReq;
import com.tv.payment.entity.Order;
import com.tv.payment.service.OrderService;
import com.tv.payment.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/15.
 */
@Controller
@RequestMapping("/aliPayment")
public class AliPayController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;


    @Autowired
    private AliPayHandler aliPayHandler;

    /**
     * APP֧��
     * @param paymentReq
     * @return �ṩ֧��url��1234tv��ϵͳ
     */
    @RequestMapping(value ="/create/ali/payurl", method = RequestMethod.POST)
    @ResponseBody
    public PaymentResult createPayUrl(@RequestBody PaymentReq paymentReq){


        logger.info("PaymentReq is" + paymentReq.toString());
        //非空检查
        if(!orderService.checkWxOrder(paymentReq))
        {
            return new PaymentResult(false, "参数错误！");
        }

        Date orderTime = new Date();
        String orderId = DateUtils.getDateTime(orderTime, DateUtils.ORDER_DATETIME_FORMAT);
        
        PaymentResult<PaymentResp> result = null;
        AliPaymentInternalResp response;
        if(!orderService.saveWxOrder(orderId, paymentReq, orderTime))
        {
            return new PaymentResult(false, "支付服务器内部错误！");
        }
        else
        {
            Order order = orderService.queryOrderById(orderId);
            try {
                if (order.getOpenId().equalsIgnoreCase("app"))//
                {
                    response = aliPayHandler.createAppPayment(order);
                    response.setOrderId(orderId);
                    logger.debug("AliPaymentInternalResp is " + response);
                    result = new PaymentResult(true, response);
                }
                else//扫码支付
                {
                    response = aliPayHandler.createNativePayment(order);
                     response.setOrderId(orderId);
                logger.debug("AliPaymentInternalResp is " + response);
                result = new PaymentResult(true, response);
                }

            } catch (AlipayApiException e) {
                logger.error("支付宝支付异常!",e);
                result = new PaymentResult(false, "支付宝支付异常!");
            }
        }
        return result;
    }

    /**
     * ����֧����֧��֪ͨ�Ľӿ�
     * @param
     * @return
     */
    @RequestMapping(value="/pay/ali/notify", method = RequestMethod.POST)
    @ResponseBody
    public void aliNotify1234Tv(HttpServletRequest request, HttpServletResponse response)
    {
        //��ȡ֧����POST����������Ϣ
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //����������δ����ڳ�������ʱʹ�á�
            try {
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put(name, valueStr);
        }
        //�м�alipaypublickey��֧�����Ĺ�Կ����ȥopen.alipay.com��ӦӦ���²鿴��
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        String[] p1 = (String[])requestParams.get("out_trade_no");
        String outTradeNo = p1[0];//商户订单号
        String[] p2 = (String[])requestParams.get("trade_status");
        String tradeStatus = p2[0];
        Order order = orderService.queryOrderById(outTradeNo);
        try {
            String returnContent="";

            if(tradeStatus.equals("TRADE_SUCCESS"))//aliPayHandler.rsaCheckV1(params)
            {
                String jsonString = "{\"result\":true,\"order_id\":\""+outTradeNo+"\",\"time_end\":\""+ new Date()+"\"}";
                logger.debug("jsonString:" + jsonString);
                if ("04".equals(order.getSystem())) {
                	returnContent = aliPayHandler.postWxPayCourseNotifyToTvWeb(jsonString);
				}else{
					returnContent = aliPayHandler.postWxPayNotifyToTvWeb(jsonString);
				}
                		
                logger.debug("returnContent:" + returnContent);
                order.setResult("01");
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().write("success");
                	
            }
            else
            {
                String jsonString = "{\"result\":false,\"order_id\":\""+outTradeNo+"\",\"time_end\":\""+ new Date()+"\"}";
                logger.debug("jsonString:" + jsonString);
                if ("04".equals(order.getSystem())) {
                	returnContent = aliPayHandler.postWxPayCourseNotifyToTvWeb(jsonString);
				}else{
				      returnContent = aliPayHandler.postWxPayNotifyToTvWeb(jsonString);
				}
                logger.debug("returnContent:" + returnContent);
                order.setResult("02");//失败
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().write("failure");
            }
        order.setRespTime(new Date());
        orderService.updateOrder(order);
            response.getWriter().flush();
            response.getWriter().close();
        }catch (IOException e) {
            e.printStackTrace();
        }
/*        catch (AlipayApiException e) {
            e.printStackTrace();
        }*/

    }
}
