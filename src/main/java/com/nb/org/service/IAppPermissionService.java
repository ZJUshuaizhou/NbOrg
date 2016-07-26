package com.nb.org.service;

public interface IAppPermissionService {
	//根据应用id、操作者id、当前登录部门id验证权限
	public int getAppOperationPermission(int appId,int operatorId,int depId) throws Exception;
}
