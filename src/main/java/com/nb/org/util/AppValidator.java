package com.nb.org.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huangxin
 * xin
 * 2016年1月22日
 */
public class AppValidator {
	public static boolean isURL(String urlString) {
		String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]" ;
		Pattern patt = Pattern. compile(regex );
		Matcher matcher = patt.matcher(urlString);
		boolean isMatch = matcher.matches();
		return isMatch;
	}
	public static boolean issuerValue(String issuer){
		String regex = "^[.-_a-zA-Z0-9]+$";
		Pattern patt = Pattern. compile(regex );
		Matcher matcher = patt.matcher(issuer);
		boolean isMatch = matcher.matches();
		return isMatch;
	}
}
