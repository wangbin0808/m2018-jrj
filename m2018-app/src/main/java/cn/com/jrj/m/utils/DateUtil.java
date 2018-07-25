package cn.com.jrj.m.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 日期格式化工具类
 * @author yuhai.li  
 * @date 2018年1月25日 下午3:56:59
 */
public class DateUtil {
	/**
	 * @Description: 根据给定格式格式化日期
	 * @param date
	 * @param format
	 * @return String
	 * @author yuhai.li
	 * @date 2018年1月25日 下午3:57:12
	 */
	public static String format(Date date, String format) {
		if (date == null)
			return null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @Description: 字符串转化为日期类型
	 * @param dateStr 字符串
	 * @param format 字符串日期格式
	 * @return Date
	 * @author yuhai.li
	 * @date 2018年2月7日 上午11:26:29
	 */
	public static Date parseDate(String dateStr, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
}
