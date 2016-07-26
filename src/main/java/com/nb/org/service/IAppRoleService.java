package com.nb.org.service;

import java.util.List;

import com.nb.org.domain.AppRole;
import com.nb.org.dto.RoleDepsDTO;
import com.nb.org.dto.RolePersDTO;

/**
 * @author huangxin
 * xin
 * 2016年1月25日
 */
public interface IAppRoleService {
	public void addRoleForApp(AppRole role);
	public void removeRolesForApp(int appId);
	public void editRoleForApp(AppRole role);
	public List<AppRole> getRoleListForApp(int appId);
	public void removeRoleForApp(int roleId);
	public AppRole getRoleDetailForApp(int roleId);
	
	public AppRole getRoleByNameAndApp(AppRole role);
	public List<RolePersDTO> searchPerson(String name);
	public List<RoleDepsDTO> searchDepartment(String name);
	public List<AppRole> getRolesByAppName(String appName);

	public List<String> getRolesForPerson(String appName,String username);
	
	public int updateAppRole(AppRole appRole);
}
