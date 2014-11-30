package org.franken.message.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.franken.date.DateUtil;
import org.franken.log.LogMessage;
import org.franken.message.req.AndroidInstructMessage;
import org.franken.message.sql.SqlOperate;
import org.franken.message.util.AndroidMessageUtil;
import org.franken.message.util.MessageUtil;

public class HomeNetWorkCoreService {

	public static String processRequest(HttpServletRequest request){
		String respMessage = null; 
		try {
			// xml请求解析 
			Map<String, String>	requestMap = MessageUtil.parseXml(request);
			//消息事件类型
			String msgType = requestMap.get("MsgType");
			String macnumber = requestMap.get("MacNumber");
			String appliance = requestMap.get("Appliance");
			if(msgType.equals(AndroidMessageUtil.REQ_MESSAGE_TYPE_GET_INSTRUCT)){
				AndroidInstructMessage message = SqlOperate.getInstructByMacNumber(appliance, macnumber);
				if(message.getAppliance() != null && message.getInstruct() != null) {
					SqlOperate.updateInstruct(message.getId());
					respMessage = message.getAppliance()+message.getInstruct();
					LogMessage logMessage = new LogMessage();
					logMessage.setType(msgType);
					logMessage.setResult(AndroidMessageUtil.LOG_RESULT_SUCCESS);
					logMessage.setContent("获取指令成功");
					logMessage.setUseridormacnumber(Integer.parseInt(macnumber));
					logMessage.setTime(DateUtil.getNowDate());
					SqlOperate.writeLog(logMessage);
				} else {
					respMessage = "No Data!";
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return respMessage;
	}
}
