package com.tv.payment.common.wxpay;

import java.util.*;
import java.net.URLEncoder;

import com.tv.payment.dto.*;
import com.tv.payment.entity.Order;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WxPayHelper {
	private HashMap<String, String> parameters = new HashMap<String, String>();

	@Value("#{configProperties['appId']}")
	private String AppId = "";

	@Value("#{configProperties['appKey']}")
	private String AppKey = "";

	@Value("#{configProperties['signType']}")
	private String SignType = "";

    @Value("#{configProperties['partnerKey']}")
	private String PartnerKey = "";

/*	public void SetAppId(String str) {
		AppId = str;
	}

	public void SetAppKey(String str) {
		AppKey = str;
	}

	public void SetSignType(String str) {
		SignType = str;
	}

	public void SetPartnerKey(String str) {
		PartnerKey = str;
	}*/

	public void SetParameter(String key, String value) {
		parameters.put(key, value);
	}

	public String GetParameter(String key) {
		return parameters.get(key);
	}

	private Boolean CheckCftParameters() {
		if (parameters.get("bank_type") == "" || parameters.get("body") == ""
				|| parameters.get("partner") == ""
				|| parameters.get("out_trade_no") == ""
				|| parameters.get("total_fee") == ""
				|| parameters.get("fee_type") == ""
				|| parameters.get("notify_url") == null
				|| parameters.get("spbill_create_ip") == ""
				|| parameters.get("input_charset") == "") {
			return false;
		}
		return true;
	}

	public String GetCftPackage() throws SDKRuntimeException {
		if ("" == PartnerKey) {
			throw new SDKRuntimeException("��Կ����Ϊ�գ�");
		}
		String unSignParaString = CommonUtil.FormatBizQueryParaMap(parameters,
				false);
		String paraString = CommonUtil.FormatBizQueryParaMap(parameters, true);
		return paraString + "&sign="
				+ MD5SignUtil.Sign(unSignParaString, PartnerKey);

	}

	public String GetBizSign(HashMap<String, String> bizObj)
			throws SDKRuntimeException {
		HashMap<String, String> bizParameters = new HashMap<String, String>();

		List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
				bizObj.entrySet());

		Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1,
					Map.Entry<String, String> o2) {
				return (o1.getKey()).toString().compareTo(o2.getKey());
			}
		});

		for (int i = 0; i < infoIds.size(); i++) {
			Map.Entry<String, String> item = infoIds.get(i);
			if (item.getKey() != "") {
				bizParameters.put(item.getKey().toLowerCase(), item.getValue());
			}
		}

		if (AppKey == "") {
			throw new SDKRuntimeException("APPKEY不能为空");
		}
		bizParameters.put("appkey", AppKey);
		String bizString = CommonUtil.FormatBizQueryParaMap(bizParameters,
				false);
		//System.out.println(bizString);

		return SHA1Util.Sha1(bizString);

	}

	/*
	 * { "appid":"wwwwb4f85f3a797777", "traceid":"crestxu",
	 * "noncestr":"111112222233333", "package":
	 * "bank_type=WX&body=XXX&fee_type=1&input_charset=GBK&notify_url=http%3a%2f%2f
	 * www
	 * .qq.com&out_trade_no=16642817866003386000&partner=1900000109&spbill_create_ip
	 * =127.0.0.1&total_fee=1&sign=BEEF37AD19575D92E191C1E4B1474CA9",
	 * "timestamp":1381405298,
	 * "app_signature":"53cca9d47b883bd4a5c85a9300df3da0cb48565c",
	 * "sign_method":"sha1" }
	 */
	public String CreateAppPackage(String traceid) throws SDKRuntimeException {
		HashMap<String, String> nativeObj = new HashMap<String, String>();
		if (CheckCftParameters() == false) {
			throw new SDKRuntimeException("����package����ȱʧ��");
		}
		nativeObj.put("appid", this.AppId);
		nativeObj.put("package", GetCftPackage());
		nativeObj.put("timestamp", Long.toString(new Date().getTime()/1000));
		nativeObj.put("traceid", traceid);
		nativeObj.put("noncestr", CommonUtil.CreateNoncestr());
		nativeObj.put("app_signature", GetBizSign(nativeObj));
		nativeObj.put("sign_method", this.SignType);
		return JSONObject.fromObject(nativeObj).toString();
	}

	/*
	 * "appId" : "wxf8b4f85f3a794e77", //���ں����ƣ����̻����� "timeStamp" : "189026618",
	 * //ʱ�����������ʹ����һ��ֵ "nonceStr" : "adssdasssd13d", //����� "package" :
	 * "bank_type=WX&body=XXX&fee_type=1&input_charset=GBK&notify_url=http%3a%2f
	 * %2fwww.qq.com&out_trade_no=16642817866003386000&partner=1900000109&
	 * spbill_create_i
	 * p=127.0.0.1&total_fee=1&sign=BEEF37AD19575D92E191C1E4B1474CA9",
	 * //��չ�ֶΣ����̻����� "signType" : "SHA1", //΢��ǩ����ʽ:sha1 "paySign" :
	 * "7717231c335a05165b1874658306fa431fe9a0de" //΢��ǩ��
	 */
	public String CreateBizPackage() throws SDKRuntimeException {
		HashMap<String, String> nativeObj = new HashMap<String, String>();
		if (CheckCftParameters() == false) {
			throw new SDKRuntimeException("����package����ȱʧ��");
		}
		nativeObj.put("appId", this.AppId);
		nativeObj.put("package", GetCftPackage());
		nativeObj.put("timestamp", Long.toString(new Date().getTime()/1000));
		nativeObj.put("noncestr", CommonUtil.CreateNoncestr());
		nativeObj.put("paySign", GetBizSign(nativeObj));
		nativeObj.put("signType", this.SignType);
		return JSONObject.fromObject(nativeObj).toString();

	}

	/*
	 * weixin://wxpay/bizpayurl?sign=XXXXX&appid=XXXXXX&productid=XXXXXX&timestamp
	 * =XXXXXX&noncestr=XXXXXX
	 */
	public String CreateNativeUrl(String productid) throws SDKRuntimeException {
		String bizString = "";
		try {
			HashMap<String, String> nativeObj = new HashMap<String, String>();
			nativeObj.put("appid", this.AppId);
			nativeObj.put("noncestr", CommonUtil.CreateNoncestr());
			nativeObj.put("productid", URLEncoder.encode(productid, "utf-8"));
			nativeObj.put("timestamp", Long.toString(new Date().getTime() / 1000));
			nativeObj.put("sign", GetBizSign(nativeObj));
			bizString = CommonUtil.FormatBizQueryParaMap(nativeObj, false);
			
		} catch (Exception e) {
			throw new SDKRuntimeException(e.getMessage());

		}
		return "weixin://wxpay/bizpayurl?" + bizString;
	}

	public JsapiResp CreateSignature4JsApi(JsapiReq jsapiReq) throws SDKRuntimeException{
		String signature ="";
		JsapiResp jsapiResp = new JsapiResp();

		jsapiResp.setTimestamp(Long.toString(new Date().getTime() / 1000));
		jsapiResp.setNonceStr(CommonUtil.CreateNoncestr());
		jsapiResp.setAppId(jsapiReq.getAppId());
		try {
			HashMap<String, String> nativeObj = new HashMap<String, String>();
			//nativeObj.put("appid", jsapiReq.getAppId());
			nativeObj.put("jsapi_ticket", jsapiReq.getJsapi_ticket());
			nativeObj.put("noncestr", jsapiResp.getNonceStr());
			nativeObj.put("timestamp", jsapiResp.getTimestamp());
			nativeObj.put("url",jsapiReq.getUrl());
			signature = GetBizSign(nativeObj);
			jsapiResp.setSignature(signature);

		} catch (Exception e) {
			throw new SDKRuntimeException(e.getMessage());
		}
		return jsapiResp;
	}

	// ����ԭ��֧������xml
	/*
	 * <xml> <AppId><![CDATA[wwwwb4f85f3a797777]]></AppId>
	 * <Package><![CDATA[a=1&url=http%3A%2F%2Fwww.qq.com]]></Package>
	 * <TimeStamp> 1369745073</TimeStamp>
	 * <NonceStr><![CDATA[iuytxA0cH6PyTAVISB28]]></NonceStr>
	 * <RetCode>0</RetCode> <RetErrMsg><![CDATA[ok]]></ RetErrMsg>
	 * <AppSignature><![CDATA[53cca9d47b883bd4a5c85a9300df3da0cb48565c]]>
	 * </AppSignature> <SignMethod><![CDATA[sha1]]></ SignMethod > </xml>
	 */
	public String CreateNativePackage(String retcode, String reterrmsg) throws SDKRuntimeException {
		HashMap<String, String> nativeObj = new HashMap<String, String>();
		if (CheckCftParameters() == false && retcode == "0") {
			throw new SDKRuntimeException("����package����ȱʧ��");
		}
		nativeObj.put("AppId", this.AppId);
		nativeObj.put("Package", GetCftPackage());
		nativeObj.put("TimeStamp", Long.toString(new Date().getTime()/1000));
		nativeObj.put("RetCode", retcode);
		nativeObj.put("RetErrMsg", reterrmsg);
		nativeObj.put("NonceStr", CommonUtil.CreateNoncestr());
		nativeObj.put("AppSignature", GetBizSign(nativeObj));
		nativeObj.put("SignMethod", this.SignType);
		return CommonUtil.ArrayToXml(nativeObj);

	}

	public String CreateNativeUrl4Payment(Order order) throws SDKRuntimeException {
		//先设置基本信息
		//this.SetAppId("wx90f65cb2256662ea");//公众号APPID
		//this.SetAppKey("xyjlds!@#$tv");//公众号密码
		//this.SetPartnerKey("655836");//微信支付商户号对应的密码
		//this.SetSignType("sha1");
		//设置请求package信息
		this.SetParameter("bank_type", "WX");
		this.SetParameter("body", order.getProductDesc());
		this.SetParameter("partner", "1415350702");//微信支付商户号
		this.SetParameter("out_trade_no", order.getId());//payment系统内部订单号
		this.SetParameter("total_fee", order.getProductFee());//目前是一单一品，没有物流
		this.SetParameter("fee_type", "1");
		this.SetParameter("notify_url", "http://pay.1234tv.com/payment/get/wx/notify");
		this.SetParameter("spbill_create_ip", order.getBillCreateIp());
		this.SetParameter("input_charset", "GBK");

	    System.out.println("生成原生支付url:");
	String url = "";
	try {
		url = this.CreateNativeUrl(order.getId());
		System.out.println(url);
	} catch (SDKRuntimeException e) {
		e.printStackTrace();
	    }

		return url;
	}

	/**
	 * 比对package时候的微信和本地的签名
	 * @param wxPackageRequest
	 * @param order
	 * @return
	 * @throws SDKRuntimeException
	 */
	public boolean compareSignBetwWxAndLocal(WxPackageRequest wxPackageRequest, Order order) throws SDKRuntimeException
	{
		HashMap<String, String> nativeObj = new HashMap<String, String>();

		nativeObj.put("AppId", this.AppId);
		nativeObj.put("AppKey", this.AppKey);
		nativeObj.put("Openid", wxPackageRequest.getOpenId());
		nativeObj.put("ProductId", order.getId());
		nativeObj.put("TimeStamp", Long.toString(new Date().getTime() / 1000));//Long.toString(new Date().getTime()/1000)
		nativeObj.put("Noncestr", CommonUtil.CreateNoncestr());
		nativeObj.put("issubscribe", wxPackageRequest.getIsSubscribe());

		String appSignature = "";
		try {
			appSignature= GetBizSign(nativeObj);
		} catch (SDKRuntimeException e) {
			e.printStackTrace();
		}

		return appSignature.equalsIgnoreCase(wxPackageRequest.getAppSignature());
	}

	/**
	 * 比对notify时候的微信和本地的签名
	 * @param WxNotifyUrlObject
	 * @param NotifyPostData
	 * @return
	 * @throws SDKRuntimeException
	 */
	public boolean compareNotifySignBetwWxAndLocal(WxPaymentNotifyReq wxNotifyUrlObject, NotifyPostData notifyPostData) throws SDKRuntimeException
	{
		HashMap<String, String> nativeObj = new HashMap<String, String>();

		nativeObj.put("AppId", this.AppId);
		nativeObj.put("AppKey", this.AppKey);
		nativeObj.put("Openid", notifyPostData.getOpenId());
//		nativeObj.put("ProductId", wxNotifyUrlObject.getOut_trade_no());
		nativeObj.put("TimeStamp", Long.toString(new Date().getTime() / 1000));//Long.toString(new Date().getTime()/1000)
		nativeObj.put("Noncestr", CommonUtil.CreateNoncestr());
		nativeObj.put("issubscribe", notifyPostData.getIsSubscribe());

		String appSignature = "";
		try {
			appSignature= GetBizSign(nativeObj);
		} catch (SDKRuntimeException e) {
			e.printStackTrace();
		}

		return appSignature.equalsIgnoreCase(notifyPostData.getAppSignature());
	}


	/**
	 *根据订单创建WxPackageResponse返回给微信公众平台
	 * @param order
	 * @Param retcode 0-表示正确
	 * @Param reterrmsg 错误信息
	 * @return
	 */
	public WxPackageResponse createWxPackageResponse(Order order, String retcode, String reterrmsg) throws SDKRuntimeException
	{
		//先设置基本信息
		//this.SetAppId("wx90f65cb2256662ea");//公众号APPID
		//this.SetAppKey("xyjlds!@#$tv");//公众号密码
		//this.SetPartnerKey("655836");//微信支付商户号对应的密码
		//this.SetSignType("sha1");
		//设置请求package信息
		this.SetParameter("bank_type", "WX");
		this.SetParameter("body", order.getProductDesc());
		this.SetParameter("partner", "1415350702");//微信支付商户号
		this.SetParameter("out_trade_no", order.gettId());//payment系统内部订单号
		this.SetParameter("total_fee", order.getProductFee());//目前是一单一品，没有物流
		this.SetParameter("fee_type", "1");
		this.SetParameter("notify_url", "htttp://www.baidu.com");
		this.SetParameter("spbill_create_ip", order.getBillCreateIp());
		this.SetParameter("input_charset", "GBK");

		HashMap<String, String> nativeObj = new HashMap<String, String>();

		nativeObj.put("AppId", this.AppId);
		nativeObj.put("AppKey", this.AppKey);
//		try {
			nativeObj.put("Package", GetCftPackage());
//		} catch (SDKRuntimeException e) {
//			e.printStackTrace();
//		}
		nativeObj.put("TimeStamp", Long.toString(new Date().getTime() / 1000));
		nativeObj.put("Noncestr", order.getNoncestr());
		nativeObj.put("Retcode", retcode);
		nativeObj.put("Reterrmsg", reterrmsg);
		nativeObj.put("AppSignature", GetBizSign(nativeObj));

		WxPackageResponse wxPackageResponse = new WxPackageResponse();

		wxPackageResponse.setAppId(nativeObj.get("AppId"));
		wxPackageResponse.setPackage(nativeObj.get("Package"));
		wxPackageResponse.setTimeStamp(nativeObj.get("TimeStamp"));
		wxPackageResponse.setNonceStr(nativeObj.get("Noncestr"));
		wxPackageResponse.setRetCode(retcode);
		wxPackageResponse.setRetErrMsg(reterrmsg);
		wxPackageResponse.setAppSignature(nativeObj.get("AppSignature"));

		return wxPackageResponse;
	}

}
