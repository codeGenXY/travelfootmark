package org.franken.log;

/**
 * Log日志信息类
 * @author frankenliu
 *
 */
public class LogMessage {

	//日志ID
	private int id;
	//引发此条日志的(人或机器)操作
	private int useridormacnumber;
	//日志类型
	private String type;
	//操作结果
	private String result;
	//日志内容
	private String content;
	//日志时间
	private String time;
	//是否删除
	private int isdelete;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUseridormacnumber() {
		return useridormacnumber;
	}
	public void setUseridormacnumber(int useridormacnumber) {
		this.useridormacnumber = useridormacnumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getIsdelete() {
		return isdelete;
	}
	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;
	}
	
}
