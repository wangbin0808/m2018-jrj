package com.jrj.wx.json.service;

import com.jrj.wx.json.bean.User;
import java.util.List;

public abstract interface LuckDrawService {
	public abstract String drawRules();

	public abstract String addUser(String paramString1, String paramString2);

	public abstract List<User> getUserDraw(String paramString);
}
