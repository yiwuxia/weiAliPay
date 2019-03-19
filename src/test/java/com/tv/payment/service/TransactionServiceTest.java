package com.tv.payment.service;

import com.tv.payment.common.wxpay.WxPayHandler;
import com.tv.payment.dto.WxPaymentNotifyReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2017/1/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml","classpath:spring/spring-web.xml"})
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private WxPayHandler wxPayHandler;

    @Test
    public void testSaveTransaction() throws Exception
    {
        WxPaymentNotifyReq wxPaymentNotifyReq = new WxPaymentNotifyReq();
        wxPaymentNotifyReq.setReturn_code("SUCCESS");
        wxPaymentNotifyReq.setReturn_msg("null");
        wxPaymentNotifyReq.setAppid("wx90f65cb2256662ea");
        wxPaymentNotifyReq.setMch_id("1415350702");
        wxPaymentNotifyReq.setDevice_info("WEB");
        wxPaymentNotifyReq.setNonce_str("coa8dYo1N2P5OQrm");
        wxPaymentNotifyReq.setSign("1BFED8AD8C92A3766820D0DCB7EFB1B0");
        wxPaymentNotifyReq.setSign_type("null");
        wxPaymentNotifyReq.setResult_code("SUCCESS");
        wxPaymentNotifyReq.setErr_code("null");
        wxPaymentNotifyReq.setErr_code_des("null");
        wxPaymentNotifyReq.setOpenid("o-KCFwp6NnkgX8e9Xnmh9-7XKFms");
        wxPaymentNotifyReq.setIs_subscribe("Y");
        wxPaymentNotifyReq.setTrade_type("NATIVE");
        wxPaymentNotifyReq.setBank_type("CFT");
        wxPaymentNotifyReq.setTotal_fee(1);
        wxPaymentNotifyReq.setSettlement_total_fee(null);
        wxPaymentNotifyReq.setFee_type("CNY");
        wxPaymentNotifyReq.setCash_fee(1);
        wxPaymentNotifyReq.setCash_fee_type("null");
        wxPaymentNotifyReq.setCoupon_fee(null);
        wxPaymentNotifyReq.setCoupon_count(null);
        wxPaymentNotifyReq.setCoupon_type_$n(null);
        wxPaymentNotifyReq.setCoupon_id_$n("null");
        wxPaymentNotifyReq.setCoupon_fee_$n(null);
        wxPaymentNotifyReq.setTransaction_id("4002522001201701126039293304");
        wxPaymentNotifyReq.setOut_trade_no("1701121109000013");
        wxPaymentNotifyReq.setAttach("17011280107851--32068813充值1元");
        wxPaymentNotifyReq.setTime_end("20170112111316");

        transactionService.saveTransaction(wxPaymentNotifyReq);
    }

    @Test
    public void testCompareWxNotifySign() throws Exception
    {
        WxPaymentNotifyReq wxPaymentNotifyReq = new WxPaymentNotifyReq();
        wxPaymentNotifyReq.setReturn_code("SUCCESS");
        wxPaymentNotifyReq.setReturn_msg("null");
        wxPaymentNotifyReq.setAppid("wx90f65cb2256662ea");
        wxPaymentNotifyReq.setMch_id("1415350702");
        wxPaymentNotifyReq.setDevice_info("WEB");
        wxPaymentNotifyReq.setNonce_str("4OYRhzyePVwJYlBA");
        wxPaymentNotifyReq.setSign("233803088AE611DE7D8F099277E34D2C");
        wxPaymentNotifyReq.setSign_type("null");
        wxPaymentNotifyReq.setResult_code("SUCCESS");
        wxPaymentNotifyReq.setErr_code("null");
        wxPaymentNotifyReq.setErr_code_des("null");
        wxPaymentNotifyReq.setOpenid("o-KCFwp6NnkgX8e9Xnmh9-7XKFms");
        wxPaymentNotifyReq.setIs_subscribe("Y");
        wxPaymentNotifyReq.setTrade_type("NATIVE");
        wxPaymentNotifyReq.setBank_type("CFT");
        wxPaymentNotifyReq.setTotal_fee(1000);
        wxPaymentNotifyReq.setSettlement_total_fee(null);
        wxPaymentNotifyReq.setFee_type("CNY");
        wxPaymentNotifyReq.setCash_fee(1);
        wxPaymentNotifyReq.setCash_fee_type("null");
        wxPaymentNotifyReq.setCoupon_fee(null);
        wxPaymentNotifyReq.setCoupon_count(null);
        wxPaymentNotifyReq.setCoupon_type_$n(null);
        wxPaymentNotifyReq.setCoupon_id_$n("null");
        wxPaymentNotifyReq.setCoupon_fee_$n(null);
        wxPaymentNotifyReq.setTransaction_id("4002522001201701126039293304");
        wxPaymentNotifyReq.setOut_trade_no("1701121109000013");
        wxPaymentNotifyReq.setAttach("17011280107851--32068813充值1元");
        wxPaymentNotifyReq.setTime_end("20170112111316");


        boolean result = wxPayHandler.compareWxNotifySign(wxPaymentNotifyReq);
        System.out.println(result);
    }

}
