package com.nb.org.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nb.org.domain.Department;
import com.nb.org.domain.Position;
import com.nb.org.service.IDepartmentPermissionService;
import com.nb.org.service.IDepartmentService;
import com.nb.org.service.IPersonService;

@Service("departmantPermissionService")
public class DepartmentPermissionServiceImpl implements IDepartmentPermissionService {
	@Resource
	private IPersonService personService;
	@Resource
	private IDepartmentService departmentService;

	/**
	 * operateDepId:需要操作的部门id
	 * operatorId:操作者id
	 * depId:操作者所属部门id
	 */
	@Override
	public int getDepartmantOperationPermission(int operateDepId, int operatorId, int depId) throws Exception {
		// 获取操作者的所属部门
		Department department = departmentService.getDepartmentById(depId);
		// 获取操作者的职位
		Position position = personService.selectPositionByPerDep(operatorId, depId);
		// 判断操作者是否为管理员
		int adminFlag = 0;
		adminFlag = position.getAdminFlag();
		if (adminFlag == 1) {
			// 查询操作者所能操作的所有部门
			List<Department> list = new ArrayList<Department>();
			list = departmentService.getAllChildDeps(list, department);
			list.add(department);
			for (Department dep : list) {
				if (dep.getId() == operateDepId) {
					return 1;
				}
			}
			return 0;
		} else {
			return 0;
		}
	}

	@Override
	public int getDepartmantPermissionRest(int operateDepId, int operatorId) throws Exception {
		Position position = personService.selectPositionByPerDep(operatorId, operateDepId);
		while (position != null && position.getAdminFlag() != 1) {
			Department department = departmentService.getDepartmentById(operateDepId);
			if (department.getParentdep().getId() == 1) {
				break;
			}
			position = personService.selectPositionByPerDep(operatorId, department.getParentdep().getId());

		}
		return position == null ? 0 : position.getAdminFlag();

		// //获取操作者
		// Person person = personService.getPersonById(operatorId);
		// Position position =
		// personService.selectPositionByPerDep(operatorId,operateDepId);
		// //获取操作的所有部门
		// List<Department> listOfDep = person.getDeps();
		// for(Department department : listOfDep){
		// //获取操作者的职位
		// Position position = personService.selectPositionByPerDep(operatorId,
		// department.getId());
		// //判断操作者是否为管理员
		// int adminFlag = 0;
		// adminFlag = position.getAdminFlag();
		// if(adminFlag==1){
		// // 查询操作者所能操作的所有部门
		// List<Department> listOpeDep = null;
		// try {
		// listOpeDep = departmentService.getDepByParentDep(department);
		// } catch (DepartmentException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// listOpeDep.add(department);
		// for(Department dep : listOpeDep){
		// if(dep.getId()==operateDepId){
		// return 1;
		// }
		// }
		// return 0;
		// }else {
		// return 0;
		// }
		// }
		// return 0;
	}

	/* (non-Javadoc)
	 * @see com.nb.org.service.IDepartmentPermissionService#getDepartmantOperationPermissionWithoutAdmin(int, int, int)
	 */
	@Override
	public int getDepartmantOperationPermissionWithoutAdmin(int operateDepId, int operatorId, int depId)
			throws Exception {
		// TODO Auto-generated method stub
		Department department = departmentService.getDepartmentById(depId);
		// 获取操作者的职位
		Position position = personService.selectPositionByPerDep(operatorId, depId);
		// 判断操作者是否为管理员
		int adminFlag = 0;
		adminFlag = position.getAdminFlag();
			// 查询操作者所能操作的所有部门
			List<Department> list = new ArrayList<Department>();
			list = departmentService.getAllChildDeps(list, department);
			list.add(department);
			for (Department dep : list) {
				if (dep.getId() == operateDepId) {
					return 1;
				}
			}
			return 0;
	}

	
}
