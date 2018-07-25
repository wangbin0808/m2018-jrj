/**
 * 
 */
package com.jrj.m.model;

import java.io.Serializable;

/**
 * @author tao.wang@jrj.com.cn
 *
 */
public class Click implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4229579752260261269L;
	
	private int iiid;
	private int total;
	
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
