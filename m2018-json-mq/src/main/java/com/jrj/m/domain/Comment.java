package com.jrj.m.domain;

import java.io.Serializable;

public class Comment implements Serializable {
	private int plid;
	private int total;
	public Comment(int _plid,int _total){
		plid=_plid;
		total=_total;
	}
	public int getPlid() {
		return plid;
	}
	public void setPlid(int plid) {
		this.plid = plid;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
}
