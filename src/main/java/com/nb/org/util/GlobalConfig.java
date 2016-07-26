package com.nb.org.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GlobalConfig {
	public static final String server;
	public static final String user;
	public static final String password;
	public static final String server_url;
	static{
		InputStream in = GlobalConfig.class.getClassLoader().getResourceAsStream("authentication.properties");
		Properties properties = new Properties();
		String s , u , p, su;
		try {
			properties.load(in);
			s = properties.getProperty("server","https://localhost:9443");
			u = properties.getProperty("user","admin");
			p = properties.getProperty("password", "admin");
			su = properties.getProperty("server_url", "https://localhost:9443/services/");
		} catch (IOException e) {
			s = "https://localhost:9443";
			u = "admin";
			p = "admin";
			su = properties.getProperty("server_url", "https://localhost:9443/services/");
			e.printStackTrace();
		}
		int lenth = s.length();
		for(int i=lenth-1;i>=0;){
			if(s.charAt(i)=='/'){
				i--;
				s = s.substring(0, i);
			}else{
				break;
			}
		}
		server = s;
		user = u;
		password = p;
		server_url = su;
	}

}
