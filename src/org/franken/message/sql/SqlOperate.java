package org.franken.message.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.franken.date.DateUtil;
import org.franken.log.LogMessage;
import org.franken.message.req.AndroidElectricMessage;
import org.franken.message.req.AndroidInstructMessage;

/**
 * 数据库处理类
 * @author Administrator
 *
 */
public class SqlOperate {

	/**
	 * 连接数据库
	 * @param url 数据可的地址
	 * @return 数据库的statement
	 * @throws Exception
	 */
	public static Statement getStatement(String url) throws Exception{
		String username="yym0y10nzn";
		String password="152wm4wxw4zj3zil0h02h3l4wkzwkxxxiwl51k01";
		String driver="com.mysql.jdbc.Driver";
		Class.forName(driver).newInstance();
		Connection con=DriverManager.getConnection(url,username,password);
		Statement statement=con.createStatement();
		return statement;
	}
	
	/**
	 * 写入数据到数据库中
	 * @param name 用户的名字
	 * @param content 用户输入的内容
	 * @throws Exception
	 */
	public static void writeContent (UserDomain userDomain) throws Exception{
		// 使用主库写数据 使用SaeUserInfo提供的静态方法获取应用的AccessKey和SecretKey
		String url="jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_travelfootmark";
		String sql="insert into wexin(username,content,head_image_url) values('"+userDomain.getUsername()+"','"+userDomain.getContent()+"','"+userDomain.getHeadImageUrl()+"')";
		Statement statement=SqlOperate.getStatement(url);
		statement.execute(sql);

	}
	
	/**
	 * 读取用户信息以及用户输入的消息
	 * @return 用户以及用户消息的集合
	 * @throws Exception
	 */
	public static List<UserDomain> readContent() throws Exception{
		// 使用从库读数据
		String URL="jdbc:mysql://r.rdc.sae.sina.com.cn:3307/app_travelfootmark";
		String sql = "SELECT * FROM wexin ORDER BY userid DESC LIMIT 30";
		Statement statement=SqlOperate.getStatement(URL);
		ResultSet set=statement.executeQuery(sql);
		List<UserDomain> list=new ArrayList<UserDomain>();
		while(set.next()){
			UserDomain userDomain=new UserDomain();
			String name=set.getString("username");
			String content=set.getString("content");
			String url = set.getString("head_image_url");
			userDomain.setUsername(name);
			userDomain.setContent(content);
			userDomain.setHeadImageUrl(url);
			list.add(userDomain);
		}
		return null==list?null:list;
	}
	
	/**
	 * 用户登录时验证用户信息
	 * @param username 用户的名称
	 * @param password 用户的命名
	 * @return 用户的机器编码(数据库有此人时), 0(数据库无此人时)
	 * @throws Exception
	 */
	public static int readAndroidLoginContent(String username, String password) throws Exception{
		int resp = 0;
		String URL="jdbc:mysql://r.rdc.sae.sina.com.cn:3307/app_travelfootmark";
		String sql = "SELECT * FROM androidlogin ";
		Statement statement=SqlOperate.getStatement(URL);
		ResultSet set=statement.executeQuery(sql);
		while(set.next()){
			String name = set.getString("username");
			String pwd = set.getString("password");
			if(name.equals(username)&&pwd.equals(password)){
				resp = Integer.parseInt(set.getString("macnumber"));
			} else {
				continue;
			}
		}
		
		return resp;
	}
	
	/**
	 * 用户注册时查询数据库是否已存在此用户名
	 * @param username 用户想要注册的用户名
	 * @return 0:还未有人注册,1:已有人注册
	 * @throws Exception
	 */
	public static int readAndroidLoginContentByUserName(String username) throws Exception {
		int resp = 0;
		String URL="jdbc:mysql://r.rdc.sae.sina.com.cn:3307/app_travelfootmark";
		String sql = "SELECT * FROM androidlogin ";
		Statement statement=SqlOperate.getStatement(URL);
		ResultSet set=statement.executeQuery(sql);
		while(set.next()){
			String name = set.getString("username");
			if(name.equals(username)){
				resp = 1;
				break;
			}
		}
		
		return resp;
	}
	
	/**
	 * 查找用户输入的身份证号与序列号是否已关联到数据库中
	 * @param idcard 身份证号
	 * @param macnumber 序列号
	 * @return 0:数据库中无此信息, 1:已关联可以注册
	 * @throws Exception
	 */
	public static int readMacNumberInfoContent(String idcard, String macnumber) throws Exception{
		int resp = 0;
		String URL="jdbc:mysql://r.rdc.sae.sina.com.cn:3307/app_travelfootmark";
		String sql = "SELECT * FROM macnumberinfo ";
		Statement statement=SqlOperate.getStatement(URL);
		ResultSet set=statement.executeQuery(sql);
		while(set.next()){
			String mac = set.getString("macnumber");
			String id = set.getString("idcard");
			if(mac.equals(macnumber)&&id.equals(idcard)){
				resp = 1;
			} 
		}
		
		return resp;
	}
	
	/**
	 * 得到指定家庭号指定电器并且不被删除的指令
	 * @param appliance 指定电器
	 * @param macnumber 指定家庭号
	 * @return AndroidInstructMessage
	 * @throws Exception
	 */
	public static AndroidInstructMessage getInstructByMacNumber(String appliance, String macnumber) throws Exception{
		AndroidInstructMessage resp = new AndroidInstructMessage();
		String URL="jdbc:mysql://r.rdc.sae.sina.com.cn:3307/app_travelfootmark";
		String sql = "SELECT * FROM do_instruct where macnumber='"+macnumber+"' AND appliance='"+appliance+"' AND isdelete=0";
		Statement statement=SqlOperate.getStatement(URL);
		ResultSet set=statement.executeQuery(sql);
		while(set.next()){
			resp.setId(set.getInt("id"));
			resp.setMacnumber(Integer.parseInt(set.getString("macnumber")));
			resp.setAppliance(set.getString("appliance"));
			resp.setInstruct(set.getString("instruct"));
			resp.setTime(set.getString("time"));
			resp.setIsdelete(set.getInt("isdelete"));
		}
		return resp;
	}
	
	/**
	 * 插入一条Log记录
	 * @param message LogMessage
	 * @return 插入是否成功
	 * @throws Exception
	 */
	public static boolean writeLog(LogMessage message) throws Exception {
		boolean result = true;
		String url="jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_travelfootmark";
		String sql="insert into log(useridormacnumber,type,result,content,time) values('"+message.getUseridormacnumber()+"','"+message.getType()+"','"+message.getResult()+"','"+message.getContent()+"','"+message.getTime()+"')";
		Statement statement=SqlOperate.getStatement(url);
		result = statement.execute(sql);
		return result;
	}
	
	/**
	 * 插入一条指令
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static boolean writeInstruct(AndroidInstructMessage message) throws Exception {
		boolean result = true;
		message.setTime(DateUtil.getNowDate());
		String url="jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_travelfootmark";
		String sql="insert into do_instruct(macnumber,appliance,instruct,time) values('"+message.getMacnumber()+"','"+message.getAppliance()+"','"+message.getInstruct()+"','"+message.getTime()+"')";
		Statement statement=SqlOperate.getStatement(url);
		result = statement.execute(sql);
		return result;
	}
	
	/**
	 * 更新指令的状态
	 * @param id 指定的id
	 * @throws Exception
	 */
	public static void updateInstruct(int id) throws Exception {
		String URL="jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_travelfootmark";
		String sql = "UPDATE do_instruct set isdelete=1 where id="+id;
		Statement statement=SqlOperate.getStatement(URL);
		statement.execute(sql);
	}
	
	/**
	 * 像客户端登录表中新增一条数据
	 * @param username 用户名
	 * @param password 用户密码
	 * @param macnumber 用户的序列号
	 * @return true:插入不成功, false:插入成功
	 * @throws Exception
	 */
	public static boolean insertAndroidLogin(String username, String password, String macnumber){
		boolean resq = true;
		String URL="jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_travelfootmark";
		String sql = "insert into androidlogin(username,password,macnumber) values('"+username+"','"+password+"','"+macnumber+"')";
		Statement statement;
		try {
			statement = SqlOperate.getStatement(URL);
			resq = statement.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resq;
	}
	
	/**
	 * 更新序列号表中的信息(已注册)
	 * @param macnumner 用户的序列号
	 * @param idcard 用户的身份证号
	 * @throws Exception
	 */
	public static void updateMacNunmberInfo(String macnumber, String idcard) throws Exception {
		String URL="jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_travelfootmark";
		String sql = "UPDATE macnumberinfo set isregister=1 where macnumber='"+macnumber+"' AND idcard='"+idcard+"'";
		Statement statement=SqlOperate.getStatement(URL);
		statement.executeUpdate(sql);
	}
	
	/**
	 * 查询macnumber关联的electricid
	 * @param macnumber
	 * @return
	 * @throws Exception
	 */
	public static List<Integer> readMacnumberToElectricByMacnumber(int macnumber) throws Exception {
		List<Integer> list = new ArrayList<Integer>();
		String URL="jdbc:mysql://r.rdc.sae.sina.com.cn:3307/app_travelfootmark";
		String sql = "select * from macnumbertoelectric where macnumber='"+macnumber+"'";
		Statement statement=SqlOperate.getStatement(URL);
		ResultSet set = statement.executeQuery(sql);
		while(set.next()) {
			int electricid = set.getInt("electricId");
			list.add(electricid);
		}
		return list;
	}
	
	/**
	 * 根据id的集合查询electric表中关联的电器
	 * @param listid
	 * @return
	 * @throws Exception
	 */
	public static List<AndroidElectricMessage> getElectricbyIdList(List<Integer> listid) throws Exception {
		List<AndroidElectricMessage> list = new ArrayList<AndroidElectricMessage>();
		String URL = "jdbc:mysql://r.rdc.sae.sina.com.cn:3307/app_travelfootmark";
		Statement statement = SqlOperate.getStatement(URL);
		Iterator<Integer> iterator = listid.iterator();
		while(iterator.hasNext()) {
			int id = iterator.next();
			String sql = "select * from electricinfo where id="+id;
			ResultSet set = statement.executeQuery(sql);
			while(set.next()) {
				AndroidElectricMessage message = new AndroidElectricMessage();
				message.setId(set.getInt("id"));
				message.setChinesename(set.getString("chinese_name"));
				message.setEnglishname(set.getString("english_name"));
				message.setContent(set.getString("content"));
				message.setIsdelete(set.getInt("isdelete"));
				list.add(message);
			}
		}
		
		return list;
	}
	
	public static boolean writeUserLocation(UserLocation location) throws Exception {
		boolean result = true;
		String url="jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_travelfootmark";
		String sql="insert into place(user_id,time,country,province,city,district,street,place,isdelete,extra1,extra2) "
				+ " values('"+location.getUserId()+"','"+location.getTime()+"','"+location.getCountry()+
				"','"+location.getProvince()+"','"+location.getCity()+"','"+location.getDistrict()+"','"
				+location.getStreet()+"','"+location.getPlace()+"','"+location.getIsDelete()+"','"+
				location.getPrecision()+"','"+location.getExtra2()+"')";
		Statement statement=SqlOperate.getStatement(url);
		result = statement.execute(sql);
		return result;
	}
	
	public static boolean writeUserLatAndLgt(UserLatAndLgt uLatAndLgt) {
		if(uLatAndLgt == null) {
			return false;
		}
		boolean result = true;
		try {
			String url="jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_travelfootmark";
			String sql="insert into user_lat_lgt(user_id,create_time,latitude,longtitude,preci) "
					+ " values('"+uLatAndLgt.getUserId()+"','"+uLatAndLgt.getCreateTime()+"','"+uLatAndLgt.getLatitude()+
					"','"+uLatAndLgt.getLongtitude()+"','"+uLatAndLgt.getPrecision()+"')";
			Statement statement=SqlOperate.getStatement(url);
			statement.execute(sql);
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}
	
	public static void deleteUserLatAndLgt(int id) {
		try {
			String url="jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_travelfootmark";
			String sql="delete from user_lat_lgt where id =" + id;
			Statement statement=SqlOperate.getStatement(url);
			statement.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<UserLatAndLgt> readUserLatAndLgt() {
		List<UserLatAndLgt> list = new ArrayList<UserLatAndLgt>();
		String URL = "jdbc:mysql://r.rdc.sae.sina.com.cn:3307/app_travelfootmark";
		try {
			Statement statement = SqlOperate.getStatement(URL);
			String sql = "select * from user_lat_lgt";
			ResultSet set = statement.executeQuery(sql);
			while(set.next()) {
				UserLatAndLgt userLatAndLgt = new UserLatAndLgt();
				userLatAndLgt.setId(set.getInt("id"));
				userLatAndLgt.setUserId(set.getString("user_id"));
				userLatAndLgt.setCreateTime(set.getLong("create_time"));
				userLatAndLgt.setLatitude(set.getString("latitude"));
				userLatAndLgt.setLongtitude(set.getString("longtitude"));
				userLatAndLgt.setPrecision(set.getString("preci"));
				list.add(userLatAndLgt);
					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
