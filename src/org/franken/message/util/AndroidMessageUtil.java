package org.franken.message.util;

public class AndroidMessageUtil {

	//客户端请求事件类型-->登录事件
	public static final String REQ_MESSAGE_TYPE_LOGIN = "login";
	//客户端请求事件类型-->注册事件
	public static final String REQ_MESSAGE_TYPE_REGISTER = "register";
	//客户端请求事件类型-->操作事件
	public static final String REQ_MESSAGE_TYPE_SEND_INSTRUCT = "send_instruct";
	//客户端请求事件类型-->检测更新事件
	public static final String REQ_MESSAGE_TYPE_CHECKUPDATE = "checkupdate";
	//客户端请求事件类型-->下载APK事件
	public static final String REQ_MESSAGE_TYPE_DOWNLOADAPK = "downloadapk";
	//客户端请求事件类型-->获取电器事件
	public static final String REQ_MESSAGE_TYPE_GET_ELECTRIC = "get_electric";
	//家庭网关请求事件类型-->获取指令事件
	public static final String REQ_MESSAGE_TYPE_GET_INSTRUCT = "get_instruct";
	//SaeStorage上的一级目录 
	public static final String SAESTORAGE_DOMAIN = "resources";
	//SaeStorage上的一级目录中的文件名
	public static final String SAESTORAGE_FILENAME = "SmartHome1.apk";
	//Log日志事件结果-->操作成功
	public static final String LOG_RESULT_SUCCESS = "success";
	//Log日志事件结果-->操作失败
	public static final String LOG_RESULT_FAIL = "fail";
}
