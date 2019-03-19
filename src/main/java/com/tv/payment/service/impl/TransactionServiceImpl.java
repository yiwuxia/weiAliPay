package com.tv.payment.service.impl;

import com.tv.payment.dao.TransactionDao;
import com.tv.payment.dto.WxPaymentNotifyReq;
import com.tv.payment.entity.Transaction;
import com.tv.payment.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Fujun on 2016/12/7.
 */
@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionDao transactionDao;

    @Override
    public boolean saveTransaction(WxPaymentNotifyReq wxPaymentNotifyReq) {

        Transaction transaction = new Transaction();

        transaction.setOutTradeNo(wxPaymentNotifyReq.getOut_trade_no());
        transaction.setReturnCode(wxPaymentNotifyReq.getReturn_code());
        transaction.setReturnMsg(wxPaymentNotifyReq.getReturn_msg());
        transaction.setAppid(wxPaymentNotifyReq.getAppid());
        transaction.setMchId(wxPaymentNotifyReq.getMch_id());
        transaction.setDeviceInfo(wxPaymentNotifyReq.getDevice_info());
        transaction.setNonceStr(wxPaymentNotifyReq.getNonce_str());
        transaction.setSign(wxPaymentNotifyReq.getSign());
        transaction.setSignType(wxPaymentNotifyReq.getSign_type());
        transaction.setResultCode(wxPaymentNotifyReq.getResult_code());
        transaction.setErrCode(wxPaymentNotifyReq.getErr_code());
        transaction.setErrCodeDes(wxPaymentNotifyReq.getErr_code_des());
        transaction.setOpenid(wxPaymentNotifyReq.getOpenid());
        transaction.setIsSubscribe(wxPaymentNotifyReq.getIs_subscribe());
        transaction.setTradeType(wxPaymentNotifyReq.getTrade_type());
        transaction.setBankType(wxPaymentNotifyReq.getBank_type());
        transaction.setTotalFee(wxPaymentNotifyReq.getTotal_fee().longValue());
        if(null != wxPaymentNotifyReq.getSettlement_total_fee())
        {
            transaction.setSettlementTotalFee(wxPaymentNotifyReq.getSettlement_total_fee().longValue());
        }
        transaction.setFeeType(wxPaymentNotifyReq.getFee_type());
        transaction.setTransactionId(wxPaymentNotifyReq.getTransaction_id());
        transaction.setAttach(wxPaymentNotifyReq.getAttach());
        transaction.setTimeEnd(wxPaymentNotifyReq.getTime_end());
        transaction.setTradeState(wxPaymentNotifyReq.getResult_code());
        transaction.setReqTime(new Date());

        return transactionDao.insertTransaction(transaction) >0;
    }

    @Override
    public Transaction queryTransactionByOrderId(String orderId) {
        return transactionDao.queryByOrderId(orderId);
    }
}
