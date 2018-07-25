package cn.com.jrj.m.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StopWatch;

//如果需要看每个方法的执行时间，放开注释
@Aspect
//@Component
public class MethodTimeAspect {

	@Around("profileMethod()")
	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch(getClass().getSimpleName());
		try {
			sw.start(pjp.getSignature().getName());
			return pjp.proceed();
		} finally {
			sw.stop();
			System.out.println(sw.prettyPrint());
		}
	}

	@Pointcut("execution(public * cn.com.jrj.m..*(..))")
	public void profileMethod() {
	}

}
