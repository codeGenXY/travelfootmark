package org.franken.fetchurl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.franken.baidu.map.api.AddressComponent;
import org.franken.baidu.map.api.BaiduResult;
import org.franken.baidu.map.api.Result;
import org.franken.user.jsonutil.AccessToken;
import org.franken.user.jsonutil.User;
import org.franken.user.jsonutil.UserJsonUtil;

import com.google.gson.reflect.TypeToken;
import com.sina.sae.fetchurl.SaeFetchurl;

public class FetchUrl {
	
	private static String access_token = GetStaticAccessToken.getAccessToken();
	/**
	 * 调用SAE上的方法SaeFetchurl从指定的URL上抓取数据以及网页的响应
	 * @param url 给定的URL
	 * @return 返回URL上的数据（网页源码以及网页的响应，某项无则该项为空）
	 */
	public String getDataFromUrl(String url){
		return getDataFromUrl(url, null, null);
	}
	
	/**
	 * 调用SAE上的方法SaeFetchurl从指定的URL上抓取数据以及网页的响应
	 * @param url 给定的URL
	 * @param accessKey SAE对应用的唯一id
	 * @param secretKey SAE对应用的唯一密码
	 * @return 返回URL上的数据（网页源码以及网页的响应，某项无则该项为空）
	 */
	public String getDataFromUrl(String url, String accessKey, String secretKey){
		SaeFetchurl fetchurl;
		if(accessKey==null||secretKey==null){
			fetchurl = new SaeFetchurl();
		}else{
			fetchurl = new SaeFetchurl(accessKey,secretKey);
		}
		fetchurl.setMethod("GET");
		String data = fetchurl.fetch(url);
		return data;
		
	}
	
	/**
	 * 得到用户的用户名
	 * @param jsonData 从url上获取的用户的json数据
	 * @return 用户的name
	 */
	public User parseUser(String jsonData){
		User user = null;
		if(null!=jsonData){
			UserJsonUtil userJsonUtil=new UserJsonUtil();
			user=userJsonUtil.parseJsonFromJsonSinger(jsonData);
			try {
					//解决中文乱码问题
				user.setNickname(new String (user.getNickname().getBytes("ISO-8859-1"),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return user;
	}
	
	/**
	 * 解析得到的json数据，获取access_token
	 * @param jsonData 待解析的json数据
	 * @return
	 */
	public String parseAccessToken(String jsonData){
		String accesstoken=null;
		if(null!=jsonData){
			UserJsonUtil userJsonUtil=new UserJsonUtil();
			AccessToken token=userJsonUtil.parseAccessToken(jsonData);
			accesstoken=token.getAccess_token();
	}
		return accesstoken;
	}
	
	/**
	 * 封装成一步获取用户的姓名
	 * @param openid 用户对本应用的唯一凭证（用户让公众号发送信息时由微信推送）
	 * @return 返回用户的姓名
	 */
	public User getUser(String openid){
		String accessKey = "4nwm51wyyk";
		String secretKey = "2ii4hllxmi03yx3x3mijx3i5j5i1x3lmxm0wm00j";
		String userinfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+access_token+"&openid="+openid;
		String userinfojsonData = getDataFromUrl(userinfoUrl,accessKey,secretKey);
		if(userinfojsonData.contains("errcode")){
			access_token = GetStaticAccessToken.getAccessToken();
			userinfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+access_token+"&openid="+openid;
			userinfojsonData = getDataFromUrl(userinfoUrl);
		}
		User user = parseUser(userinfojsonData);
		return user;
	}
	
	/**
	 * 获取应用程序的access_token
	 * @param appid 应用的id（微信公众平台上获取）
	 * @param appsecret 应用的密码（微信公众平台上获取）
	 * @return 返回应用的access_token
	 */
	public static String getAccessToken(String appid, String appsecret){
		String accesstokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+
				appid+"&secret="+appsecret;
		FetchUrl fetchUrl = new FetchUrl();
		String accesstokenjsonData = fetchUrl.getDataFromUrl(accesstokenUrl);
		String access_token = fetchUrl.parseAccessToken(accesstokenjsonData);
		return access_token;
	}
	
	public static void main(String[] args) {
//		doParseGeographic();
	} 
	
	public static void doParseGeographic() {
		String projectPath=System.getProperty("user.dir");
		String propertiesPath=projectPath+File.separator+"files"+File.separator+"latandlgt.txt";
		File file = new File(propertiesPath);
		FileReader fReader = null;
		BufferedReader buf = null;
		Pattern pattern = Pattern.compile("[0-9]+.[0-9]+");
		Matcher matcher;
		try {
			fReader = new FileReader(file);
			buf = new BufferedReader(fReader);
			String s;
			while((s = buf.readLine()) != null) {
				int i = 0;
				String[] temp = new String[2];
				matcher = pattern.matcher(s);
				while(matcher.find()) {
					temp[i] = matcher.group();
					i++;
				}
				System.out.println("lgt:" + temp[0] + ", lat:" + temp[1]);
				doGeographic(temp[0], temp[1]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try{
				if(fReader != null) {
					fReader.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static void doGeographic(String lgt, String lat) {
		FetchUrl fetchUrl = new FetchUrl();
		String url = "http://api.map.baidu.com/geocoder/v2/?"
				+ "ak=67a4a2023b2c6e275528f1a52e7ab69a&"
				+ "callback=renderReverse&location="
				+ lat
				+ ","
				+ lgt
				+ "&output=json&pois=1";
		String str = fetchUrl.getDataFromUrl(url);
		try {
			str = new String(str.getBytes(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(str);
		str = str.replace("renderReverse&&renderReverse(", "");
		str = str.replace(")", "");
		Type type = new TypeToken<BaiduResult>() {}.getType();
		UserJsonUtil uJsonUtil = new UserJsonUtil();
		BaiduResult baiduResult = uJsonUtil.parseBaiduResult(str, type);
		Result result = baiduResult.getResult();
		String formatted_address = result.getFormattedAddress();
		AddressComponent component = result.getAddressComponent();
		String city = component.getCity();
		String district = component.getDistrict();
		String provice = component.getProvince();
		String street = component.getStreet();
		System.out.println(formatted_address + ", " + city + ", " + district + ", " + provice + ", " + street);
	}
}
