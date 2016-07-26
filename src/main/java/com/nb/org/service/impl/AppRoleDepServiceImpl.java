package com.nb.org.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nb.org.dao.IAppRoleDeparmentDao;
import com.nb.org.domain.AppRoleDepartment;
import com.nb.org.service.IAppRoleDepService;

@Service("appRoleDepService")
public class AppRoleDepServiceImpl implements IAppRoleDepService {
	
	@Autowired
	private IAppRoleDeparmentDao appRoleDeparmentDao;
	
	public int addDepsToRole(List<AppRoleDepartment> appRoleDepartments) {
		return appRoleDeparmentDao.addDepsToRole(appRoleDepartments);
	}
	
	public int removeDepsFromRole(List<AppRoleDepartment> appRoleDepartments) {
		return appRoleDeparmentDao.removeDepsFromRole(appRoleDepartments);
	}

}
