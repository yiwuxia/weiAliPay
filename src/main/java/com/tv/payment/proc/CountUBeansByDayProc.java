package com.tv.payment.proc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/16.
 */
public class CountUBeansByDayProc extends StoredProcedure {


    public CountUBeansByDayProc(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, "a0001_chat.count_u_beans_by_day");

        declareParameter(new SqlParameter("yearIn", Types.INTEGER));
        declareParameter(new SqlParameter("monthIn", Types.INTEGER));
        declareParameter(new SqlParameter("dayIn", Types.INTEGER));

        compile();
    }

    public Map<String, Object> executeProc(int year, int month, int day)
    {
        Map<String, Integer> paramMap = new HashMap<String, Integer>();
        paramMap.put("yearIn", year);
        paramMap.put("monthIn", month);
        paramMap.put("dayIn", day);

        return execute(paramMap);
    }
}
