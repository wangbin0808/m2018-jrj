package com.jrj.pay.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jrj.pay.constant.PayConstant;
import com.jrj.pay.util.CookiesUtil;
import com.jrj.pay.util.StringUtils;

/**
 * 
 * @description 头条内容拦截器
 * @author qiushun.sun
 * @date 2018.03.24
 *
 */
@Slf4j
public class InterceptorGetHeadlineContent extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("InterceptorGetHeadlineContent---------------preHandle");
		
		// 跨域设置
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin")); 
	    response.setHeader("Access-Control-Allow-Methods", "*");
	    response.setHeader("Access-Control-Allow-Headers","Origin,Content-Type,Accept,token,X-Requested-With");
	    response.setHeader("Access-Control-Allow-Credentials", "true");
	    log.info("origin=" + request.getHeader("Origin") + " method=" + request.getMethod());
		if("OPTIONS".equals(request.getMethod().toUpperCase())) { // 若为options请求 不进行拦截
			return true;
		}
		log.info("--InterceptorGetHeadlineContent请求的路径是"+request.getRequestURL());
		// 登录判断
		String login_state = CookiesUtil.getCookie(request, PayConstant.LOGIN_COOKIE_KEY, "");
		if(StringUtils.isNotEmpty(login_state)){
			if(!login_state.trim().equalsIgnoreCase(PayConstant.LOGIN_COOKIE_OK)){
				log.info("InterceptorGetHeadlineContent 用户未登录");
				response.setHeader("Error-Message", "not login");
				return false;
			}
		}
		
		// 获取用户id
		String userid = CookiesUtil.getCookie(request, PayConstant.LOGIN_COOKIE_USERID, "");
		if(StringUtils.isEmptyOrBlank(userid)){
			log.info("InterceptorGetHeadlineContent 用户userid为空");
			response.setHeader("Error-Message", "myjrj_userid is invaild");
			return false;
		}
		long start = System.currentTimeMillis();
		log.info("InterceptorGetHeadlineContent----开始调用接口："+PayConstant.VERIFY_INTERFACE_URL+"/privilege/getUserPrivilegePeriod?bizCode=5&productId=8&productSubId=100080001&passportId={}",userid);
		log.info("InterceptorGetHeadlineContent passportId= " + userid);
		ServletContext context = request.getServletContext();
		WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
		RestTemplate restTemplate = (RestTemplate) applicationContext.getBean("restTemplate");
		String json = restTemplate.getForObject(PayConstant.VERIFY_INTERFACE_URL + "/privilege/getUserPrivilegePeriod?bizCode=5&productId=8&productSubId=100080001&passportId={passportId}", String.class, userid);
		JSONObject object = JSON.parseObject(json);
		if ("1".equals(object.getString("code"))) { // 请求失败--拦截
			log.info("InterceptorGetHeadlineContent 获取用户权限的接口调用失败！");
			return false;
		}
		long end = System.currentTimeMillis();
		log.info("InterceptorGetHeadlineContent----调用接口："+PayConstant.VERIFY_INTERFACE_URL+"/privilege/getUserPrivilegePeriod?bizCode=5&productId=8&productSubId=100080001&passportId={},用时：{}毫秒",userid,(end-start));
		JSONObject jsonObject = JSON.parseObject(object.get("data").toString());
		String expireDate = jsonObject.getString("expireDate"); // 过期日期
		String validDate = jsonObject.getString("validDate"); // 开始日期
		Integer days = jsonObject.getInteger("days"); // 有效天数 
		String valid = jsonObject.getString("valid"); // 是否有效
		log.info("expireDate="+expireDate+" validDate="+validDate+" days="+days+" valid="+valid);
		if ("0".equals(valid)) { // 账号有效 -- 放行
			log.info("InterceptorGetHeadlineContent 账号有效 ");
			request.setAttribute("passportId", userid);
			request.setAttribute("expireDate", expireDate);
			request.setAttribute("validDate", validDate);
			request.setAttribute("days", days);
			return true;
		} else { // 账号无效 -- 拦截
			log.info("InterceptorGetHeadlineContent 账号无效");
			response.setHeader("Error-Message", "expireDate is invalid");
			return false;
		}
	}
}
