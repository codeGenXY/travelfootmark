package org.franken.message.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.franken.date.DateUtil;
import org.franken.log.LogMessage;
import org.franken.message.req.AndroidElectricMessage;
import org.franken.message.req.AndroidInstructMessage;
import org.franken.message.sql.SqlOperate;
import org.franken.message.util.AndroidMessageUtil;
import org.franken.message.util.PropertiesUtil;
import org.franken.message.util.MessageUtil;

import com.google.gson.Gson;

public class AndroidCoreService {

	/**
	 * 处理android客户端发送的请求
	 * @param request post请求(包含数据)
	 * @return
	 */
	public static String processRequest(HttpServletRequest request){
		String respMessage = null; 
			try {
				// xml请求解析 
				Map<String, String> requestMap = MessageUtil.parseXml(request);
				//消息事件类型
				String msgType = requestMap.get("MsgType");
				
				if(msgType.equals(AndroidMessageUtil.REQ_MESSAGE_TYPE_LOGIN)){
					String username = requestMap.get("UserName");
					String password = requestMap.get("Password");
					int id = SqlOperate.readAndroidLoginContent(username, password);
					respMessage = String.valueOf(id);
					LogMessage message = new LogMessage();
					message.setType(AndroidMessageUtil.REQ_MESSAGE_TYPE_LOGIN);
					if(id == 0){
						message.setResult(AndroidMessageUtil.LOG_RESULT_FAIL);
						message.setContent("无此人");
						message.setUseridormacnumber(-1);//以后换为以后的mac地址
					} else {
						message.setResult(AndroidMessageUtil.LOG_RESULT_SUCCESS);
						message.setContent("查询成功");
						message.setUseridormacnumber(id);
					}
					message.setTime(DateUtil.getNowDate());
					SqlOperate.writeLog(message);
				} else if(msgType.equals(AndroidMessageUtil.REQ_MESSAGE_TYPE_CHECKUPDATE)){
					PropertiesUtil util = new PropertiesUtil();
					String serverName = util.getVersion();
					respMessage = serverName;
					
				} else if(msgType.equals(AndroidMessageUtil.REQ_MESSAGE_TYPE_DOWNLOADAPK)){
					PropertiesUtil util = new PropertiesUtil();
					String urlPath = util.getAPKUrl(AndroidMessageUtil.SAESTORAGE_DOMAIN, AndroidMessageUtil.SAESTORAGE_FILENAME);
					respMessage = urlPath;//发送APK文件所在URL给客户端, 让客户端去URL下载
					if(urlPath != null) {
						LogMessage message = new LogMessage();
						message.setType(AndroidMessageUtil.REQ_MESSAGE_TYPE_DOWNLOADAPK);
						message.setResult(AndroidMessageUtil.LOG_RESULT_SUCCESS);
						message.setContent("返回正确URL下载地址");
						message.setUseridormacnumber(-1);//以后换为以后的mac地址
						message.setTime(DateUtil.getNowDate());
						SqlOperate.writeLog(message);
					}
				} else if(msgType.equals(AndroidMessageUtil.REQ_MESSAGE_TYPE_REGISTER)){
					String username = requestMap.get("username");
					int isHasRegistered = SqlOperate.readAndroidLoginContentByUserName(username);
					if(isHasRegistered == 0) {
						String idcard = requestMap.get("idcard");
						String macnumber = requestMap.get("macNumber");
						int isInmacnumberinfo = SqlOperate.readMacNumberInfoContent(idcard, macnumber);
						if(isInmacnumberinfo == 0) {
							respMessage = "你的身份信息还未与序列号绑定, 请稍后尝试或联系客服!";
						} else {
							String password = requestMap.get("password");
							boolean isInsert = SqlOperate.insertAndroidLogin(username, password, macnumber);
							if(isInsert) {
								respMessage = "亲,由于各种原因注册未能成功, 请稍后尝试!";
							} else {
								SqlOperate.updateMacNunmberInfo(macnumber, idcard);
								respMessage = "注册成功, 请返回登录!";
								LogMessage message = new LogMessage();
								message.setType(AndroidMessageUtil.REQ_MESSAGE_TYPE_REGISTER);
								message.setResult(AndroidMessageUtil.LOG_RESULT_SUCCESS);
								message.setContent("注册成功");
								message.setUseridormacnumber(Integer.parseInt(macnumber));
								message.setTime(DateUtil.getNowDate());
								SqlOperate.writeLog(message);
							}
						}
					} else {
						respMessage = "警告: 用户名已被注册!";
					}
				} else if(msgType.equals(AndroidMessageUtil.REQ_MESSAGE_TYPE_SEND_INSTRUCT)){
					AndroidInstructMessage message = new AndroidInstructMessage();
					message.setMacnumber(Integer.parseInt(requestMap.get("macnumber")));
					message.setAppliance(requestMap.get("appliance"));
					message.setInstruct(requestMap.get("instruct"));
					SqlOperate.writeInstruct(message);
					respMessage = "Good, instruct has sent!";
					
					LogMessage logmessage = new LogMessage();
					logmessage.setType(AndroidMessageUtil.REQ_MESSAGE_TYPE_SEND_INSTRUCT);
					logmessage.setResult(AndroidMessageUtil.LOG_RESULT_SUCCESS);
					logmessage.setContent("instruct-->"+requestMap.get("instruct")+" has sent to database");
					logmessage.setUseridormacnumber(Integer.parseInt(requestMap.get("macnumber")));
					logmessage.setTime(DateUtil.getNowDate());
					SqlOperate.writeLog(logmessage);
				} else if (msgType.equals(AndroidMessageUtil.REQ_MESSAGE_TYPE_GET_ELECTRIC)) {
					int macnumber = Integer.parseInt(requestMap.get("macnumber"));
					List<Integer> listid =SqlOperate.readMacnumberToElectricByMacnumber(macnumber);
					List<AndroidElectricMessage> list = SqlOperate.getElectricbyIdList(listid);
					
					Gson gson = new Gson();
					respMessage = gson.toJson(list);
					
					LogMessage logmessage = new LogMessage();
					logmessage.setType(AndroidMessageUtil.REQ_MESSAGE_TYPE_GET_ELECTRIC);
					logmessage.setResult(AndroidMessageUtil.LOG_RESULT_SUCCESS);
					logmessage.setContent("electric get success");
					logmessage.setUseridormacnumber(macnumber);
					logmessage.setTime(DateUtil.getNowDate());
					SqlOperate.writeLog(logmessage);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return respMessage;
	}
}
