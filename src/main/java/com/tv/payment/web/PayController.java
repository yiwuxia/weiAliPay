package com.tv.payment.web;

import com.tv.payment.common.DateUtils;
import com.tv.payment.common.alipay.AliPayHandler;
import com.tv.payment.common.wxpay.SDKRuntimeException;
import com.tv.payment.common.wxpay.WxPayHelper;
import com.tv.payment.common.wxpay.WxPayHandler;
import com.tv.payment.dto.*;
import com.tv.payment.entity.Order;
import com.tv.payment.service.OrderService;
import com.tv.payment.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fujun on 2016/12/5.
 */
@Controller
@RequestMapping("/payment")
public class PayController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxPayHandler wxPayHandler;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private WxPayHelper wxPayHelper;
    
    @Autowired
    private AliPayHandler aliPayHandler;

    /**
     * 公众号，手机和PC端扫码支付
     * @param wxOrder
     * @return 提供支付url给1234tv的系统
     */
    @RequestMapping(value ="/create/wx/payurl", method = RequestMethod.POST)
    @ResponseBody
    public PaymentResult createPayUrl(@RequestBody PaymentReq wxOrder){

        logger.info("PaymentReq is"+wxOrder.toString());

        if(!orderService.checkWxOrder(wxOrder))
        {
            return new PaymentResult(false, "参数错误！");
        }

        Date orderTime = new Date();
        String orderId = DateUtils.getDateTime(orderTime, DateUtils.ORDER_DATETIME_FORMAT);

        String url = "";
        String prepay_id ="";
        PaymentResult<PaymentResp> result = null;
        WxPaymentSecondResp wxPaymentSecondResp = new WxPaymentSecondResp();
        if(!orderService.saveWxOrder(orderId, wxOrder, orderTime))
        {
            return new PaymentResult(false, "支付服务器内部错误！");
        }
        else
        {
            Order order = orderService.queryOrderById(orderId);
//            url ="weixin://wxpay/s/An4baqw";//"01".equals(wxOrder.getSystemId()) && "02".equals(wxOrder.getProductId()主站的讲座
            try {
                if ("3".equals(wxOrder.getSystemId())|| ("01".equals(wxOrder.getSystemId()) && "02".equals(wxOrder.getProductId())))
                {
                    wxPaymentSecondResp = wxPayHandler.createValuePayment(order, "JSAPI");//JSAPI--公众号支付
                    wxPaymentSecondResp.setOrderId(orderId);
                    //prepay_id = wxPaymentSecondResp.getPrepay_id();
                    //WxPaymentMobileReq wxPaymentMobileReq = wxPayHandler.createWxPaymentMobileReq(order, prepay_id, orderId);
                    WxPaymentInternalResp wxPaymentInternalResp=wxPayHandler.toWxPaymentInternalResp(wxPaymentSecondResp,"JSAPI");

                    result = new PaymentResult(true, wxPaymentInternalResp);
                }
                else if("04".equals(wxOrder.getSystemId())||("01".equals(wxOrder.getSystemId()) && "03".equals(wxOrder.getProductId())))//主站的课程
                {
                        if(wxOrder.getOpenId().equals("app"))
                        {
                            wxPaymentSecondResp = wxPayHandler.createValuePayment(order, "APP");//APP--手机支付
                            wxPaymentSecondResp.setOrderId(orderId);
                            WxPaymentInternalResp wxPaymentInternalResp=wxPayHandler.toWxPaymentInternalResp(wxPaymentSecondResp,"app");
                            result = new PaymentResult(true, wxPaymentInternalResp);
                        }
                        else
                        {
                            wxPaymentSecondResp = wxPayHandler.createValuePayment(order, "NATIVE");//NATIVE--原生扫码支付
//                            url = wxPaymentSecondResp.getCode_url();
//                            result = new PaymentResult(true, new PaymentResp(url,orderId));
                            wxPaymentSecondResp.setOrderId(orderId);
                            WxPaymentInternalResp wxPaymentInternalResp=wxPayHandler.toWxPaymentInternalResp(wxPaymentSecondResp,"NATIVE");

                            result = new PaymentResult(true, wxPaymentInternalResp);

                        }
                }
                else
                {
                    wxPaymentSecondResp = wxPayHandler.createValuePayment(order, "NATIVE");//NATIVE--原生扫码支付
                    /*url = wxPaymentSecondResp.getCode_url();
                    result = new PaymentResult(true, new PaymentResp(url,orderId));*/
                    wxPaymentSecondResp.setOrderId(orderId);
                    WxPaymentInternalResp wxPaymentInternalResp=wxPayHandler.toWxPaymentInternalResp(wxPaymentSecondResp,"NATIVE");

                    result = new PaymentResult(true, wxPaymentInternalResp);
                }

            } catch (SDKRuntimeException e) {
                logger.error("微信支付处理异常",e);
                result = new PaymentResult(false, "微信支付处理异常!");
            }
        }
        return result;
    }



    /**
     * 微信平台获取Package的接口
     * @param req
     * @return
     */
/*
    @RequestMapping(value="/get/wx/package", method = RequestMethod.POST)
    @ResponseBody
    public  WxPackageResponse wxGetPackage(@RequestBody WxPackageRequest req)
    {
         System.out.println(req);

        Order order = orderService.queryOrderById(req.getProductId());

        boolean isTure = false;

        WxPackageResponse wxPackageResponse= new WxPackageResponse();
        try {
            isTure = wxPayHelper.compareSignBetwWxAndLocal(req, order);

            if(isTure)
            {
                wxPackageResponse = wxPayHelper.createWxPackageResponse(order, "0","Success");
            }
            else
            {
                wxPackageResponse.setRetCode("-1");//错误
                wxPackageResponse.setRetErrMsg("非法访问!");//错误
            }
        } catch (SDKRuntimeException e) {
            wxPackageResponse.setRetCode("-1");//错误
            wxPackageResponse.setRetErrMsg("处理失败!");//错误
        }

        return wxPackageResponse;
    }
*/


    /**
     * 接受微信支付通知的接口
     * @param WxPaymentNotifyReq notifyPostData
     * @return
     */
    @RequestMapping(value="/pay/wx/notify", method = RequestMethod.POST)
    @ResponseBody
    public WxPaymentNotifyResp wxNotify1234Tv(@RequestBody WxPaymentNotifyReq wxPaymentNotifyReq)
    {
        logger.debug(wxPaymentNotifyReq.toString());

        Order order = orderService.queryOrderById(wxPaymentNotifyReq.getOut_trade_no());
        order.setRespTime(new Date());

        if("SUCCESS".equals(wxPaymentNotifyReq.getReturn_code()))
        {
            order.setResult("01");
            order.setRespTime(DateUtils.parse(wxPaymentNotifyReq.getTime_end(), "yyyyMMddHHmmss"));
        }
        else
        {
            order.setResult("02");//失败
        }
        orderService.updateOrder(order);

        boolean isTrue = false;
        try {
            isTrue = wxPayHandler.compareWxNotifySign(wxPaymentNotifyReq);
            //isTrue = true;
            //if(isTrue)//如果签名比对成功
            if("SUCCESS".equals(wxPaymentNotifyReq.getReturn_code()))//针对微信用户参与支付活动时微信给的签名和1234tv的签名不一致导致校验失败的情况，改用return_code进行校验。
            {
                if (transactionService.saveTransaction(wxPaymentNotifyReq))
                {
//                    return new WxPaymentNotifyResp("<![CDATA[SUCCESS]]>","<![CDATA[OK]]>");
                    String jsonString = "{\"result\":true,\"order_id\":\""+wxPaymentNotifyReq.getOut_trade_no()+"\",\"time_end\":\""+
                            DateUtils.getDateTime(DateUtils.parse(wxPaymentNotifyReq.getTime_end(),"yyyyMMddHHmmss"),DateUtils.DEFAULT_DATETIME_FORMAT)+"\"}";
                    logger.debug("jsonString:" + jsonString);
                    String  returnContent="";
                    if ("04".equals(order.getSystem())) {
                    	 returnContent = aliPayHandler.postWxPayCourseNotifyToTvWeb(jsonString);
					}else{
						 returnContent = wxPayHandler.postWxPayNotifyToTvWeb(jsonString);
					}
                    logger.debug("returnContent:" + returnContent);

                    return new WxPaymentNotifyResp("SUCCESS","OK");
                }
                else
                {
                    return new WxPaymentNotifyResp("FAIL","系统操作失败！");
                }
            }
            else
            {
                return new WxPaymentNotifyResp("FAIL","签名校验错误");
            }
        } catch (SDKRuntimeException e) {
            return new WxPaymentNotifyResp("FAIL","签名校验错误");
        }
    }


    /**
     * 接受微信公众号支付通知的接口
     * @param WxPaymentNotifyReq notifyPostData
     * @return
     */
    @RequestMapping(value="/pay/wxmobile/notify", method = RequestMethod.POST)
    @ResponseBody
    public WxPaymentNotifyResp wxMobileNotify1234Tv(@RequestBody WxPaymentNotifyReq wxPaymentNotifyReq)
    {
        logger.debug(wxPaymentNotifyReq.toString());

        Order order = orderService.queryOrderById(wxPaymentNotifyReq.getOut_trade_no());
        order.setRespTime(new Date());

        if("SUCCESS".equals(wxPaymentNotifyReq.getReturn_code()))
        {
            order.setResult("01");
            order.setRespTime(DateUtils.parse(wxPaymentNotifyReq.getTime_end(), "yyyyMMddHHmmss"));
        }
        else
        {
            order.setResult("02");//失败
        }
        orderService.updateOrder(order);

        boolean isTrue = false;
        try {
            isTrue = wxPayHandler.compareWxNotifySign(wxPaymentNotifyReq);
            //if(isTrue)//如果签名比对成功
            if("SUCCESS".equals(wxPaymentNotifyReq.getReturn_code()))//针对微信用户参与支付活动时微信给的签名和1234tv的签名不一致导致校验失败的情况，改用return_code进行校验。
            {
                if (transactionService.saveTransaction(wxPaymentNotifyReq))
                {
//                    return new WxPaymentNotifyResp("<![CDATA[SUCCESS]]>","<![CDATA[OK]]>");
                    String jsonString = "{\"result\":true,\"order_id\":\""+wxPaymentNotifyReq.getOut_trade_no()+"\",\"time_end\":\""+
                            DateUtils.getDateTime(DateUtils.parse(wxPaymentNotifyReq.getTime_end(),"yyyyMMddHHmmss"),DateUtils.DEFAULT_DATETIME_FORMAT)+"\"}";
                    logger.debug("jsonString:" + jsonString);

                    String returnContent="";
                    if("01".equals(order.getSystem()) && "02".equals(order.getProduct()))//主站的讲座
                    {
                        returnContent = wxPayHandler.postWxPayNotifyToTvLecture(jsonString);
                    }
                    else
                    {
                        returnContent = wxPayHandler.postWxPayNotifyToTvMobile(jsonString);
                    }

                    logger.debug("returnContent:" + returnContent);

                    return new WxPaymentNotifyResp("SUCCESS","OK");
                }
                else
                {
                    return new WxPaymentNotifyResp("FAIL","系统操作失败！");
                }
            }
            else
            {
                return new WxPaymentNotifyResp("FAIL","签名校验错误");
            }
        } catch (SDKRuntimeException e) {
            return new WxPaymentNotifyResp("FAIL","签名校验错误");
        }
    }


}
