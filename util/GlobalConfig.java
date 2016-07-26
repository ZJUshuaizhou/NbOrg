package com.nb.org.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GlobalConfig {
	public static final String server;
	public static final String user;
	public static final String password;
	static{
		InputStream in = GlobalConfig.class.getClassLoader().getResourceAsStream("authentication.properties");
		Properties properties = new Properties();
		String s , u , p;
		try {
			properties.load(in);
			s = properties.getProperty("server","https://localhost:9443");
			u = properties.getProperty("user","admin");
			p = properties.getProperty("password", "admin");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			s = "https://localhost:9443";
			u = "admin";
			p = "admin";
			e.printStackTrace();
		}
		server = s;
		user = u;
		password = p;
	}

}
