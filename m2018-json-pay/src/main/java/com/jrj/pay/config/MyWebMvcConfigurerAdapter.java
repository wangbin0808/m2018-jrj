package com.jrj.pay.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jrj.pay.interceptor.InterceptorGetHeadlineContent;
import com.jrj.pay.interceptor.InterceptorGetHeadlines;
import com.jrj.pay.interceptor.InterceptorOfHeadlineAbstract;
import com.jrj.pay.interceptor.Interceptors;

/**
 * 
 * @description 注册拦截器
 * @author qiushun.sun
 * @date 2018.03.24
 *
 */
@Slf4j
@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		log.info("----------------MyWebMvcConfigurerAdapter---------------------");
		
		// 数据列表拦截器
		InterceptorRegistration registration_getHeadlines = registry.addInterceptor(new InterceptorGetHeadlines());
		registration_getHeadlines.addPathPatterns("/json/pay/headline/getHeadlines");
		
		// 头条详情拦截器
		InterceptorRegistration registration_getHeadlineContent = registry.addInterceptor(new InterceptorGetHeadlineContent());
		registration_getHeadlineContent.addPathPatterns("/json/pay/headline/getHeadlineContent");
		
		// 免费体验/权限获取/用户是否体验过 拦截器
		InterceptorRegistration registration_userPermission = registry.addInterceptor(new Interceptors());
		registration_userPermission.addPathPatterns("/json/pay/headline/userPermission", "/json/pay/headline/freeExperience", 
													"/json/pay/headline/hasExperienced", "/json/pay/headline/orderResult");
		
		// 摘要拦截器
		InterceptorRegistration registration_HeadlineAbstract = registry.addInterceptor(new InterceptorOfHeadlineAbstract());
		registration_HeadlineAbstract.addPathPatterns("/json/pay/headline/headlineAbstract");
		//registration.excludePathPatterns("/json/pay/headline/headlineAbstract", "/json/pay/headline/hasExperienced", "/json/pay/headline/freeExperience");
	}
}
