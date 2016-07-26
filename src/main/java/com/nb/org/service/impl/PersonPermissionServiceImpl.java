package com.nb.org.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nb.org.domain.Department;
import com.nb.org.domain.Person;
import com.nb.org.domain.Position;
import com.nb.org.exception.DepartmentException;
import com.nb.org.service.IDepartmentService;
import com.nb.org.service.IPersonPermissionService;
import com.nb.org.service.IPersonService;

@Service("personPermissionServiceImpl")
public class PersonPermissionServiceImpl implements IPersonPermissionService{
	@Resource
	private IPersonService personService;
	@Resource
	private IDepartmentService departmentService;
	@Override
	public int getPersonOperationPermission(int perId, int operatorId, int depId) throws Exception {
		//获取所要操作人员
		Person operatePerson = personService.getPersonById(perId);	
		//获取所要操作人员对应的部门
		Department operateDepartment = departmentService.getDepartmentById(operatePerson.getCreateDep().getId());
		
		//获取用户
		Person person = personService.getPersonById(operatorId);
		//获取用户的所属部门
		Department department = departmentService.getDepartmentById(depId);
		//获取用户的职位
		Position position = personService.selectPositionByPerDep(person.getId(), department.getId());
		//判断用户是否为管理员
		int adminFlag = 0;
		adminFlag = position.getAdminFlag();
		
		if(adminFlag==1){
			// 查询操作者所能操作的所有部门
			List<Department> list = new ArrayList<Department>();
			list = departmentService.getAllChildDeps(list, department);
			list.add(department);
			for(Department dep : list){
				if(dep.getId()==operateDepartment.getId()){
					return 1;
				}
			}
			return 0;
		}else {
			return 0;
		}
	}
	
}
