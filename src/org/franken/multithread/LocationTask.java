package org.franken.multithread;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.franken.baidu.map.api.AddressComponent;
import org.franken.baidu.map.api.BaiduResult;
import org.franken.baidu.map.api.Result;
import org.franken.message.sql.SqlOperate;
import org.franken.message.sql.UserLatAndLgt;
import org.franken.message.sql.UserLocation;
import org.franken.user.jsonutil.UserJsonUtil;

import com.google.gson.reflect.TypeToken;
import com.sina.sae.fetchurl.SaeFetchurl;

public class LocationTask implements Runnable{

	private List<UserLatAndLgt> mDeleteList = new ArrayList<UserLatAndLgt>();
	private List<UserLatAndLgt> mLatLgts = new ArrayList<UserLatAndLgt>();
	private int mRetryTimes = 0;
	
	public void loadData(List<UserLatAndLgt> list) {
		mLatLgts.addAll(list);
	}
	/**
	 * 处理用户地理位置
	 */
	public void run() {
		if(mLatLgts != null && mLatLgts.size() > 0) {
			mRetryTimes++;
			for(UserLatAndLgt user : mLatLgts) {
				String latitude = user.getLatitude();
				String longtitude = user.getLongtitude();
				if(isEmptyStr(latitude) || isEmptyStr(longtitude)) {
					continue;
				}
				String url = "http://api.map.baidu.com/geocoder/v2/?"
						+ "ak=67a4a2023b2c6e275528f1a52e7ab69a&"
						+ "callback=renderReverse&location="
						+ latitude + ","
						+ longtitude
						+ "&output=json&pois=1";
				SaeFetchurl sFetchurl = new SaeFetchurl();
				String fetchData = sFetchurl.fetch(url);
				fetchData = fetchData.replace("renderReverse&&renderReverse(", "");
				fetchData = fetchData.replace(")", "");
				UserJsonUtil uJsonUtil = new UserJsonUtil();
				Type type = new TypeToken<BaiduResult>() {}.getType();
				BaiduResult baiduResult = uJsonUtil.parseBaiduResult(fetchData, type);
				if(baiduResult.getStatus() != 0) {
					continue;
				}
				Result result = baiduResult.getResult();
				String formatted_address = result.getFormattedAddress();
				AddressComponent component = result.getAddressComponent();
				String city = component.getCity();
				String district = component.getDistrict();
				String provice = component.getProvince();
				String street = component.getStreet();
					
				UserLocation location = new UserLocation();
				location.setUserId(user.getUserId());
				location.setTime(user.getCreateTime());
				location.setCountry("中国");
				location.setProvince(provice);
				location.setCity(city);
				location.setDistrict(district);
				location.setStreet(street);
				location.setPlace(formatted_address);
				try {
					location.setPrecision(Double.parseDouble(user.getPrecision()));
				} catch (Exception e) {
					location.setPrecision(0);
					e.printStackTrace();
				}
				location.setIsDelete(0);
				location.setExtra1(user.getPrecision());
				location.setExtra2("");
				try {
					SqlOperate.writeUserLocation(location);
					mDeleteList.add(user);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			mLatLgts.removeAll(mDeleteList);
			mDeleteList.clear();
			if(mRetryTimes > 3) {
				mRetryTimes = 0;
				return;
			}
			if(mLatLgts.size() > 0) {
				run();
			}
		}
	}
	
	private boolean isEmptyStr(String str) {
		if(str == null || "".equals(str)) {
			return true;
		}
		return false;
	}
}
