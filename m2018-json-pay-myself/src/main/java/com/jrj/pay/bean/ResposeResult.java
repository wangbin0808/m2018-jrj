package com.jrj.pay.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author bin.wang
 * 响应类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResposeResult {
	

	// 响应码
	private Integer code;
	// 响应信息
	private String message;
	//数据
	private Object data;
	public ResposeResult(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

}
