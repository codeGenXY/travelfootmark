package org.franken.message.req;

/**
 * Android客户端登录消息类
 * @author frankenliu
 *
 */
public class AndroidLoginMessage extends AndroidBaseMessage{

	//用户的名称
	private String UserName;
	//用户的密码
	private String Password;

	public String getUsername() {
		return UserName;
	}

	public void setUsername(String username) {
		this.UserName = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		this.Password = password;
	}
	
	
}
