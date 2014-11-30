package org.franken.fetchurl;

import org.franken.message.util.TravelPropertiesUtil;

public class GetStaticAccessToken {
	//应用的id（微信公众平台上获取）
	private static String appid = "wx15a17f9bd8702f70";
	//应用的密码（微信公众平台上获取）
	private static String appsecret = "5e3a32858229df7ebf5320421a690a20";
	// 再次获取应用程序的access_token的时间间隔
	private static final long SUB_TIME = (2 * 60 - 5) * 60 * 1000;
	/**
	 * 获取应用程序的access_token
	 * @param appid 应用的id（微信公众平台上获取）
	 * @param appsecret 应用的密码（微信公众平台上获取）
	 * @return 返回应用的access_token
	 */
	public static String getAccessToken(){
//		long sub = System.currentTimeMillis() - TravelPropertiesUtil.getInstance().getLastCachedAccessTokenTime(); 
//		if(sub > 0 && sub < SUB_TIME) {
//			String str = TravelPropertiesUtil.getInstance().getCachedAccessToken();
//			if(str != null) {
//				return str;
//			}
//		}
		String accesstokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+
				appid+"&secret="+appsecret;
		FetchUrl fetchUrl = new FetchUrl();
		String accesstokenjsonData = fetchUrl.getDataFromUrl(accesstokenUrl);
		String access_token = fetchUrl.parseAccessToken(accesstokenjsonData);
//		TravelPropertiesUtil.getInstance().setCacheAccessToken(access_token);
//		TravelPropertiesUtil.getInstance().setLastCacheAccessTokenTime(System.currentTimeMillis());
		return access_token;
	}
}
