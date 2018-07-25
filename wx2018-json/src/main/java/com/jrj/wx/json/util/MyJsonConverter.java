package com.jrj.wx.json.util;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * 这是一个Json转换的类
 * 
 * @author Administrator
 *
 */
public abstract class MyJsonConverter {
	/* 这个方法是吧对象转换成json类型的字符串 */
	public static <T> String objectToString(T t) {
		if (t == null) {
			return null;
		}
		String jsonString = JSON.toJSONString(t);

		return jsonString;

	}

	/* 这是把传过来的字符串转换成对象 */
	public static <T> T stringToObject(String name, Class<T> t) {

		if (!"".equals(name.trim())) {

			T t2 = JSON.parseObject(name, t);
			return t2;
		}
		return null;
	}

	/* 这是把list集合转换成一个json类型的字符串 */
	public static <T> String listToString(List<T> t) {

		String jsonString = JSON.toJSONString(t);
		return jsonString;
	}

	/* 这是把json类型的字符串转换成list集合 */
	public static <T> List<T> stringToList(String json, Class<T> t) {

		if (!"".equals(json.trim())) {
			List<T> list = JSON.parseArray(json, t);
			return list;
		}
		return null;
	}

	/* 这是把map集合转换成一个json类型的字符串 */
	public static <T, V> String mapToString(Map<T, V> map) {

		String jsonString = JSON.toJSONString(map);
		return jsonString;
	}

	/* 这是把一个json类型的字符串转换成一个map对象 */
	@SuppressWarnings("unchecked")
	public static <T, V> Map<T, V> stringToMap(String json, Class<?> t) {
		if (!"".equals(json.trim())) {
			Map<T, V> parseObject = (Map<T, V>) JSON.parseObject(json, t);
			return parseObject;
		}
		return null;
	}

}
