package com.nb.org.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SyncProperties {
	public static final String servicecode;
	public static final String servicepwd;
	public static final String SimpleAuthServiceAddress;
	public static final String initialOrg;
	public static final String nbDepId;
	static{
		InputStream in = SyncProperties.class.getClassLoader().getResourceAsStream("sync.properties");
		Properties properties = new Properties();
		String pra1,pra2,pra3,pra4,pra5;
		try {
			properties.load(in);
			pra1 = properties.getProperty("servicecode","nborg");
			pra2 = properties.getProperty("servicepwd","nborgpwd");
			pra3 = properties.getProperty("SimpleAuthServiceAddress", "http://115.236.188.141:8086/sso/service/SimpleAuthService");
			pra4 = properties.getProperty("initialOrg", "005");
			pra5 = properties.getProperty("nbDepId", "005");
		} catch (IOException e) {
			pra1 = "nborg";
			pra2 = "nborgpwd";
			pra3 = "http://115.236.188.141:8086/sso/service/SimpleAuthService";
			pra4 = "005";
			pra5 = "1";
			e.printStackTrace();
		}
		servicecode = pra1;
		servicepwd = pra2;
		SimpleAuthServiceAddress = pra3;
		initialOrg = pra4;
		nbDepId = pra5;
	}

}
