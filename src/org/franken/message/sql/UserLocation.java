package org.franken.message.sql;

/**
 * 用户地理位置信息类
 * @author frankenliu
 *
 */
public class UserLocation {
	// 数据库ID
	private long id;
	// 用户对于公众号的OpenID
	private String userId;
	// 记录位置信息的时间
	private long time;
	// 用户当时所在的国家
	private String country;
	// 用户当时所在的省份
	private String province;
	// 用户当时所在的城市
	private String city;
	// 用户当时所在的区/县
	private String district;
	// 用户当时所在的街道
	private String street;
	// 用户当时所在城市的具体位置
	private String place;
	// 地理位置精度
	private double precision;
	// 记录是否删除
	private int isDelete;
	// 拓展字段1
	private String extra1;
	// 拓展字段2
	private String extra2;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public double getPrecision() {
		return precision;
	}

	public void setPrecision(double precision) {
		this.precision = precision;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getExtra1() {
		return extra1;
	}

	public void setExtra1(String extra1) {
		this.extra1 = extra1;
	}

	public String getExtra2() {
		return extra2;
	}

	public void setExtra2(String extra2) {
		this.extra2 = extra2;
	}
	
}
