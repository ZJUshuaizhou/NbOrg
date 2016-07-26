package com.nb.org.service;

import java.util.List;

import com.nb.org.domain.AppRoleDepartment;

public interface IAppRoleDepService {
	
	int addDepsToRole(List<AppRoleDepartment> appRoleDepartments);
	
	int removeDepsFromRole(List<AppRoleDepartment> appRoleDepartments);

}
