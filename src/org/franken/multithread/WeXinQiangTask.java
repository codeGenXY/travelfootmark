package org.franken.multithread;

import org.franken.fetchurl.FetchUrl;
import org.franken.message.sql.SqlOperate;
import org.franken.message.sql.UserDomain;
import org.franken.message.util.PropertiesUtil;
import org.franken.user.jsonutil.User;

public class WeXinQiangTask implements Runnable{

	private String mFromUserName;
	private String mContent;
	public void loadData(String fromUserName, String content) {
		this.mFromUserName = fromUserName;
		this.mContent = content;
	}
	public void run() {
		if(isEmptyStr(mFromUserName) || isEmptyStr(mContent)) {
			return;
		}
		FetchUrl fetchUrl = new FetchUrl();
		User user = fetchUrl.getUser(mFromUserName);
		try {
			if(user != null) {
				UserDomain userDomain = new UserDomain();
				userDomain.setUsername(user.getNickname());
				userDomain.setContent(mContent);
				userDomain.setHeadImageUrl(user.getHeadimgurl());
				SqlOperate.writeContent(userDomain);
				if("org.franken.test.geographic".equals(mContent)) {
					PropertiesUtil util = new PropertiesUtil();
					util.doParseGeographic(userDomain);
					SqlOperate.writeContent(userDomain);
					return;
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean isEmptyStr(String str) {
		if(str == null || "".equals(str)) {
			return true;
		}
		return false;
	}

}
