package org.franken.message.util;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.sina.sae.storage.SaeStorage;

public class TravelPropertiesUtil {
	private static final String STROAGE_DOMAIN = "resources";
	private static final String STROAGE_DOMAIN_TRAVEL_FILE = "travel.properties";
	private static final String KEY_ACCESS_TOKEN = "key_access_token";
	private static final String KEY_LAST_GET_ACCESS_TOKEN_TIME = "key_last_get_access_token_time";
	private static TravelPropertiesUtil mInstance;
	private static Map<String, String> mTravelProperties = new HashMap<String, String>();
	private TravelPropertiesUtil() {
		
	}
	
	public static TravelPropertiesUtil getInstance() {
		if(mInstance == null) {
			synchronized (TravelPropertiesUtil.class) {
				if(mInstance == null) {
					mInstance = new TravelPropertiesUtil();
					mInstance.loadProperties();
				}
			}
		}
		return mInstance;
	}
	
	private void loadProperties() {
		SaeStorage storage = new SaeStorage();
		try {
			byte[] bytes = storage.read(STROAGE_DOMAIN, STROAGE_DOMAIN_TRAVEL_FILE);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
			Properties properties = new Properties();
			properties.load(byteArrayInputStream);
			for(Entry<Object, Object> entry : properties.entrySet()) {
				if(entry == null || entry.getKey() == null || isEmptyStr(entry.getKey().toString()) || entry.getValue() == null) {
					continue;
				}
				mTravelProperties.put(entry.getKey().toString(), entry.getValue().toString());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean updateProperties() {
		SaeStorage storage = new SaeStorage();
		try {
			storage.write(STROAGE_DOMAIN, STROAGE_DOMAIN_TRAVEL_FILE, createContent());
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	private String createContent() {
		StringBuffer buffer = new StringBuffer();
		
		for(Entry<String, String> entry : mTravelProperties.entrySet()) {
			buffer.append(entry.getKey());
			buffer.append("=");
			buffer.append(entry.getValue());
			buffer.append("\n");
		}
		
		return buffer.toString();
	}
	
	private boolean isEmptyStr(String str) {
		if(str == null || "".equals(str)) {
			return true;
		}
		return false;
	}
	
	private boolean setProperties(String key, String value) {
		mTravelProperties.put(key, value);
		return updateProperties();
	}
	
	public String getCachedAccessToken() {
		loadProperties();
		return mTravelProperties.get(KEY_ACCESS_TOKEN);
	}
	
	public boolean setCacheAccessToken(String accessToken) {
		return setProperties(KEY_ACCESS_TOKEN, accessToken);
	}
	
	public long getLastCachedAccessTokenTime() {
		loadProperties();
		long result = 0;
		String value = mTravelProperties.get(KEY_LAST_GET_ACCESS_TOKEN_TIME);
		try{
			result = Long.parseLong(value);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean setLastCacheAccessTokenTime(long time) {
		return setProperties(KEY_LAST_GET_ACCESS_TOKEN_TIME, String.valueOf(time));
	}
		
}
