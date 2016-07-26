package com.nb.org.testmybatis;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.nb.org.domain.Department;
import com.nb.org.domain.Person;
import com.nb.org.exception.DepartmentException;
import com.nb.org.exception.PersonException;
import com.nb.org.service.IDepartmentService;
import com.nb.org.service.IPersonService;
import com.nb.org.service.impl.PersonServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner�?
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})

public class TestDepartmentDao {
	private static Logger logger = Logger.getLogger(TestDepartmentDao.class);
//	private ApplicationContext ac = null;
	@Resource
	private IDepartmentService departmentService = null;
	@Resource
	private IPersonService personService = null;
//	@Before
//	public void before() {
//		ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//		userService = (IUserService) ac.getBean("userService");
//	}
	@Test
	public void testGetDep() {
		Department dep=new Department();
		try {
			dep = departmentService.getDepartmentById(1);
		} catch (DepartmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(dep);
		logger.info(JSON.toJSONString(dep));
	}
	@Test
	public void testInsertDep() {
		Department dep=new Department();
		dep.setAddress("浙江省宁波市XX区");
		dep.setContactNumber("123456");
		dep.setContactPerson("小李");
		dep.setDescription("一段描述");
		dep.setName("工商局城管2支队");
		dep.setSn("AA03");
		try {
			dep.setParentdep(departmentService.getDepartmentById(3));
		} catch (DepartmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//departmentService.insertDepartment(dep);
		
		logger.info(JSON.toJSONString(dep));
	}
	@Test
	public void testGetDepListByParentDep() {
		Department dep=new Department();
		try {
			dep = departmentService.getDepartmentById(2);
		} catch (DepartmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Department> list=new ArrayList<Department>();
		try {
			list = departmentService.getDepByParentDep(dep);
		} catch (DepartmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Department d:list){
			System.out.println(d);
		}
		logger.info(JSON.toJSONString(dep));
	}
	
	@Test
	public void testSelectDepByFullName() throws PersonException, DepartmentException{
		Department dep = departmentService.selectDepByFullName("宁波市");

		logger.info(JSON.toJSONString(dep));
		
		// 查询管理员所能操作的所有部门
		Person person = personService.getPersonByUserName("admin");
		//获取用户的所属部门
		Department depM = person.getDeps().get(0);
		Department department = departmentService.getDepartmentById(depM.getId());
		List<Department> list = null;
		try {
			list = departmentService.getDepByParentDep(depM);
		} catch (DepartmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list.add(department);
		logger.info(JSON.toJSONString(list));
		
		if (list.contains(department)) {
			System.out.println("success");
		} else {
			System.out.println("error");
		}
		
	}
}
