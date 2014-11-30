package org.franken.menu;

public class UrlButton extends Button{

	//菜单的响应动作类型，目前有click、view两种类型 
	private String type;
	//网页链接，用户点击菜单可打开链接，不超过256字节 
	private String url;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
