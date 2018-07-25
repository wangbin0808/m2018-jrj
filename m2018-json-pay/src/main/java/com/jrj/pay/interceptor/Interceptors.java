package com.jrj.pay.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jrj.pay.constant.PayConstant;
import com.jrj.pay.util.CookiesUtil;
import com.jrj.pay.util.StringUtils;

/**
 * 
 * @description 拦截器: 免费体验/权限获取/用户是否体验过
 * @author qiushun.sun
 * @date 2018.04.12
 *
 */
@Slf4j
public class Interceptors extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("Interceptors---------------preHandle");

		// 跨域设置
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Headers", "Origin,Content-Type,Accept,token,X-Requested-With");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		log.info("origin=" + request.getHeader("Origin") + " method=" + request.getMethod());
		if ("OPTIONS".equals(request.getMethod().toUpperCase())) { // 若为options请求
			return true;
		}

		// 登录判断
		String login_state = CookiesUtil.getCookie(request, PayConstant.LOGIN_COOKIE_KEY, "");
		if(StringUtils.isNotEmpty(login_state)){
			if(!login_state.trim().equalsIgnoreCase(PayConstant.LOGIN_COOKIE_OK)){
				log.info("AdminManagerInterceptor 用户未登录");
				response.setHeader("Error-Message", "not login");
				return false;
			}
		}
		
		// 获取用户id
		String userid = CookiesUtil.getCookie(request, PayConstant.LOGIN_COOKIE_USERID, "");
		if(StringUtils.isEmptyOrBlank(userid)){
			log.info("AdminManagerInterceptor 用户userid为空");
			response.setHeader("Error-Message", "myjrj_userid is invaild");
			return false;
		}
		request.setAttribute("myjrj_userid", userid);
		log.info("AdminManagerInterceptor 用户已登录 ，用户id为：" + userid);
		
		return true;
	}
}
