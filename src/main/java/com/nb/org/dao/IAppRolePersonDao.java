package com.nb.org.dao;

import java.util.List;

import com.nb.org.domain.AppRolePerson;
import com.nb.org.domain.Person;

/**
 * @author huangxin
 * xin
 * 2016年1月25日
 */
public interface IAppRolePersonDao {
	public int insertRoleOfPerson(AppRolePerson rolePerson);
	public int deleteRoleOfPersonById(int id);
	public AppRolePerson getRoleOfPersonById(int id);
	public AppRolePerson getRoleOfPerson(AppRolePerson rolePerson);
	public int updateRoleOfPerson(AppRolePerson rolePerson);
	public List<Person> getPersonsOfRole(int roleId);
	public int deleteRoleOfPerson(AppRolePerson rolePerson);
	public int deletePersonsByRoleId(int roleId);
	public String getRoleNameForPerson(AppRolePerson rolePerson);
	
	//upshi 20160302
	public int addPersonsToRole(List<AppRolePerson> appRolePersons);
	public int removePersonsFromRole(List<AppRolePerson> appRolePersons);
}
