package com.nb.org.domain;

import java.util.List;

public class AppRole {
	private Integer id;
	private String name;
	private Integer appId;
	private List<Person> persons;
	private List<Department> departments;
	public AppRole(Integer id, String name, Integer appId, List<Person> persons, List<Department> departments) {
		super();
		this.id = id;
		this.name = name;
		this.appId = appId;
		this.persons = persons;
		this.departments = departments;
	}
	public AppRole() {
		super();
		// TODO Auto-generated constructor stub
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
	public List<Person> getPersons() {
		return persons;
	}
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	public List<Department> getDepartments() {
		return departments;
	}
	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
	@Override
	public String toString() {
		return "AppRole [id=" + id + ", name=" + name + ", appId=" + appId + ", persons=" + persons + ", departments="
				+ departments + "]";
	}

	
}
