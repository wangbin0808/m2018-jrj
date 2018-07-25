package cn.com.jrj.m.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description: 常量类（后续可提取到配置文件中）
 * @author yuhai.li  
 * @date 2018年2月7日 上午8:48:55
 */
@Component
public class Constant {
	/**
	 * redis键前缀
	 */
	public static String M_REDIS_KEY_BASE;
	public static int M_REDIS_KEY_CACHE;
	
	public String getM_REDIS_KEY_BASE() {
		return M_REDIS_KEY_BASE;
	}
	
	@Value("${custom.redis.key_base}")
	public void setM_REDIS_KEY_BASE(String m_REDIS_KEY_BASE) {
		M_REDIS_KEY_BASE = m_REDIS_KEY_BASE;
	}
	
	public int getM_REDIS_KEY_CACHE() {
		return M_REDIS_KEY_CACHE;
	}
	
	@Value("${custom.redis.cache_second}")
	public void setM_REDIS_KEY_CACHE(int m_REDIS_KEY_CACHE) {
		M_REDIS_KEY_CACHE = m_REDIS_KEY_CACHE;
	}
	
}
