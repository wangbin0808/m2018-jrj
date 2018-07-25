package com.jrj.pay.pc.bean;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * @description pc端A股头条-付费版 - 头条列表封装类
 * @author bin.wang
 * @date 2018.06.19
 *
 */
@Data
public class AStockHeadline implements Serializable {

	private static final long serialVersionUID = 1L;
	private String iiid = ""; // 主键
	private String title = ""; // 标题
	private String detail = ""; // 摘要
	private String makeDate = ""; // 发布日期
	private String paperMediaSource; // 媒体来源ID
	private String infoUrl = ""; // 生成专题的URL
	private String imgUrl = ""; // 图片网址
	private String keyWord = ""; // 关键字

}
