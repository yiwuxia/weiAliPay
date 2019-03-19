package com.tv.payment.dao;

import com.tv.payment.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * Created by Fujun on 2016/12/5.
 */
public interface OrderDao {

    /**
     *
     */
    int insertOrder(@Param("id") String id,@Param("tId") String tId, @Param("system") String system, @Param("channel") String channel, @Param("product") String product, @Param("productDesc") String productDesc,
                          @Param("productFee") String productFee, @Param("billCreateIp") String billCreateIp,@Param("noncestr") String noncestr,@Param("openId") String openId, @Param("issubscribe") String issubscribe, @Param("reqTime") Date reqTime);

    /**
     *
     * @param id
     * @return
     */
    Order queryById(String id);

    int updateOrderById(Order order);

}
