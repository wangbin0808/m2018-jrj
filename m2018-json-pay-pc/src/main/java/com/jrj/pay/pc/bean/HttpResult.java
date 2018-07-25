package com.jrj.pay.pc.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description M站A股头条-付费版 - httpResult bean
 * @author qiushun.sun
 * @date 2018.03.24
 *
 */
@Data // get/set方法
@NoArgsConstructor // 无参构造方法
@AllArgsConstructor // 全参构造方法
public class HttpResult {

	// 响应码
	private Integer code;
	// 响应体
	private String body;

}