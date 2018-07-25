package com.jrj.wx.json.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author bin.wang
 * @date 2018.6.4
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PrizePool {
	
	private Integer id;
	
	private Integer prizelevel;//奖品等级，1、2、3代表一二三奖
	
	private Integer currentnum;//代表奖品的数量

}
