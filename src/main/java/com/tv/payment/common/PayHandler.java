package com.tv.payment.common;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Administrator on 2017/8/17.
 */
@Component
public class PayHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("#{configProperties['tvWebNotifyResultUrl']}")
    private String tvWebNotifyResultUrl;

    @Value("#{configProperties['tvMobileNotifyResultUrl']}")
    private String tvMobileNotifyResultUrl;

    @Value("#{configProperties['mobile_notify_url']}")
    private String mobile_notify_url;

    @Value("#{configProperties['tvLectureNotifyResultUrl']}")
    private String tvLectureNotifyResultUrl;
    
    @Value("#{configProperties['tvCourseNotifyResultUrl']}")
    private String tvCourseNotifyResultUrl;
    

    /**
     *
     * @param jsonString
     * @return
     */
    public String postWxPayNotifyToTvWeb(String jsonString)
    {
        logger.debug("tvWebNotifyResultUrl:" + this.tvWebNotifyResultUrl);

        return this.postJson(this.tvWebNotifyResultUrl, jsonString);
    }

    /**
    *
    * @param jsonString
    * @return
    */
   public String postWxPayCourseNotifyToTvWeb(String jsonString)
   {
       logger.debug("tvWebCourseNotifyResultUrl:" + this.tvCourseNotifyResultUrl);

       return this.postJson(this.tvCourseNotifyResultUrl, jsonString);
   }
    
    /**
     *
     * @param jsonString
     * @return
     */
    public String postWxPayNotifyToTvMobile(String jsonString)
    {
        logger.debug("tvMobileNotifyResultUrl:" + this.tvMobileNotifyResultUrl);

        return this.postJson(this.tvMobileNotifyResultUrl, jsonString);
    }

    /**
     * @param jsonString
     * @return
     */
    public String postWxPayNotifyToTvLecture(String jsonString)
    {
        logger.debug("tvLectureNotifyResultUrl:" + this.tvLectureNotifyResultUrl);

        return this.postJson(this.tvLectureNotifyResultUrl, jsonString);
    }

    private String postJson(String url, String jsonString) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse rawResponse=null;
        StringEntity requestEntity = new StringEntity(
                jsonString,
                ContentType.APPLICATION_JSON);

        HttpPost postMethod = new HttpPost(url);
        postMethod.setEntity(requestEntity);

        try {
            rawResponse = httpclient.execute(postMethod);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String rtnString = null;
        try {
            rtnString = EntityUtils.toString(rawResponse.getEntity());
        } catch (IOException e) {
            logger.debug(rtnString);
        }
        return rtnString;
    }
}
