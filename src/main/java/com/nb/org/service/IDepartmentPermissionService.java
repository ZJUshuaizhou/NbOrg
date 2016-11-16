package com.nb.org.service;

public interface IDepartmentPermissionService {
	//根据部门id、操作者id、当前登录部门id验证权限
	public int getDepartmantOperationPermission(int operateDepId,int operatorId,int depId) throws Exception;
	public int getDepartmantOperationPermissionWithoutAdmin(int operateDepId,int operatorId,int depId) throws Exception;
	//根据所要操作的部门id、操作者id验证权限
	public int getDepartmantPermissionRest(int operateDepId,int operatorId) throws Exception;
   
}
