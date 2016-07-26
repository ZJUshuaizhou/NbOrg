package com.nb.org.dto;

import java.util.List;

public class RoleDTO {
	int id;
	String name;
	List<RolePersDTO> persons;
	List<RoleDepsDTO> departments;
	String personNames;
	String departmentNames;

	public RoleDTO() {
		super();
	}

	public RoleDTO(int id, String name, List<RolePersDTO> persons, List<RoleDepsDTO> departments) {
		super();
		this.id = id;
		this.name = name;
		this.persons = persons;
		this.departments = departments;

		StringBuilder ps = new StringBuilder();
		for (RolePersDTO p : persons) {

			ps.append(p.getName());
			ps.append(',');
		}
		if (ps.length() > 0)
			ps.deleteCharAt(ps.length() - 1);
		personNames = ps.toString();

		StringBuilder ds = new StringBuilder();
		for (RoleDepsDTO d : departments) {
			ds.append(d.getFullname());
			ds.append(',');
		}
		if (ds.length() > 0)
			ds.deleteCharAt(ds.length() - 1);
		departmentNames = ds.toString();
	}

	public String getPersonNames() {
		return personNames;
	}

	public void setPersonNames(String personNames) {
		this.personNames = personNames;
	}

	public String getDepartmentNames() {
		return departmentNames;
	}

	public void setDepartmentNames(String departmentNames) {
		this.departmentNames = departmentNames;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<RolePersDTO> getPersons() {
		return persons;
	}

	public void setPersons(List<RolePersDTO> persons) {
		this.persons = persons;
	}

	public List<RoleDepsDTO> getDepartments() {
		return departments;
	}

	public void setDepartments(List<RoleDepsDTO> departments) {
		this.departments = departments;
	}

	@Override
	public String toString() {
		return "RoleDTO [id=" + id + ", name=" + name + ", persons=" + persons + ", departments=" + departments + "]";
	}
}