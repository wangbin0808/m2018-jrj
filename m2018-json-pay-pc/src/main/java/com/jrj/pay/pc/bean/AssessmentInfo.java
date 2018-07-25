package com.jrj.pay.pc.bean;

import lombok.Data;

/**
 * @description pc端A股头条-付费版 - 风测数据返回的数据
 * @author bin.wang
 * @date 2018.6.20
 */
@Data
public class AssessmentInfo {
	
	private String retCode;//0：成功-1：参数无效-2：获取评测详情失败2052：无风测数据，没有做过最新版的风测或者风测结果已失效
	
	private String retMsg;//表示的信息
	
	private Ext ext;//测评后的一些数据

}
