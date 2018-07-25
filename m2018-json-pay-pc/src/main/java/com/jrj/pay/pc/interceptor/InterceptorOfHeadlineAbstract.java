package com.jrj.pay.pc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jrj.pay.pc.util.StringUtils;

/**
 * 
 * @description 拦截器: 新闻摘要
 * @author bin.wang
 * @date 2018.06.20
 *
 */
@Slf4j
public class InterceptorOfHeadlineAbstract extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("InterceptorOfHeadlineAbstract---------------preHandle");

		StringBuffer buffer = request.getRequestURL();
		String name = buffer.toString();
		name=name.substring(0,name.lastIndexOf(":")).substring(name.substring(0,name.lastIndexOf(":")).indexOf("//")+2);
		System.out.println("name==="+name);
		if("localhost".equals(name)){
			System.out.println("-----host------");
			return true;
		}
		// 跨域设置
//		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//		response.setHeader("Access-Control-Allow-Methods", "*");
//		response.setHeader("Access-Control-Allow-Headers", "Origin,Content-Type,Accept,token,X-Requested-With");
//		response.setHeader("Access-Control-Allow-Credentials", "true");
//		log.info("origin=" + request.getHeader("Origin") + " method=" + request.getMethod());
//		if ("OPTIONS".equals(request.getMethod().toUpperCase())) { // 若为options请求
//			return true;
//		}
		
		String iiid = request.getParameter("iiid");
		if(StringUtils.isEmptyOrBlank(iiid)){
			log.info("InterceptorOfHeadlineAbstract iiid为空");
			response.setHeader("Error-Message", "iiid is null");
			return false;
		}
		log.info("InterceptorOfHeadlineAbstract iiid= " + iiid);
		return true;

	}
}
