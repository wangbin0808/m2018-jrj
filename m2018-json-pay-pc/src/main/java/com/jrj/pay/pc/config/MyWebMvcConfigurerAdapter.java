package com.jrj.pay.pc.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jrj.pay.pc.interceptor.InterceptorGetHeadlineContent;
import com.jrj.pay.pc.interceptor.InterceptorGetHeadlines;
import com.jrj.pay.pc.interceptor.InterceptorOfHeadlineAbstract;
import com.jrj.pay.pc.interceptor.Interceptors;

/**
 * 
 * @description 注册拦截器
 * @author bin.wang
 * @date 2018.06.20
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
		registration_getHeadlines.addPathPatterns("/json/pcpay/headline/getHeadlines");
		
		// 头条详情拦截器
		InterceptorRegistration registration_getHeadlineContent = registry.addInterceptor(new InterceptorGetHeadlineContent());
		registration_getHeadlineContent.addPathPatterns("/json/pcpay/headline/getHeadlineContent");
		
		// 免费体验/权限获取/用户是否体验过 拦截器
		InterceptorRegistration registration_userPermission = registry.addInterceptor(new Interceptors());
		registration_userPermission.addPathPatterns("/json/pcpay/headline/userPermission", "/json/pcpay/headline/freeExperience", 
													"/json/pcpay/headline/hasExperienced", "/json/pcpay/headline/orderResult",
													"/json/pcpay/headline/getLatestAssessmentInfo","/json/pcpay/headline/getUserInfoState");
		
		// 摘要拦截器
		InterceptorRegistration registration_HeadlineAbstract = registry.addInterceptor(new InterceptorOfHeadlineAbstract());
		registration_HeadlineAbstract.addPathPatterns("/json/pcpay/headline/headlineAbstract");
		//registration.excludePathPatterns("/json/pay/headline/headlineAbstract", "/json/pay/headline/hasExperienced", "/json/pay/headline/freeExperience");
	}
}
