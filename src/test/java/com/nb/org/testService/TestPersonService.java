package com.nb.org.testService;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nb.org.domain.Department;
import com.nb.org.domain.Person;
import com.nb.org.exception.DepartmentException;
import com.nb.org.exception.PersonException;
import com.nb.org.service.IDepartmentService;
import com.nb.org.service.IPersonService;
import com.nb.org.sync.util.MD5;
import com.nb.org.util.SNGenerator;


@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner�?
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})

public class TestPersonService {

	
	@Resource
	private IPersonService personService;
	@Resource
	private IDepartmentService iDepartmentService;
	@Resource
	private SNGenerator snGenerate;
	
	@Test
	public void test() throws Exception {
//		int password = personService.changePassword("admin", "admin", "123456");
//		System.out.println(password);
		
	}
	
	@Test
	public void testSavePerson(){
		Department department = null;
		department = iDepartmentService.selectDepByName("杭州电子政务办用户");
		Person person = new Person();
		person.setName("何炜");
		person.setUsername("wayneabc");
		person.setGender(0);
		person.setTelephone("18817391644");
		person.setMobilephone("18817391644");
		person.setEmail("way_ho@126.com");
		//设置按规则生成的SN
		person.setSn(snGenerate.generatePersonSN(department));
		person.setCreateDep(department);
		person.getDeps().add(department);
		try {
			personService.insertPersonBySync(person, department, "普通员工", 0,"wayneabc");
		} catch (PersonException e) {
			
		}
	}
	@Test
	public void deletePerson(){
		int res = 0;
		try {
			res =personService.delPerson(35);
		} catch (PersonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(res);
	}
	
}
