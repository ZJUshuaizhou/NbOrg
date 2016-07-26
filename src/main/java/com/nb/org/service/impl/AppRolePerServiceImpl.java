package com.nb.org.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nb.org.dao.IAppRolePersonDao;
import com.nb.org.domain.AppRolePerson;
import com.nb.org.service.IAppRolePerService;

@Service("appRolePerService")
public class AppRolePerServiceImpl implements IAppRolePerService{
	
	@Autowired
	private IAppRolePersonDao appRolePersonDao;

	public int addPersonsToRole(List<AppRolePerson> appRolePersons) {
		return appRolePersonDao.addPersonsToRole(appRolePersons);
	}

	public int removePersonsFromRole(List<AppRolePerson> appRolePersons) {
		return appRolePersonDao.removePersonsFromRole(appRolePersons);
	}

}
