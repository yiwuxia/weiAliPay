package com.tv.payment.service;

import com.tv.payment.dto.PaymentReq;
import com.tv.payment.dto.WxPackageRequest;
import com.tv.payment.entity.Order;

import java.util.Date;

/**
 * Created by Fujun on 2016/12/5.
 */

public interface OrderService {


    boolean saveOrder(Order order);

    boolean saveWxOrder(String orderId, PaymentReq wxOrder, Date ordertime);

    Order queryOrderById(String orderId);

    boolean checkWxOrder(PaymentReq wxOrder);

    boolean checkWxPackageRequest(WxPackageRequest wxPackageRequest);

    boolean updateOrder(Order order);
}
