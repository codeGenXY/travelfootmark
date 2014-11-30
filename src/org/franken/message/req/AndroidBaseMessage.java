package org.franken.message.req;

/**
 * Android客户端消息基类
 * @author frankenliu
 *
 */
public class AndroidBaseMessage {

	//发送请求的时间类型
	private String MsgType;

	public String getType() {
		return MsgType;
	}

	public void setType(String type) {
		this.MsgType = type;
	}
}
