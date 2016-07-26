package com.nb.org.testapp;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nb.org.service.IPersonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestUpshi {

	@Autowired
	IPersonService personService;
	
	@Test
	public void testAuthenticate() throws Exception {
		assertEquals(true, personService.authenticate("admin", "admin1"));
	}
	
	@Test
	public void test3(){
		boolean flag = false;
		try {
			
			flag = personService.authenticate("admin", "admin");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(flag);
	}

}
