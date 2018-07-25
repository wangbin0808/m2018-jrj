package cn.com.jrj.m.domain;

import lombok.Data;

/**
 * @Description: 接口返回数据格式
 * @author yuhai.li  
 * @date 2018年2月5日 上午10:01:50
 */
@Data
public class ReturnWrapper {
	private Integer ret;
	private String msg;
	private Object data;
	
	public ReturnWrapper(){
		ret = 200;
		msg = "";
	}
}
