package com.nb.org.dto;

public class RolePersDTO {
	int id;
	String name;
	String dep;
	
	public RolePersDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RolePersDTO(int id, String name, String dep) {
		super();
		this.id = id;
		this.name = name;
		this.dep = dep;
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
	public String getDep() {
		return dep;
	}
	public void setDep(String dep) {
		this.dep = dep;
	}
	@Override
	public String toString() {
		return "RolePersDTO [id=" + id + ", name=" + name + ", dep=" + dep + "]";
	}
	
	
}
