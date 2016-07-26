package com.nb.org.testapp;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nb.org.dao.IAppInfoDao;
import com.nb.org.dao.IAppRoleDao;
import com.nb.org.dao.IAppRoleDeparmentDao;
import com.nb.org.dao.IAppRolePersonDao;
import com.nb.org.domain.AppInfo;
import com.nb.org.domain.AppRole;
import com.nb.org.domain.Department;
import com.nb.org.domain.Person;
import com.nb.org.service.IAppInfoService;
import com.nb.org.service.IAppRoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestAppRole {
	@Autowired
	private IAppInfoDao dao;

	@Autowired
	private IAppRoleDeparmentDao depDao;

	@Autowired
	private IAppRoleService roleService;
	
	@Autowired
	private IAppInfoService roleInfoService;
	
	@Autowired
	private IAppRoleDao roleDao;

	@Autowired
	private IAppRolePersonDao personDao;

	@Autowired
	private IAppRoleDao appRole;

	@Autowired
	private IAppInfoDao appDao;

	@Test
	public void testDep() {
		depDao.deleteDepartmentsByRoleId(28);
	}

	@Test
	public void testRole() {
		// List<Person> persons = personDao.getPersonsOfRole(1);
		List<Person> per = personDao.getPersonsOfRole(1);
		System.out.println(per);

		int a = 1;
		List<AppRole> app = appRole.getRolesByAppId(a);

		List<Department> departments = depDao.getDepartmentsByRole(1);
		// AppInfo app = dao.getAppById(1);
		System.out.println(app.size() + ":" + app);

		AppInfo appInfo = appDao.getAppById(1);

		System.out.println(appInfo);
	}

	@Test
	public void testAddRole() {
		// AppRole role = new AppRole();
		// role.setAppId(3);
		// role.setName("abc");
		// roleDao.insertAppRole(role);
		List<Person> persons = new ArrayList<Person>();
		Person p = new Person();
		p.setId(7);
		persons.add(p);
		p = new Person();
		p.setId(10);
		persons.add(p);

		List<Department> departments = new ArrayList<Department>();
		Department d = new Department();
		d.setId(8);
		departments.add(d);
		d = new Department();
		d.setId(10);
		departments.add(d);

		roleService.addRoleForApp(new AppRole(0, "abcd", 3, persons, departments));
	}

	@Test
	public void testApi() {
		System.out.println(roleService.getRolesForPerson("huangxin", "admin"));
		System.out.println(roleService.getRolesByAppName("huangxin"));
		System.out.println(roleService.getRoleListForApp(8));
		System.out.println(roleInfoService.getAppsByCreator("admin"));
		System.out.println(roleInfoService.getAppsByDepartmentId(1));
		
	}
}
