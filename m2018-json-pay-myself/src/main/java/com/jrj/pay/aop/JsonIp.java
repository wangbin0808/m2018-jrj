package com.jrj.pay.aop;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jrj.pay.util.IPUtils;

import lombok.extern.slf4j.Slf4j;
/**
 *
 * @author bin.wang
 * 2018.08.02
 * 这个类是记录是那个ip访问的，访问了那个接口
 *
 */
@Aspect
@Component
@Slf4j
public class JsonIp {
	
	@Pointcut("execution(public * com.jrj.pay.controller..*.*(..))")
	public void webLog() {
	}

	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		// 记录下请求内容
		log.info("URL : {}", request.getRequestURL().toString());
		log.info("HTTP_METHOD : {}", request.getMethod());
		log.info("IP : {}", IPUtils.getIpAddress(request));
		log.info("CLASS_METHOD : {}",
				joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		log.info("ARGS : {}", Arrays.toString(joinPoint.getArgs()));

	}

	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(Object ret) throws Throwable {
		// 处理完请求，返回内容
		log.info("RESPONSE : {}", ret);
	}

}
