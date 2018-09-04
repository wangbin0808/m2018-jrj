//如果要记录所有方法执行的时间，可以把这个加上
package com.jrj.pay.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author bin.wang
 * 2018.08.02
 * 这个类是记录所有方法执行的时间
 */
@Aspect
@Component
@Slf4j
public class MethodTimeAspect {
	
	@Pointcut(value="execution(public * com.jrj.pay..*.*(..))")
	public void pointCut(){}
	
	@Around(value="pointCut()")
	public Object around(ProceedingJoinPoint joint) throws Throwable{
		StopWatch stop=new StopWatch(getClass().getSimpleName());
		try {
			stop.start(joint.getSignature().getName());
			return joint.proceed();
		} finally {
			stop.stop();
			System.out.println(stop.prettyPrint());
			log.info(stop.prettyPrint());
		}
	}

}
