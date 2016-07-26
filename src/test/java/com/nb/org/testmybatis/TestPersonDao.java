package com.nb.org.testmybatis;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nb.org.domain.Department;
import com.nb.org.domain.Person;
import com.nb.org.exception.DepartmentException;
import com.nb.org.exception.PersonException;
import com.nb.org.service.IDepartmentService;
import com.nb.org.service.IPersonService;

@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner�?
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})

public class TestPersonDao {
	private static Logger logger = Logger.getLogger(TestPersonDao.class);
//	private ApplicationContext ac = null;
	@Resource
	private IPersonService personService = null;
	
	@Resource
	private IDepartmentService departmentService = null;

//	@Before
//	public void before() {
//		ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//		userService = (IUserService) ac.getBean("userService");
//	}
	@Test
	public void testInsertPer() throws Exception {
		Department dep=departmentService.getDepartmentById(2);
	//	Department dep2=departmentService.getDepartmentById(2);
	Person per=new Person();
	per.setName("小方");
	per.setUsername("XZ");
	per.setEmail("12345@qq.com");
	per.setGender(1);
	per.setMobilephone("123456");
	per.setTelephone("87582581");
	per.setSn("FB004");
	per.setCreateDep(dep);
	personService.insertPerson(per);
	//per=personService.selectPersonsByName(per.getName()).get(0);
	System.out.println(per);
//	personService.saveRelativity(per, dep2, "局长", 1);
	personService.saveRelativity(per, dep, "市长", 0);
		
	}
	@Test
	public void testGetPer() {
	List<Person> list=new ArrayList<Person>();
	try {
		list = personService.selectPersonsByName("小周");
	} catch (PersonException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	for(Person per:list){
		System.out.println(per);
	}
		
	}
	@Test
	public void testUpdate() {
	Person p=new Person();
	try {
		p = personService.getPersonById(1);
	} catch (PersonException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Department dep=new Department();
	try {
		dep = departmentService.getDepartmentById(1);
	} catch (DepartmentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	personService.updateRelativity(p, dep, "科长", 0);
	
		
	}
	@Test
	public void testDelete() {
	Person p=new Person();
	try {
		p = personService.getPersonById(1);
	} catch (PersonException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Department dep=new Department();
	try {
		dep = departmentService.getDepartmentById(1);
	} catch (DepartmentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	personService.delRelativity(p, dep);
	
		
	}
	@Test
	public void testSave() {
	Person p=new Person();
	try {
		p = personService.getPersonById(1);
	} catch (PersonException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Department dep=new Department();
	try {
		dep = departmentService.getDepartmentById(1);
	} catch (DepartmentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	personService.saveRelativity(p, dep,"科长",1);
	
	}
	@Test
	public void testChangepos() {
	Person p=new Person();
	try {
		p = personService.getPersonById(1);
	} catch (PersonException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Department dep=new Department();
	try {
		dep = departmentService.getDepartmentById(1);
	} catch (DepartmentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	personService.changePosition(p, dep, "局长");
	
	}
	@Test
	public void testAmin() {
	Person p=new Person();
	try {
		p = personService.getPersonById(1);
	} catch (PersonException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Department dep=new Department();
	try {
		dep = departmentService.getDepartmentById(1);
	} catch (DepartmentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	personService.changeAdminFlag(p, dep, 0);
	
	}
}
