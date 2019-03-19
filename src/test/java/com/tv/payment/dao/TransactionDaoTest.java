package com.tv.payment.dao;

import com.tv.payment.entity.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Fujun on 2016/12/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class TransactionDaoTest {

    @Resource
    TransactionDao transactionDao;

    @Test
    public void testInsertTransaction() throws Exception {

     Date now = new Date();
    Transaction transaction = new Transaction();
        transaction.setOutTradeNo("1612091600000016");
        transaction.setReturnCode("11111");
        transaction.setReturnMsg("11111");
        transaction.setAppid("11111");
        transaction.setMchId("11111");
        transaction.setDeviceInfo("11111");
        transaction.setNonceStr("11111");
        transaction.setSign("11111");
        transaction.setSignType("11111");
        transaction.setResultCode("11111");
        transaction.setErrCode("11111");
        transaction.setErrCodeDes("11111");
        transaction.setOpenid("11111");
        transaction.setIsSubscribe("Y");
        transaction.setTradeType("11111");
        transaction.setBankType("11111");
        transaction.setTotalFee(1111L);
        transaction.setSettlementTotalFee(2222L);
        transaction.setFeeType("11111");
        transaction.setTransactionId("11111");
        transaction.setAttach("11111");
        transaction.setTimeEnd("11111");
        transaction.setTradeState("11111");
    int insertCount =  transactionDao.insertTransaction(transaction);

        System.out.println("insertCount:"+insertCount);
    }



}
