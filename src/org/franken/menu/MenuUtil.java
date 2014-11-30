package org.franken.menu;

import java.util.HashMap;
import java.util.Map;

import org.franken.fetchurl.GetStaticAccessToken;

import net.sf.json.JSONObject;

import com.sina.sae.fetchurl.SaeFetchurl;

/**
 * 创建自定义菜单
 * SaeFetchurl的post方法使用暂时不成功
 * 当前使用微信的微信公众平台接口调试工具来创建自定义菜单
 * String json = JSONObject.fromObject(getMenu()).toString();
 * 实现： 将json复制到微信公众平台接口调试工具 
 *      获取access_token
 * @author frankenliu
 *
 */
public class MenuUtil {

	public static Menu getMenu(){
		ClickButton button11 = new ClickButton();
		button11.setType("click");
		button11.setName("天气预报");
		button11.setKey("11");
		
		ClickButton button12 = new ClickButton();
		button12.setType("click");
		button12.setName("公交查询");
		button12.setKey("12");
		
		ClickButton button13 = new ClickButton();
		button13.setType("click");
		button13.setName("周边搜索");
		button13.setKey("14");
		
		ClickButton button14 = new ClickButton();
		button14.setType("click");
		button14.setName("历史上的今天");
		button14.setKey("15");
		
		UrlButton button21 = new UrlButton();
		button21.setType("view");
		button21.setName("百度");
		button21.setUrl("http://www.baidu.com");
		
		UrlButton button22 = new UrlButton();
		button22.setType("view");
		button22.setName("hao123");
		button22.setUrl("http://www.hao123.com");
		
		UrlButton button23 = new UrlButton();
		button23.setType("view");
		button23.setName("微信墙");
		button23.setUrl("http://wexinqiang.sinaapp.com/");
		
		ClickButton button31 = new ClickButton();
		button31.setType("click");
		button31.setName("关闭电脑");
		button31.setKey("31");
		
		ClickButton button32 = new ClickButton();
		button32.setType("click");
		button32.setName("开启电脑");
		button32.setKey("32");
		
//		ClickButton button33 = new ClickButton();
//		button33.setType("click");
//		button33.setName("窗帘停止");
//		button33.setKey("33");

		ComplexButton complexButton1 = new ComplexButton();
		complexButton1.setName("生活助手");
		complexButton1.setSub_button(new ClickButton[]{button11,button12,button13,button14});
		
		ComplexButton complexButton2 = new ComplexButton();
		complexButton2.setName("休闲驿站");
		complexButton2.setSub_button(new UrlButton[]{button21,button22,button23});
		
		ComplexButton complexButton3 = new ComplexButton();
		complexButton3.setName("更多体验");
		complexButton3.setSub_button(new ClickButton[]{button31,button32});
		
		Menu menu =new Menu();
		menu.setButton(new Button[]{complexButton1,complexButton2,complexButton3}); 
		return menu;
	}
	
	public static void main(String[] args){
		
		String json = JSONObject.fromObject(getMenu()).toString();
		String access_token = GetStaticAccessToken.getAccessToken();
		SaeFetchurl fetchurl = new SaeFetchurl();
		fetchurl.setMethod("POST");
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("", json);
		fetchurl.setPostData(maps);
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token;
		String str = fetchurl.fetch(url);
		System.out.println(json);
		System.out.println(str);
		
	}
}
