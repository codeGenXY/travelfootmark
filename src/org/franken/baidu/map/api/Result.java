package org.franken.baidu.map.api;

import java.util.List;

public class Result {

	// 用户地理位置信息, 坐标
	private Location location;
	// 结构化地址信息
	private String formatted_address;
	// 所在商圈信息，如 "人民大学,中关村,苏州街
	private String business;
	// 地址信息类
	private AddressComponent addressComponent;
	// 周围信息
	private List<Pois> pois;
	// 城市编码
	private String cityCode;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getFormattedAddress() {
		return formatted_address;
	}

	public void setFormattedAddress(String formatted_address) {
		this.formatted_address = formatted_address;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public AddressComponent getAddressComponent() {
		return addressComponent;
	}

	public void setAddressComponent(AddressComponent addressComponent) {
		this.addressComponent = addressComponent;
	}

	public List<Pois> getPois() {
		return pois;
	}

	public void setPois(List<Pois> pois) {
		this.pois = pois;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	
}
