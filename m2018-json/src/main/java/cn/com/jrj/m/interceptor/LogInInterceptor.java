package cn.com.jrj.m.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @Description: 登陆拦截器
 * @author yuhai.li  
 * @date 2018年1月23日 上午9:20:35
 */
//@Component	//	需要的话打开注释
@Slf4j
public class LogInInterceptor extends HandlerInterceptorAdapter {
	
	private static final String SSO_URL="http://sso.jrj.com.cn/sso/passport/checkAccessToken.jsp";
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());
        System.out.println(request.getCookies());
        System.out.println(request.getRemoteAddr());
        System.out.println(request.getRemoteHost());
        System.out.println("登录拦截");
        return true;    
    }
}
