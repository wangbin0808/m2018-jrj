package com.jrj.pay.vo;

import java.util.List;

import lombok.Data;

import com.jrj.pay.bean.OrderList;

/**
 * @description 调用订单接口返回信息封装类
 * @author qiushun.sun
 * @date 2018.03.24
 *
 */
@Data
public class OrderListMessVo {
	
	private String msg; // 调用信息
	private String errorMessage; // 错误信息
	private String errorCode;// 错误代码
	private String retCode; 
	private List<OrderList> orderList; // 订单列表
	
}
