package com.jrj.pay.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
/**
 * @description cookie工具类
 * @author qiushun.sun
 * @date 2018.03.15
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
