package org.franken.message.req;

/**
 * 指令信息类
 * @author frankenliu
 *
 */
public class AndroidInstructMessage {
	//指令ID
	private int id;
	//指令要发送到的机器编码(家庭编号)
	private int macnumber;
	//指令要操作的电器(对家庭内的电器再编码)
	private String appliance;
	//指令内容
	private String instruct;
	//指令发送时间
	private String time;
	//是否删除
	private int isdelete;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMacnumber() {
		return macnumber;
	}
	public void setMacnumber(int macnumber) {
		this.macnumber = macnumber;
	}
	public String getAppliance() {
		return appliance;
	}
	public void setAppliance(String appliance) {
		this.appliance = appliance;
	}
	public String getInstruct() {
		return instruct;
	}
	public void setInstruct(String instruct) {
		this.instruct = instruct;
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
