package com.tv.payment.service.impl;

import com.tv.payment.common.wxpay.CommonUtil;
import com.tv.payment.dao.OrderDao;
import com.tv.payment.dto.PaymentReq;
import com.tv.payment.dto.WxPackageRequest;
import com.tv.payment.entity.Order;
import com.tv.payment.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Fujun on 2016/12/5.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private OrderDao orderDao;

    @Override
    public boolean saveOrder(Order order) {
        return orderDao.insertOrder(order.getId(),order.gettId(),
                order.getSystem(),order.getChannel(),order.getProduct(),order.getProductDesc(),
                order.getProductFee(), order.getBillCreateIp(),order.getNoncestr(),order.getOpenId(),order.getIsSubscriber(), new Date()) >0;
    }

    @Override
    public boolean saveWxOrder(String orderId, PaymentReq wxOrder, Date orderTime) {
        String tId = UUID.randomUUID().toString();

        Order order = new Order(orderId,wxOrder.getBody(),
                wxOrder.getSystemId(), wxOrder.getChannelId(), wxOrder.getProductId(),
                wxOrder.getProductDesc(), wxOrder.getProductFee(), wxOrder.getProductFee(),
                wxOrder.getSpbillCreateIp(), CommonUtil.CreateNoncestr(),wxOrder.getOpenId(), wxOrder.getIssubscribe(), orderTime);

        return saveOrder(order);
    }

    @Override
    public Order queryOrderById(String orderId) {
        return orderDao.queryById(orderId);
    }

    @Override
    public boolean checkWxOrder(PaymentReq wxOrder) {
        if (wxOrder.getSystemId().equals("") || wxOrder.getChannelId().equals("")
                || wxOrder.getProductId().equals("")
                || wxOrder.getProductDesc().equals("")
                || wxOrder.getProductFee().equals("")
                || wxOrder.getSpbillCreateIp().equals("")) {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkWxPackageRequest(WxPackageRequest wxPackageRequest)
    {
        if (wxPackageRequest.getOpenId().equals("") || wxPackageRequest.getAppId().equals("")
                || wxPackageRequest.getIsSubscribe().equals("")
                || wxPackageRequest.getProductId().equals("")
                || wxPackageRequest.getTimeStamp().equals("")
                || wxPackageRequest.getNonceStr().equals("")
                || wxPackageRequest.getAppSignature().equals("")
                || wxPackageRequest.getSignMethod().equals("")) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateOrder(Order order) {
        return (orderDao.updateOrderById(order) >0);
    }
}
