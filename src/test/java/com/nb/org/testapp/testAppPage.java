package com.nb.org.testapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nb.org.service.IAppInfoService;
import com.nb.org.vo.ApplicationVO;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class testAppPage {
	@Autowired
	private IAppInfoService appInfoService;
	@Test
	public void testPage(){
		ApplicationVO vo = new ApplicationVO(0, 1, 10, 2);
		vo = appInfoService.listAppsPageByPersonID(vo);
		System.out.println(vo);
	}
}
