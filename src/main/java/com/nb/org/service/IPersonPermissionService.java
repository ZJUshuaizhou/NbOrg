package com.nb.org.service;

public interface IPersonPermissionService {
	//根据所要操作的用户id、操作者id、当前登录部门id验证权限
	public int getPersonOperationPermission(int perId,int operatorId,int depId) throws Exception;
}
