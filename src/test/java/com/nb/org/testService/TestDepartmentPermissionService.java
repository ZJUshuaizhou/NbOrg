package com.nb.org.testService;

import javax.annotation.Resource;

import org.jivesoftware.smackx.provider.VCardProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nb.org.service.IDepartmentPermissionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class TestDepartmentPermissionService {
	@Resource
	private IDepartmentPermissionService departmentPermissionService;
	
	@Test
	public void test(){
		int a=  8;
		int b = 7;
		int c = 8;
		try {
			System.out.println(departmentPermissionService.getDepartmantOperationPermission(a, b, c));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
