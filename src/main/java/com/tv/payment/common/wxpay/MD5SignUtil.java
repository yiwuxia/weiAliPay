package com.tv.payment.common.wxpay;

public class MD5SignUtil {
	public static String Sign(String content, String key)
			throws SDKRuntimeException {
		String signStr = "";

		if ("" == key) {
			throw new SDKRuntimeException("key不能为空！");
		}
		if ("" == content) {
			throw new SDKRuntimeException("内容不能为空！");
		}
		signStr = content + "&key=" + key;

		String result=MD5Util.MD5(signStr);

/*		try {
			//result= MD5Util.md5_2(new String(signStr.getBytes("GB2312"), "UTF-8")).toUpperCase();
            result = MD5Util.MD5(new String(signStr.getBytes("ISO-8859-1"))).toUpperCase();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/

		return result;
        //result = new String((result).getBytes("ISO-8859-1"),"UTF-8");
		//new String(str.toString().getBytes(), "ISO8859-1")
	}
	public static boolean VerifySignature(String content, String sign, String md5Key) {
		String signStr = content + "&key=" + md5Key;
		String calculateSign = MD5Util.MD5(signStr).toUpperCase();
		String tenpaySign = sign.toUpperCase();
		return (calculateSign == tenpaySign);
	}

	/*public static void main(String[] args)
	{
		try {
			System.out.println(Sign("appid=wx3afa461aa7d037c1&attach=17081653856856--32035461--支付课程--支付10000元&body=1234tv财经直播支付-课程-K线组合&device_info=123&mch_id=1455793402&nonce_str=0YOXINqZifaV3BLo&notify_url=http://testlogin.1234tv.com/payment/pay/wx/notify&out_trade_no=1708161603000023&spbill_create_ip=192.168.2.28&total_fee=10000&trade_type=APP","214d99f57c33d4d5276809c6cc1c39e2"));
		} catch (SDKRuntimeException e) {
			e.printStackTrace();
		}
	}*/
}
