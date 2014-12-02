package org.franken.message.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.franken.date.DateUtil;
import org.franken.fetchurl.FetchUrl;
import org.franken.log.LogMessage;
import org.franken.message.req.AndroidInstructMessage;
import org.franken.message.resq.TextMessage;
import org.franken.message.sql.SqlOperate;
import org.franken.message.sql.UserDomain;
import org.franken.message.sql.UserLatAndLgt;
import org.franken.message.util.AndroidMessageUtil;
import org.franken.message.util.MessageUtil;
import org.franken.multithread.LocationTask;
import org.franken.user.jsonutil.User;

/**
 * 核心服务类
 * @author Administrator
 *
 */
public class WeChatCoreService {

	private static List<UserLatAndLgt> mLatLgts;
	private static Thread mThread;
	private static LocationTask mLocationTask;
	/**
	 * 处理微信发来的请求 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request){
		if(mLocationTask == null) {
			mLocationTask = new LocationTask();
		}
		if(mThread == null) {
			mThread = new Thread(mLocationTask);
		}
		String respMessage = null; 
		try{
			// 默认返回的文本消息内容  
			String respContent = null;  
			
			// xml请求解析  
			Map<String, String> requestMap = MessageUtil.parseXml(request);  
			
			// 发送方帐号（open_id）  
			final String fromUserName = requestMap.get("FromUserName");  
			// 公众帐号  
		    String toUserName = requestMap.get("ToUserName");  
			// 消息类型  
			String msgType = requestMap.get("MsgType");  
			
			//回复文本消息给用户，告知用户其消息已被系统接受
			TextMessage textMessage=new TextMessage();
			textMessage.setFromUserName(toUserName);
			textMessage.setToUserName(fromUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			try{
			if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
				String content = requestMap.get("Content");
				doWriteContent(fromUserName, content);
				respContent = "亲, 你发送的消息系统正在处理中……";
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)){
				
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)){
				
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)){
				
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)){
				
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)){
				String eventType = requestMap.get("Event");
				// 订阅
				if(eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
					respContent = "亲，昵称改为真实姓名有助于朋友认识你哦！";
				}
				// 取消订阅
				else if(eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)){
					 // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件 click
				else if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){
					String key = requestMap.get("EventKey");
					AndroidInstructMessage message = new AndroidInstructMessage();
					message.setMacnumber(2147483647);
					message.setAppliance("143234876");
					
					if(key.contains("3")) {
						if(key.equals("31")) {
							message.setInstruct("do_computer_off");
						} else if(key.equals("32")) {
							message.setInstruct("do_computer_open");
						} 
						SqlOperate.writeInstruct(message);
						respContent = "亲，指令发送成功, 请等待系统处理！";
						
						LogMessage logmessage = new LogMessage();
						logmessage.setType(AndroidMessageUtil.REQ_MESSAGE_TYPE_SEND_INSTRUCT);
						logmessage.setResult(AndroidMessageUtil.LOG_RESULT_SUCCESS);
						logmessage.setContent("instruct-->"+message.getInstruct()+" has sent to database and operate by wexin");
						logmessage.setUseridormacnumber(2147483647);
						logmessage.setTime(DateUtil.getNowDate());
						SqlOperate.writeLog(logmessage);
					} else {
						respContent = "亲，此功能暂不支持哦！";
					}
				}else if(eventType.equals(MessageUtil.EVENT_TYPE_VIEW)){ // 自定义菜单点击事件 view
					
				} else if(eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) { // 获取用户地理位置
					String createTime = requestMap.get("CreateTime");
					String latitude = requestMap.get("Latitude");
					String longtitude = requestMap.get("Longitude");
					String precision = requestMap.get("Precision");
					UserLatAndLgt uLatAndLgt = new UserLatAndLgt();
					uLatAndLgt.setUserId(fromUserName);
					uLatAndLgt.setCreateTime(Long.parseLong(createTime));
					uLatAndLgt.setLatitude(latitude);
					uLatAndLgt.setLongtitude(longtitude);
					uLatAndLgt.setPrecision(precision);
					if(mLatLgts == null) {
						mLatLgts = new ArrayList<UserLatAndLgt>();
					}
					boolean isAdd = mLatLgts.add(uLatAndLgt);
					if(isAdd) {
						respContent = "亲, 您的位置记录成功!";
					} else {
						respContent = "很抱歉, 您的位置暂时无法记录，请稍后重试!";
					}
					if(mLatLgts.size() >= 3) {
						mLocationTask.loadData(mLatLgts);
						mLatLgts.clear();
						if(mThread.isAlive()) {
							mThread.run();
						} else {
							mThread.start();
						}
					}
				}
				
			}
			}catch (Exception e){
				e.printStackTrace();
			}finally{
				textMessage.setContent(respContent);
				respMessage=MessageUtil.textMessageToXml(textMessage);
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
		return respMessage;
	}
	
	private static void doWriteContent(final String fromUserName, final String content) {
		Runnable runnable = new Runnable() {
			public void run() {
				FetchUrl fetchUrl = new FetchUrl();
				User user = fetchUrl.getUser(fromUserName);
				try {
					if(user != null) {
						UserDomain userDomain = new UserDomain();
						userDomain.setUsername(user.getNickname());
						userDomain.setContent(content);
						userDomain.setHeadImageUrl(user.getHeadimgurl());
						SqlOperate.writeContent(userDomain);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		Thread thread = new Thread(runnable);
		thread.start();
	}
}
