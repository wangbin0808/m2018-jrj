package com.jrj.wx.json.bean;

import java.util.Date;
/**
 * 
 * @author bin.wang
 * @date 2018.5.30
 * 这个类是用户类
 *
 */
public class User {
	private Integer id;
	
	private String phonenum;//手机号
	
	private Integer grandprize;//1、表示一等奖，2表示二等奖，3表示三等奖
	
	private Date createtime;//创建时间

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhonenum() {
		return this.phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public Integer getGrandprize() {
		return this.grandprize;
	}

	public void setGrandprize(Integer grandprize) {
		this.grandprize = grandprize;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String toString() {
		return "User [id=" + this.id + ", phonenum=" + this.phonenum + ", grandprize=" + this.grandprize
				+ ", createtime=" + this.createtime + "]";
	}
}
