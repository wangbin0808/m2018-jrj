package com.jrj.m.model;

import java.io.Serializable;

public class Task implements Serializable{
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
