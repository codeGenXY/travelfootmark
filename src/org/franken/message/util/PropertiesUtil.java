package org.franken.message.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.franken.baidu.map.api.AddressComponent;
import org.franken.baidu.map.api.BaiduResult;
import org.franken.baidu.map.api.Result;
import org.franken.fetchurl.FetchUrl;
import org.franken.message.sql.SqlOperate;
import org.franken.message.sql.UserLocation;
import org.franken.user.jsonutil.UserJsonUtil;

import com.google.gson.reflect.TypeToken;
import com.sina.sae.storage.SaeStorage;

/**
 * 服务器上资源处理类
 * @author frankenliu
 *
 */
public class PropertiesUtil {
	private static final String STROAGE_DOMAIN = "resources";
	private static final String STROAGE_DOMAIN_UPDATE_FILE = "update.properties";
	private static final String STROAGE_DOMAIN_LAT_LGT_FILE = "latandlgt.txt";
	/**
	 * 得到服务器上资源文件的版本号
	 * @return 返回得到的版本号
	 */
	public String getVersion(){
		SaeStorage storage = new SaeStorage();
		String str = null;
		try {
			str = new String(storage.read(STROAGE_DOMAIN, STROAGE_DOMAIN_UPDATE_FILE),"utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			return str;
	}
	
	public void doParseGeographic() {
		SaeStorage storage = new SaeStorage();
		try {
			ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(storage.read(STROAGE_DOMAIN, STROAGE_DOMAIN_LAT_LGT_FILE));
			BufferedReader reader = new BufferedReader(new InputStreamReader(arrayInputStream));
			Pattern pattern = Pattern.compile("[0-9]+.[0-9]+");
			Matcher matcher;
			String s;
			while((s = reader.readLine()) != null) {
				int i = 0;
				String[] temp = new String[2];
				matcher = pattern.matcher(s);
				while(matcher.find()) {
					temp[i] = matcher.group();
					i++;
				}
				doGeographic(temp[0], temp[1]);
			}
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void doGeographic(String lgt, String lat) {
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
		UserLocation location = new UserLocation();
		location.setUserId("test");
		location.setTime(System.currentTimeMillis());
		location.setCountry("中国");
		location.setProvince(provice);
		location.setCity(city);
		location.setDistrict(district);
		location.setStreet(street);
		location.setPlace(formatted_address);
		try {
			location.setPrecision(Double.parseDouble("0.0"));
		} catch (Exception e) {
			location.setPrecision(0);
			e.printStackTrace();
		}
		location.setIsDelete(0);
		location.setExtra1("0.0");
		location.setExtra2("");
		try {
			SqlOperate.writeUserLocation(location);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取SaeStorage上文件的路径
	 * @param domain SaeStorage上的一级目录
	 * @param filename SaeStorage上的一级目录中的文件名
	 * @return urlPath
	 */
	public String getAPKUrl(String domain, String filename){
		SaeStorage storage = new SaeStorage();
		String urlPath = storage.getUrl(domain, filename);
		return urlPath;
	}
	
}
