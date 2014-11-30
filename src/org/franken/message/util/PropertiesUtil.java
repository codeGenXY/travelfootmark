package org.franken.message.util;

import java.io.UnsupportedEncodingException;
import com.sina.sae.storage.SaeStorage;

/**
 * 服务器上资源处理类
 * @author frankenliu
 *
 */
public class PropertiesUtil {
	private static final String STROAGE_DOMAIN = "resources";
	private static final String STROAGE_DOMAIN_UPDATE_FILE = "update.properties";
	/**
	 * 得到服务器上资源文件的版本号
	 * @return 返回得到的版本号
	 */
	public String getVersion(){
		SaeStorage storage = new SaeStorage();
		String str = null;
		try {
			str = new String(storage.read(STROAGE_DOMAIN, STROAGE_DOMAIN_UPDATE_FILE),"utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			return str;
	}
	
	/**
	 * 获取SaeStorage上文件的路径
	 * @param domain SaeStorage上的一级目录
	 * @param filename SaeStorage上的一级目录中的文件名
	 * @return urlPath
	 */
	public String getAPKUrl(String domain, String filename){
		SaeStorage storage = new SaeStorage();
		String urlPath = storage.getUrl(domain, filename);
		return urlPath;
	}
	
}
