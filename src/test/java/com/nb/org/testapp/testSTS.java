package com.nb.org.testapp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nb.org.application.client.STSMgtClient;
import com.nb.org.dao.IAppSTSDao;
import com.nb.org.domain.AppSTS;
import com.nb.org.util.GlobalConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class testSTS {
	
	@Autowired
	IAppSTSDao dao;
	
	@Test
	public void teststub() throws Exception {
		STSMgtClient client = new STSMgtClient(GlobalConfig.server);
		System.out.println(client.getSTSApps()[0].getCertAlias());
	}
	
	
	@Test
	public void testSts(){
		AppSTS app = new AppSTS("bb","b","c");
		dao.insertSTS(app);
		System.out.println(dao.getSTSByAppName("bb"));
		dao.deleteSTS(1);
	}
	
}


