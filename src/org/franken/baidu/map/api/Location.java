package org.franken.baidu.map.api;

/**
 * 用户地理位置信息 , 坐标
 * @author frankenliu
 *
 */
public class Location {

	// 地理位置经度
	private double lng;
	// 地理位置纬度
	private double lat;

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}
	
}
