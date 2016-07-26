package com.nb.org.dao;

import java.util.List;

import com.nb.org.domain.AppRole;
import com.nb.org.dto.RoleDepsDTO;
import com.nb.org.dto.RolePersDTO;

/**
 * @author huangxin
 * xin
 * 2016年1月25日
 */
public interface IAppRoleDao {
	public int insertAppRole(AppRole role);
	public int deleteAppRoleById(int id);
	public int updateAppRole(AppRole role);
	public List<AppRole> getRolesByAppId(int appId);
	public AppRole getRoleById(int id);
	public int deleteRolesByAppId(int appId);
	
	public List<RolePersDTO> searchPerson(String name);
	public String getDepartmentById(int id);
	public List<RoleDepsDTO> searchDepartment(String name);
	public AppRole getRoleByNameAndApp(AppRole role);
	public int getAppIdByAppName(String appName);
}
