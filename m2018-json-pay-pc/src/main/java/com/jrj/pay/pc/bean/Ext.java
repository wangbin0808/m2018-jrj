package com.jrj.pay.pc.bean;

import lombok.Data;
/**
 * @description pc端A股头条-付费版 - 风测数据的封装的
 * @author bin.wang
 * @date 2018.6.20
 */
@Data
public class Ext {
	
	private String passportId;//通行证 Id
	
	private String riskLevel;//风险级别--10:保守型（最低）|20:保守型|30:谨慎型|40:稳健型|50:积极型|60:激进型
	
	private Double targetScore;//目标得分
	
	private Double score;//评测得分（1位小数）

}
