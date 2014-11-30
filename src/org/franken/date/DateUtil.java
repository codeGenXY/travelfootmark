package org.franken.date;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期格式类
 * @author frankenliu
 *
 */
public class DateUtil {

	/**
	 * 以指定的格式获取系统时间  pattern为空时以默认格式获取
	 * @param pattern 返回系统时间的格式
	 * @return 系统时间
	 */
	public static String getNowDate(String pattern){
		String date = null;
		if(pattern == null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.CHINA);
			date = format.format(new Date());
		} else {
			SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
			date = format.format(new Date());
		}
		return date;
	}
	
	/**
	 * 以默认格式获取系统当前时间
	 * 默认格式: yyyy-MM-dd kk:mm:ss
	 * @return 系统时间
	 */
	public static String getNowDate(){
		return getNowDate(null);
	}
}
