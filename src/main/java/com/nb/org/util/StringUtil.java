package com.nb.org.util;

import java.util.List;

/**
 * @author upshi
 * @date 20160120
 * String常用操作
 */
public class StringUtil {
	
	/**
	 * @author upshi
	 * @date 20160120
	 * @param str 带判断的字符
	 * @return null或空字符串返回true,否则返回false
	 */
	public static boolean isNoE(String str) {
		return str == null || "".equals(str.trim());
	}
	
	//拼接字符串以,号分割
	public static String splice(List<String> list) {
		StringBuffer str = new StringBuffer();
		for(String s : list) {
			str.append(s);
			str.append(',');
		}
		str.deleteCharAt(str.length() - 1);
		return str.toString();
	}
	
}
