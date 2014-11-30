package org.franken.menu;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sina.sae.fetchurl.SaeFetchurl;

public class TodayInHistoryService {

	/**
	 * 发送get请求获取网页的信息
	 * @return
	 */
	private String fetchUrl(){
		SaeFetchurl fetchurl = new SaeFetchurl();
		fetchurl.setMethod("GET");
		String data = fetchurl.fetch("http://www.rijiben.com/");
		return data;
	}
	
	/**
	 * 获取前/后n天日期(M月d日) 
	 * @param diff 前/后n(以前用-n）
	 * @return M月d日
	 */
	private String getMonthDay(int diff) {  
		DateFormat df = new SimpleDateFormat("M月d日");  
	    Calendar c = Calendar.getInstance();  
		c.add(Calendar.DAY_OF_YEAR, diff);  
		return df.format(c.getTime());  
	}  
	
	/**
	 * 
	 * @param html
	 * @return
	 */
	private String extractHtml(String html){
		StringBuffer buffer = null;
		
		String dateTag = getMonthDay(0);  
		
		Pattern pattern = Pattern.compile("(\n*.*)(<div class=\"listren\">)(\n*.*?)(</div>)(\n*.*)");
		Matcher matcher = pattern.matcher(html);
		System.out.println(html.length());
		System.out.println(matcher.matches());
		if (matcher.matches()) {  
			buffer = new StringBuffer();  
			if (matcher.group(3).contains(getMonthDay(-1)))  
			dateTag = getMonthDay(-1);  
			// 拼装标题  
			buffer.append("≡≡ ").append("历史上的").append(dateTag).append(" ≡≡").append("\n\n");  
			// 抽取需要的数据  
			for (String info : matcher.group(3).split("  ")) {  
			info = info.replace(dateTag, "").replace("（图）", "").replaceAll("</?[^>]+>", "").trim();  
			// 在每行末尾追加2个换行符  
			if (!"".equals(info)) {  
			buffer.append(info).append("\n\n");  
			      }  
			   }  
			}  
			// 将buffer最后两个换行符移除并返回  
			return (null == buffer) ? null : buffer.substring(0, buffer.lastIndexOf("\n\n"));  

	}
	
	public String getTodayInHistoryInfo(){
		 // 获取网页源代码  
		String html = fetchUrl();  
		// 从网页中抽取信息  
		String result = extractHtml(html);  
		return result;  

	}
	
	public static void main(String[] args){
		TodayInHistoryService service = new TodayInHistoryService();
		System.out.println(service.getTodayInHistoryInfo());
		
		
		Pattern p = Pattern.compile("a*b");
		Matcher m = p.matcher("aaaaab");
		System.out.println(m.matches());
	}
}
