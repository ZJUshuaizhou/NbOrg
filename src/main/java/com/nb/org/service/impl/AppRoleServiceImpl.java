package com.nb.org.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nb.org.dao.IAppInfoDao;
import com.nb.org.dao.IAppRoleDao;
import com.nb.org.dao.IAppRoleDeparmentDao;
import com.nb.org.dao.IAppRolePersonDao;
import com.nb.org.dao.IPersonDao;
import com.nb.org.domain.AppRole;
import com.nb.org.domain.AppRoleDepartment;
import com.nb.org.domain.AppRolePerson;
import com.nb.org.domain.Department;
import com.nb.org.domain.Person;
import com.nb.org.dto.RoleDepsDTO;
import com.nb.org.dto.RolePersDTO;
import com.nb.org.service.IAppRoleService;

@Service("appRoleService")
public class AppRoleServiceImpl implements IAppRoleService {

	@Autowired
	private IAppRoleDeparmentDao roleDepDao;
	
	@Autowired
	private IAppRolePersonDao rolePersonDao;
	
	@Autowired 
	private IPersonDao personDao;
	
	@Autowired
	private IAppRoleDao roleDao;
	@Override
	@Transactional
	public void addRoleForApp(AppRole role) {
		roleDao.insertAppRole(role);
		AppRole tmp = roleDao.getRoleByNameAndApp(role);
		role.setId(tmp.getId());
		List<Person> persons = role.getPersons();
		if(persons != null) {
			for(Person p : persons){
				AppRolePerson person = new AppRolePerson();
				person.setPerson(p);
				person.setAppRole(role);
				rolePersonDao.insertRoleOfPerson(person);
			}
		}
		List<Department> departments = role.getDepartments();
		if(departments != null) {
			for (Department d:departments){
				AppRoleDepartment department = new AppRoleDepartment();
				department.setDepartment(d);
				department.setAppRole(role);
				roleDepDao.insertRoleOfDepartment(department);
			}
		}
	}

	@Override
	@Transactional
	public void removeRoleForApp(int roleId) {
		rolePersonDao.deletePersonsByRoleId(roleId);
		roleDepDao.deleteDepartmentsByRoleId(roleId);
		roleDao.deleteAppRoleById(roleId);
	}

	@Override
	@Transactional
	public void removeRolesForApp(int appId) {
		List<AppRole> roles = roleDao.getRolesByAppId(appId);
		for(AppRole r : roles){
			rolePersonDao.deletePersonsByRoleId(r.getId());
			roleDepDao.deleteDepartmentsByRoleId(r.getId());
		}
		roleDao.deleteRolesByAppId(appId);
	}

	@Override
	@Transactional
	public void editRoleForApp(AppRole role) {
		roleDao.updateAppRole(role);
		List<Person> persons = role.getPersons();
		List<Department> departments = role.getDepartments();
		rolePersonDao.deletePersonsByRoleId(role.getId());
		roleDepDao.deleteDepartmentsByRoleId(role.getId());
		for(Person p : persons){
			AppRolePerson person = new AppRolePerson();
			person.setAppRole(role);
			person.setPerson(p);
			rolePersonDao.insertRoleOfPerson(person);
		}
		for(Department d :departments){
			AppRoleDepartment department = new AppRoleDepartment();
			department.setAppRole(role);
			department.setDepartment(d);
			roleDepDao.insertRoleOfDepartment(department);
		}
	}

	@Override
	public List<AppRole> getRoleListForApp(int appId) {
		return roleDao.getRolesByAppId(appId);
	}
	@Override
	public AppRole getRoleDetailForApp(int roleId) {
		return roleDao.getRoleById(roleId);
	}

	@Override
	public List<RolePersDTO> searchPerson(String name) {
		// TODO Auto-generated method stub
		List<RolePersDTO> persons=roleDao.searchPerson(name);
//		ArrayList<RolePersDTO> dto = new  ArrayList<RolePersDTO>();
//		for(Person p:persons){
//			RolePersDTO per = new RolePersDTO(p.getId(),p.getName(),p.getCreateDep().getFullname());
//			dto.add(per);
//		}
		return persons;
	}

	@Override
	public List<RoleDepsDTO> searchDepartment(String name) {
		// TODO Auto-generated method stub
		return roleDao.searchDepartment(name);
	}

	@Override
	public AppRole getRoleByNameAndApp(AppRole role) {		
		return roleDao.getRoleByNameAndApp(role);
	}

	@Override
	@Transactional
	public List<AppRole> getRolesByAppName(String appName) {
		// TODO Auto-generated method stub
		try{
		int appId = roleDao.getAppIdByAppName(appName);	
		return roleDao.getRolesByAppId(appId);
		}catch(Exception e){
			return new ArrayList<AppRole>();
		}
	}

	@Override
	public List<String> getRolesForPerson(String appName,String username) {
		// TODO Auto-generated method stub
		
		ArrayList<String> roleNames = new ArrayList<String>();
		AppRolePerson rolePerson=new AppRolePerson();
		AppRoleDepartment roleDepartment=new AppRoleDepartment();
		try{
		Person person = personDao.selectPersonByUserName(username);
		rolePerson.setPerson(person);
		int appId = roleDao.getAppIdByAppName(appName);	
		List<AppRole> roles = roleDao.getRolesByAppId(appId);
		for(AppRole r : roles){
			rolePerson.setAppRole(r);
			String name = rolePersonDao.getRoleNameForPerson(rolePerson);
			if(name != null){
				roleNames.add(name);
			}
			for(Department d : person.getDeps()){
				roleDepartment.setDepartment(d);
				roleDepartment.setAppRole(r);
				AppRoleDepartment roleDep = roleDepDao.getRoleOfDepartment(roleDepartment);
				if(roleDep != null && roleDep.getAppRole() != null){
					roleNames.add(roleDep.getAppRole().getName());
				}
			}
		}
		HashSet<String> temp = new HashSet<String>(roleNames);
		roleNames.clear();
		roleNames.addAll(temp);
		}catch(Exception e){
			return roleNames;
		}
		return roleNames;
	}

	public int updateAppRole(AppRole appRole) {
		return roleDao.updateAppRole(appRole);
	}

}
