package org.franken.message.service;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.franken.baidu.map.api.AddressComponent;
import org.franken.baidu.map.api.BaiduResult;
import org.franken.baidu.map.api.Result;
import org.franken.date.DateUtil;
import org.franken.fetchurl.FetchUrl;
import org.franken.log.LogMessage;
import org.franken.message.req.AndroidInstructMessage;
import org.franken.message.resq.TextMessage;
import org.franken.message.sql.SqlOperate;
import org.franken.message.sql.UserDomain;
import org.franken.message.sql.UserLatAndLgt;
import org.franken.message.sql.UserLocation;
import org.franken.message.util.AndroidMessageUtil;
import org.franken.message.util.MessageUtil;
import org.franken.message.util.TravelPropertiesUtil;
import org.franken.user.jsonutil.User;
import org.franken.user.jsonutil.UserJsonUtil;

import com.google.gson.reflect.TypeToken;
import com.sina.sae.fetchurl.SaeFetchurl;

/**
 * 核心服务类
 * @author Administrator
 *
 */
public class WeChatCoreService {

	/**
	 * 处理微信发来的请求 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request){
		String respMessage = null; 
		final FetchUrl fetchUrl = new FetchUrl();
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
				final String content = requestMap.get("Content");
				Runnable runnable = new Runnable() {
					
					public void run() {
						User user = fetchUrl.getUser(fromUserName);
						try {
							if(user != null) {
								UserDomain userDomain = new UserDomain();
								userDomain.setUsername(user.getNickname());
								userDomain.setContent(content);
								userDomain.setHeadImageUrl(user.getHeadimgurl());
								SqlOperate.writeContent(userDomain);
								// 测试TravelPropertiesUtil文件读写
								TravelPropertiesUtil util = TravelPropertiesUtil.getInstance();
								userDomain.setContent(util.getCachedAccessToken());
								userDomain.setHeadImageUrl(util.getLastCachedAccessTokenTime()+"");
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
					boolean flag = SqlOperate.writeUserLatAndLgt(uLatAndLgt);
					if(flag) {
						respContent = "亲, 您的位置记录成功!";
					} else {
						respContent = "很抱歉, 您的位置暂时无法记录，请稍后重试!";
					}
					doLocation();
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
	
	private static boolean isEmptyStr(String str) {
		if(str == null || "".equals(str)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 处理用户地理位置
	 */
	private static void doLocation() {
		Runnable runnable = new Runnable() {
			
			public void run() {
				List<UserLatAndLgt> list = SqlOperate.readUserLatAndLgt();
				if(list != null && list.size() > 0) {
					for(UserLatAndLgt user : list) {
						String latitude = user.getLatitude();
						String longtitude = user.getLongtitude();
						if(isEmptyStr(latitude) || isEmptyStr(longtitude)) {
							continue;
						}
						String url = "http://api.map.baidu.com/geocoder/v2/?"
								+ "ak=E4805d16520de693a3fe707cdc962045&"
								+ "callback=renderReverse&location="
								+ latitude + ","
								+ longtitude
								+ "&output=json&pois=1";
						SaeFetchurl sFetchurl = new SaeFetchurl();
						String fetchData = sFetchurl.fetch(url);
						fetchData = fetchData.replace("renderReverse&&renderReverse(", "");
						fetchData = fetchData.replace(")", "");
						UserJsonUtil uJsonUtil = new UserJsonUtil();
						Type type = new TypeToken<BaiduResult>() {}.getType();
						BaiduResult baiduResult = uJsonUtil.parseBaiduResult(fetchData, type);
						if(baiduResult.getStatus() != 0) {
							continue;
						}
						Result result = baiduResult.getResult();
						String formatted_address = result.getFormattedAddress();
						AddressComponent component = result.getAddressComponent();
						String city = component.getCity();
						String district = component.getDistrict();
						String provice = component.getProvince();
						String street = component.getStreet();
							
						UserLocation location = new UserLocation();
						location.setUserId(user.getUserId());
						location.setTime(user.getCreateTime());
						location.setCountry("中国");
						location.setProvince(provice);
						location.setCity(city);
						location.setDistrict(district);
						location.setStreet(street);
						location.setPlace(formatted_address);
						try {
							location.setPrecision(Double.parseDouble(user.getPrecision()));
						} catch (Exception e) {
							location.setPrecision(0);
							e.printStackTrace();
						}
						location.setIsDelete(0);
						location.setExtra1("");
						location.setExtra2("");
						try {
							SqlOperate.writeUserLocation(location);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						SqlOperate.deleteUserLatAndLgt(user.getId());
					}
				}
				
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}
}
