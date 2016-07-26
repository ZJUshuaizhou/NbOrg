package com.nb.org.testService;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nb.org.domain.SyncOrg;
import com.nb.org.service.ISyncOrgService;

@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner�?
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class TestSyncOrgService {
	@Resource
	private ISyncOrgService iSyncOrgService;	
	@Test
	public void testInsert(){
		SyncOrg syncOrg = new SyncOrg();
		syncOrg.setOrgcoding("001");
		syncOrg.setDepId(1);
		int res= 0;
		res = iSyncOrgService.insert(syncOrg);
		System.out.println(res);
	}
	@Test
	public void testSelect(){
		SyncOrg syncOrg  = iSyncOrgService.selectByOrgCoding("001");
		System.out.println(syncOrg.toString());
	}
	@Test
	public void testDelete(){
		int res= 0;
		res = iSyncOrgService.deteleByOrgCoding("001");
		System.out.println(res);
	}
}
