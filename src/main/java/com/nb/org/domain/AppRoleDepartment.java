package com.nb.org.domain;

public class AppRoleDepartment {
	private Integer id;
	private AppRole appRole;
	private Department department;
	public AppRoleDepartment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AppRoleDepartment(Integer id, AppRole appRole, Department department) {
		super();
		this.id = id;
		this.appRole = appRole;
		this.department = department;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public AppRole getAppRole() {
		return appRole;
	}
	public void setAppRole(AppRole appRole) {
		this.appRole = appRole;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	@Override
	public String toString() {
		return "AppRoleDepartment [id=" + id + ", appRole=" + appRole + ", department=" + department + "]";
	}
	


	

}
