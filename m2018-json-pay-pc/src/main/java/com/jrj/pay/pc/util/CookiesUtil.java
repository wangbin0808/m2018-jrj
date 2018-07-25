package com.jrj.pay.pc.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
/**
 * @description cookie工具类
 * @author bin.wang
 * @date 2018.06.20
 *
 */
public class CookiesUtil {
	
	/**
	 * 获取指定的cookie值
	 * 
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String cookieName, String defaultCookieValue) {
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals(cookieName)){
					return cookie.getValue();
				}
			}
		}
		return defaultCookieValue;
	}
	
}
