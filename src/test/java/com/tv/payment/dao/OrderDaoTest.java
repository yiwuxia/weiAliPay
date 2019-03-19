package com.tv.payment.dao;

import com.tv.payment.common.DateUtils;
import com.tv.payment.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Fujun on 2016/12/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class OrderDaoTest {

    @Resource
    private OrderDao orderDao;

    @Test
    public void insertOrderTest() throws Exception {
        Date now = new Date();
     int insertCount = orderDao.insertOrder(DateUtils.getDateTime(now, DateUtils.ORDER_DATETIME_FORMAT),"111111","1234456789", "1", "2","3","4","5","6","789789","Y", now);

     System.out.println("insertCount:"+insertCount);
    }

    @Test
    public void testQueryById() throws Exception {
        Order transaction = orderDao.queryById("1612061451000050");

        System.out.println(transaction);
    }

    @Test
    public void testUpdateOrderById() throws  Exception{
        Order order = new Order();
        order.setId("1701111622000055");
        order.setResult("00");

        System.out.println(orderDao.updateOrderById(order));;
    }
}
