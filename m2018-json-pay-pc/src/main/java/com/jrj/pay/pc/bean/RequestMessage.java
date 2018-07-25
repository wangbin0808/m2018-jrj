package com.jrj.pay.pc.bean;

import lombok.Data;

/**
 * @description pc端A股头条-付费版 - 接口调用返回信息封装类
 * @author bin.wang
 * @date 2018.06.21
 *
 */
@Data
public class RequestMessage {

	private String msg; // 接口调用信息
	private String retCode; // 访问是否成功的标记 0：成功  1：失败
	private String hasPermission; // 是否有获取信息的权限 0:有  1:没有
	private String isLogined; // 是否登录 0:已登录  1:未登录
	private String isFreeUsered; // 是否已经免费体验过 0：未体验  1：已体验
	private String status; // 获取订单状态   0:支付成功  1:未支付成功
	private int days;//表示天数

}
