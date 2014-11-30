package org.franken.message.sql;

public class UserDomain {

	//用户的名字
	private String username;
	//用户输入的内容
	private String content;
	//用户的头像
	private String headImageUrl;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getHeadImageUrl() {
		return headImageUrl;
	}
	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}
	
}
