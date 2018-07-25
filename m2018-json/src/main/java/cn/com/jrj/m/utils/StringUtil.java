package cn.com.jrj.m.utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class StringUtil {
	
	/**
	 * @Description: 指定字符串使用分隔符分开，返回List
	 * @param text 原始string
	 * @param reg 分隔符
	 * @param canEmpty 是否可以为空字符串
	 * @return List<String>
	 * @author yuhai.li
	 * @date 2018年2月5日 下午5:00:07
	 */
	public static List<String> splitStr(String text, String reg, boolean canEmpty){
		List<String> list = new LinkedList<String>();
		if(text==null){
			return list;
		}
		
		if(reg==null){
			return null;
		}
		
		if(reg.equals("")){
			list.add(text);
		} else {
			if (canEmpty) {	//	可以存在空字符串和空格字符串
				list = Arrays.asList(text.split(reg));
			} else {	//	不可以存在空字符串
				String[] strs = text.split(reg);
				for(String s:strs){
					if(!s.trim().equals("")){
						list.add(s);
					}
				}
			}
		}
		
		return list;
	}
}
