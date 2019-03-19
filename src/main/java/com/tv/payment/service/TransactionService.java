package com.tv.payment.service;

import com.tv.payment.dto.WxPaymentNotifyReq;
import com.tv.payment.entity.Transaction;

/**
 * Created by Fujun on 2016/12/7.
 */
public interface TransactionService {

    boolean saveTransaction(WxPaymentNotifyReq wxPaymentNotifyReq);

    Transaction queryTransactionByOrderId(String orderId);
}
