package com.nb.org.testSN;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nb.org.domain.Department;
import com.nb.org.domain.DepartmentSN;
import com.nb.org.exception.DepartmentException;
import com.nb.org.service.IDepartmentSNService;
import com.nb.org.service.IDepartmentService;
import com.nb.org.util.SNGenerator;

@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner�?
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml","classpath:spring-mvc.xml"})
public class TestSN {

	@Autowired
	private IDepartmentSNService departmentSNService;
	
	@Autowired
	private IDepartmentService departmentService;
	
	@Autowired
	private SNGenerator snGenerator;
	
	@Test
	public void testSN() {
		DepartmentSN sn = new DepartmentSN(1,"1A","NB",1);
//		System.out.println(departmentSNDao.insert(sn));
		//System.out.println(departmentSNDao.selectByParentDeptIdAndFlag(2, 1));
		Department department=new Department();
		try {
			department = departmentService.getDepartmentById(1);
		} catch (DepartmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(department);
		DepartmentSN departmentSN = snGenerator.generateDepartmentSN(department);
		System.out.println(departmentSN);
	}

}
