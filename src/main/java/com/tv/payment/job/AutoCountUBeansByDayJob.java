package com.tv.payment.job;

import com.tv.payment.proc.CountUBeansByDayProc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/16.
 */
@Component("autoCountUBeansByDayJob")
public class AutoCountUBeansByDayJob extends Job {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result run() {
        logger.info("Task AutoCountUBeansByDayJob is beginning");
        CountUBeansByDayProc countUBeansByDayProc = new CountUBeansByDayProc(jdbcTemplate);

        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        int year = yesterday.get(Calendar.YEAR);
        int month = yesterday.get(Calendar.MONTH) + 1;
        int day = yesterday.get(Calendar.DAY_OF_MONTH);
        logger.info("今天是年: "+year+",月: "+month+",日: "+day);
        countUBeansByDayProc.executeProc(year,month,day);

        String remarks = String.format("%s,执行完毕。",new Date());
        return new Job.Result(true, true, remarks);
    }
}
