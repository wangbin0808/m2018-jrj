package com.jrj.pay.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期处理的工具类
 * 
 * @description 日期处理的工具类
 * @author qiushun.sun
 * @date 2018.04.24
 *
 */
public class DateUtil {

	/**
	 * @author qiushun.sun
	 * 
	 * @return 获取日期的16位字符串
	 * */
	public static String getDateString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = dateFormat.format(new Date());
		return date;
	}

	/**
	 * @author qiushun.sun
	 * 
	 * @return 获取当前日期(YYYY-MM-DD)
	 * */
	public static String getCurrentDate() {
		TimeZone tz = TimeZone.getTimeZone("Etc/GMT-8");
		TimeZone.setDefault(tz);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(new Date().getTime());
		String currentDate = format.format(date);
		return currentDate;
	}

	/**
	 * @author qiushun.sun
	 * 
	 * @return 获取当前一周的日期(YYYY-MM-DD)
	 * */
	public static String getCurrentDayPreSevenDate() {
		return getDateAddDay(getCurrentDate(), -7);
	}

	/**
	 * 获取给定日期的前或者后多少天的日期
	 * 
	 * @param date  当前日期
	 * @param day  向前或者后移动的天数
	 * @return
	 */
	public static String getDateAddDay(String date, int day) {
		TimeZone tz = TimeZone.getTimeZone("Etc/GMT-8");
		TimeZone.setDefault(tz);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = null;
		Calendar cale = Calendar.getInstance();
		try {
			currentDate = format.parse(date);
			cale.setTime(currentDate);
			cale.add(Calendar.DAY_OF_YEAR, day);
		} catch (Exception e) {
		}
		return format.format(cale.getTime());
	}
}
