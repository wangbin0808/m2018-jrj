/**
 * 
 */
package com.jrj.m.model;

import java.io.Serializable;

/**
 * @author tao.wang@jrj.com.cn
 *
 */
public class Comment implements Serializable {
	/**
	 *   
	 */
	private static final long serialVersionUID = -8312244685905250377L;
	private int plid;
	private int total;
	public int getPlid() {
		return plid;
	}
	public int getTotal() {
		return total;
	}
	public void setPlid(int plid) {
		this.plid = plid;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}
