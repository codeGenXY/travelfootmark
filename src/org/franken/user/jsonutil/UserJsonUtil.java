package org.franken.user.jsonutil;

import java.lang.reflect.Type;

import org.franken.baidu.map.api.BaiduResult;

import com.google.gson.Gson;

/**
 * 解析UserJson数据的工具类
 * @author Administrator
 *
 */
public class UserJsonUtil {

	/**
	 * 解析单一Json数据
	 * @param jsonData 待解析的json数据
	 * @return 解析后的数据对象
	 */
	public User parseJsonFromJsonSinger(String jsonData){
		Gson gson=new Gson();
		User user=gson.fromJson(jsonData, User.class);
		return user;
	}
	
	/**
	 * 解析AccessToken
	 * @param jsonData 待解析的json数据
	 * @return AccessToken的实例
	 */
	public AccessToken parseAccessToken(String jsonData){
		Gson gson=new Gson();
		AccessToken accessToken=gson.fromJson(jsonData, AccessToken.class);
		return accessToken;
	}
	
	/**
	 * 解析错误信息
	 * @param jsonData
	 * @return ErrorMessage的实例
	 */
	public ErrorMessage parseErrorMessage(String jsonData){
		Gson gson=new Gson();
		ErrorMessage errorMessage=gson.fromJson(jsonData, ErrorMessage.class);
		return errorMessage;
	}
	
	public BaiduResult parseBaiduResult(String jsonData, Type type) {
		Gson gson = new Gson();
		BaiduResult baiduResult = gson.fromJson(jsonData, type);
		return baiduResult;
	}
}
