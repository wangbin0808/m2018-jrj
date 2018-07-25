/**
 * 
 */
package com.jrj.m.domain;

import java.io.Serializable;

/**
 * @author tao.wang@jrj.com.cn
 *
 */
public class Click implements Serializable {
	private int iiid;	
	private int total;
	public Click(int _iiid,int _total){
		iiid=_iiid;
		total=_total;
	}
	public int getIiid() {
		return iiid;
	}
	public int getTotal() {
		return total;
	}
	public void setIiid(int iiid) {
		this.iiid = iiid;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}
