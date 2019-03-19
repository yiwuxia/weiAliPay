package com.tv.payment.dao;

import com.tv.payment.entity.Transaction;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Fujun on 2016/12/7.
 */
public interface TransactionDao {

    int insertTransaction(Transaction transaction);


    Transaction queryByOrderId(@Param("orderId") String orderId);
}
