package com.jrj.m.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * 配置文件工具
 * 
 * @author tao.wang
 *
 */
public class ConfigUtil {
	private static final String file = "application";

	private static final ConfigUtil instance = new ConfigUtil();

	/**
	 * instance
	 * 
	 * @return
	 */
	public static ConfigUtil getInstance() {
		return instance;
	}

	/**
	 * 
	 * @param file
	 * @return
	 */
	public ResourceBundle loadFile(String file) {
		try {
			return ResourceBundle.getBundle(file, Locale.ENGLISH);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("[Property]:Can't Load property.properties");
			return null;
		}
	}

	public String getString(String key) {
		try {
			String value = loadFile(file).getString(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
