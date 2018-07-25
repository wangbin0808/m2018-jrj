package com.jrj.pay.pc.util;

/**
 * 
 * @description StringUtil工具类
 * @author bin.wang
 * @date 2018.06.20
 *
 */
public class StringUtils {
	/**
	 * 对象不为空且不为null
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmptyAndBlank(Object obj) {
		if (obj != null && !"".equals(obj.toString().trim())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 对象为null或者为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmptyOrBlank(Object obj) {
		if (obj == null || "".equals(obj.toString().trim())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 对象为null
	 * @param obj
	 */
	public static boolean isEmpty(Object obj) {
		if(obj == null){
			return true;
		}
		return false;
	}

	/**
	 * 对象不为null
	 * @param obj
	 */
	public static boolean isNotEmpty(Object obj) {
		if(obj != null){
			return true;
		}
		return false;
	}
	
	/**
	 * 对象为""
	 * @param obj
	 */
	public static boolean isBlank(Object obj) {
		if("".equals(obj)){
			return true;
		}
		return false;
	}

	/**
	 * 对象不为""
	 * @param obj
	 */
	public static boolean isNotBlank(Object obj) {
		if(!"".equals(obj)){
			return true;
		}
		return false;
	}
	
}
