package org.franken.message.req;

public class AndroidRegisterMessage extends AndroidBaseMessage{

	//用户的身份证号
	private String idcard;
	//用户已购买的机器序列号
	private String macNumber;
	//用户的密码
	private String password;
	//用户注册的昵称
	private String username;
	
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getMacNumber() {
		return macNumber;
	}
	public void setMacNumber(String macNumber) {
		this.macNumber = macNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
