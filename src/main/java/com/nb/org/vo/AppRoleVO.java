package com.nb.org.vo;

import java.util.List;



/*
 * @author upshi
 * @time 20160301
 * 
 * */

public class AppRoleVO {

	private Integer id;
	private String name;
	private Integer appId;
	//增加角色时赋予的用户
	private List<String> personUserNames;
	//增加角色时赋予的部门
	private List<String> departmentNames;
	//修改角色时变动的用户
	private List<String> addUserNames;
	private List<String> removeUserNames;
	//修改角色时变动的部门
	private List<String> addDepartmentNames;
	private List<String> removeDepartmentNames;
	
	public AppRoleVO() {}
	
	public AppRoleVO(Integer id, String name, Integer appId, List<String> personUserNames, List<String> departmentNames, List<String> addUserNames, List<String> removeUserNames, List<String> addDepartmentNames, List<String> removeDepartmentNames) {
		super();
		this.id = id;
		this.name = name;
		this.appId = appId;
		this.personUserNames = personUserNames;
		this.departmentNames = departmentNames;
		this.addUserNames = addUserNames;
		this.removeUserNames = removeUserNames;
		this.addDepartmentNames = addDepartmentNames;
		this.removeDepartmentNames = removeDepartmentNames;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public List<String> getPersonUserNames() {
		return personUserNames;
	}

	public void setPersonUserNames(List<String> personUserNames) {
		this.personUserNames = personUserNames;
	}

	public List<String> getDepartmentNames() {
		return departmentNames;
	}

	public void setDepartmentNames(List<String> departmentNames) {
		this.departmentNames = departmentNames;
	}

	public List<String> getAddUserNames() {
		return addUserNames;
	}

	public void setAddUserNames(List<String> addUserNames) {
		this.addUserNames = addUserNames;
	}

	public List<String> getRemoveUserNames() {
		return removeUserNames;
	}

	public void setRemoveUserNames(List<String> removeUserNames) {
		this.removeUserNames = removeUserNames;
	}

	public List<String> getAddDepartmentNames() {
		return addDepartmentNames;
	}

	public void setAddDepartmentNames(List<String> addDepartmentNames) {
		this.addDepartmentNames = addDepartmentNames;
	}

	public List<String> getRemoveDepartmentNames() {
		return removeDepartmentNames;
	}

	public void setRemoveDepartmentNames(List<String> removeDepartmentNames) {
		this.removeDepartmentNames = removeDepartmentNames;
	}

	@Override
	public String toString() {
		return "AppRoleVO [id=" + id + ", name=" + name + ", appId=" + appId + ", personUserNames=" + personUserNames + ", departmentNames=" + departmentNames + ", addUserNames=" + addUserNames + ", removeUserNames=" + removeUserNames + ", addDepartmentNames=" + addDepartmentNames + ", removeDepartmentNames=" + removeDepartmentNames + "]";
	}

}
