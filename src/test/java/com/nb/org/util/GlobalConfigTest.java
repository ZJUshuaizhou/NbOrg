package com.nb.org.util;

import org.junit.Test;

import com.nb.org.exception.AppUpdateException;
import com.nb.org.remote.app.AppDefaultData;

public class GlobalConfigTest {

	@Test
	public void test() {
		System.out.println(GlobalConfig.server);
		System.out.println(GlobalConfig.user);
		System.out.println(GlobalConfig.password);
	}
	@Test
	public void teststs() {
		AppDefaultData data = new AppDefaultData();
		try {
			System.out.println(data.getDefaultSTSCertAlias());
			
		} catch (AppUpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
