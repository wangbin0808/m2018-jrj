package cn.com.jrj.m.utils;

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
}
