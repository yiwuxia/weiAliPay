package com.tv.payment.web;


import com.tv.payment.common.wxpay.SDKRuntimeException;
import com.tv.payment.common.wxpay.WxPayHandler;
import com.tv.payment.common.wxpay.WxPayHelper;
import com.tv.payment.dto.JsapiReq;
import com.tv.payment.dto.JsapiResp;
import com.tv.payment.dto.PaymentReq;
import com.tv.payment.dto.PaymentResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sign")
public class SignController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxPayHelper wxPayHelper;

    /**
     * 生成jsapi签名,最终给前端使用
     * @param JsapiReq jsapiReq
     * @return 提供jsapi签名响应给login_go_api系统使用，最终给信息流前端使用
     */
    @RequestMapping(value ="/create/jsapi/signature", method = RequestMethod.POST)
    @ResponseBody
    public JsapiResp createJsapiSign(@RequestBody JsapiReq jsapiReq){
        JsapiResp jsapiResp = new JsapiResp();
        try {
            logger.info("JsapiReq is "+ jsapiReq);
            jsapiResp = wxPayHelper.CreateSignature4JsApi(jsapiReq);

        } catch (SDKRuntimeException e) {
            e.printStackTrace();
            logger.error("Sign jsapiReq failed!");
        }

        return jsapiResp;
    }
}
