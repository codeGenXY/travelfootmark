package org.franken.baidu.map.api;

/**
 * 地址信息类
 * @author frankenliu
 *
 */
public class AddressComponent {

	// 城市名
	private String city;
	// 区县名
	private String district;
	// 省名
	private String province;
	// 街道名
	private String street;
	// 街道门牌号
	private String street_number;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreet_number() {
		return street_number;
	}
	public void setStreet_number(String street_number) {
		this.street_number = street_number;
	}
	
}
