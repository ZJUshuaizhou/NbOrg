package com.nb.org.dao;

import java.util.List;

import com.nb.org.domain.AppRoleDepartment;
import com.nb.org.domain.Department;

/**
 * @author huangxin
 * xin
 * 2016年1月25日
 */
public interface IAppRoleDeparmentDao {
	public AppRoleDepartment getRoleOfDepartmentById(int id);
	public AppRoleDepartment getRoleOfDepartment(AppRoleDepartment roleDep);
	
	
	public List<Department> getDepartmentsByRole(int roleId);
	
	
	public int updateRoleOfDepartment(AppRoleDepartment roleDep);
	public int deleteRoleOfDepartmentById(int id);
	public int deleteRoleOfDepartment(AppRoleDepartment roleDep);
	public int deleteDepartmentsByRoleId(int roleId);
	
	
	public int insertRoleOfDepartment(AppRoleDepartment roleDep);
	
	//upshi 20160302
	int addDepsToRole(List<AppRoleDepartment> appRoleDepartments);
	int removeDepsFromRole(List<AppRoleDepartment> appRoleDepartments);
}
