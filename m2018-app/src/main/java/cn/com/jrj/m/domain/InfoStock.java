package cn.com.jrj.m.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * @Description: 新闻相关股票
 * @author yuhai.li  
 * @date 2018年1月25日 上午11:02:20
 */
@Data
public class InfoStock implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -3845634053059110224L;
	
	/**
	 * 股票id
	 */
	private int stockId;
	/**
	 * 股票代码
	 */
	private String stockCode;
	/**
	 * 股票名称
	 */
	private String stockName;
}
