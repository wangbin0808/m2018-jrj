package cn.com.jrj.m.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description: 常量类
 * @author yuhai.li  
 * @date 2018年1月24日 下午5:40:39
 */
@Component
public class Constant {
	/**
	 * 每次取导库任务池数据数量
	 */
	public static int SYNC_NEWS_NNM;
	/**
	 * redis键前缀
	 */
	public static String M_REDIS_KEY_BASE;
	/**
	 * 评论数获取接口
	 */
	public static String COMMENT_MUL_URL;
	
	public int getSYNC_NEWS_NNM() {
		return SYNC_NEWS_NNM;
	}
	
	@Value("${custom.news.sync_num}")
	public void setSYNC_NEWS_NNM(int sYNC_NEWS_NNM) {
		SYNC_NEWS_NNM = sYNC_NEWS_NNM;
	}
	public String getM_REDIS_KEY_BASE() {
		return M_REDIS_KEY_BASE;
	}
	
	@Value("${custom.redis.key_base}")
	public void setM_REDIS_KEY_BASE(String m_REDIS_KEY_BASE) {
		M_REDIS_KEY_BASE = m_REDIS_KEY_BASE;
	}
	
	public String getCOMMENT_MUL_URL() {
		return COMMENT_MUL_URL;
	}
	
	@Value("${custom.comment.mul_url}")
	public void setCOMMENT_MUL_URL(String cOMMENT_MUL_URL) {
		COMMENT_MUL_URL = cOMMENT_MUL_URL;
	}
}
