package com.jrj.pay.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jrj.pay.constant.PayConstant;
import com.jrj.pay.constant.UserCookieKey;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author bin.wang
 * 
 *
 */
@Slf4j
public class CookieUseridMd5 {

	public static boolean loginState(HttpServletRequest request) {
		String myjrj_userid = CookiesUtil.getCookie(request, PayConstant.LOGIN_COOKIE_USERID, "");
		String myjrj_md5 = CookiesUtil.getCookie(request, PayConstant.LOGIN_COOKIE_JRJ_MYJRJ_MD5, "");
		String jrj_ssopassport = CookiesUtil.getCookie(request, PayConstant.LOGIN_COOKIE_JRJ_SSOPASSPORT, "");
		ServletContext context = request.getServletContext();
		WebApplicationContext ioc = WebApplicationContextUtils.getWebApplicationContext(context);
		UserCookieKey bean = ioc.getBean(UserCookieKey.class);
		System.out.println("name="+bean.getUsecookie());
		String name=new String(HexUtil.decode(bean.getUsecookie()));
		System.out.println("加密之前"+myjrj_userid+jrj_ssopassport+name);
		log.info("加密之前"+myjrj_userid+jrj_ssopassport+name);
		return ((Md5Hex.getDigestMD5(myjrj_userid+jrj_ssopassport+name)).equals(myjrj_md5))?false:true;
	}

}
