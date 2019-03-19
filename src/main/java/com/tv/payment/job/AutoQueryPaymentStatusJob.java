package com.tv.payment.job;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by PC on 2016/9/23.
 */
@Component("autoQueryPaymentStatusJob")
public class AutoQueryPaymentStatusJob extends  Job{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Result run() {
/*        String url = "http://www.saskatchewan.ca/residents/moving-to-saskatchewan/immigrating-to-saskatchewan/saskatchewan-immigrant-nominee-program/maximum-number-of-sinp-applications";

        CssSelectorNodeFilter divFilter = new CssSelectorNodeFilter("table >> td");//非学历课程，a作为ul的一个子节点。

        try
        {
            Parser parser = new Parser(url);

            NodeList list = parser.extractAllNodesThatMatch(divFilter);

            List<String> nodePlainTextList = new ArrayList<String>();

            for (int i = 0; i < list.size(); i++)
            {
                TagNode n1 = (TagNode) list.elementAt(i);
                //System.out.println(i+":"+n1.toHtml());
//                System.out.println(i + ":" + n1.toPlainTextString().trim());
                nodePlainTextList.add(n1.toPlainTextString().trim());
            }

            if(!nodePlainTextList.get(13).toString().equals("0"))//Express Entry Quota
            {
                logger.info("13::"+nodePlainTextList.get(11).toString()+":"+nodePlainTextList.get(12).toString()+":"+nodePlainTextList.get(13).toString());

                MessageSender.sendMessage("13813972167", "@1@=SK的EE放名额了,"+nodePlainTextList.get(13).toString()+"个名额!", "JSM40243-0002");
            }

            if(!nodePlainTextList.get(16).toString().equals("0"))//Occupations In-Demand Quota
            {
                MessageSender.sendMessage("13813972167", "@1@=SK的Occupations In-Demand放名额了,"+nodePlainTextList.get(16).toString()+"个名额!", "JSM40243-0002");
                logger.info("16::"+nodePlainTextList.get(14).toString()+":"+nodePlainTextList.get(15).toString()+":"+nodePlainTextList.get(16).toString());
            }
        }
        catch (ParserException e)
        {
            e.printStackTrace();
        }*/
        String remarks = String.format("%s,执行完毕。",new Date());
        return new Job.Result(true, true, remarks);
    }

}
