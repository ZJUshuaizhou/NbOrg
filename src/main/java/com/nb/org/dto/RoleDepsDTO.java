package com.nb.org.dto;

public class RoleDepsDTO {
	int id;
	String fullname;
	
	
	
	public RoleDepsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoleDepsDTO(int id, String fullname) {
		super();
		this.id = id;
		this.fullname = fullname;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Override
	public String toString() {
		return "RoleDepsDTO [id=" + id + ", fullname=" + fullname + "]";
	}
	
}
