package com.nb.org.domain;

/**
 * @ClassName: Position
 * @Description: 人员-部门-职位关联类
 * @author: Naughtior
 * @date:2016年2月21日 下午1:05:08
 */ 
public class Position {
	/**
	 * @Fields: id
	 * @Todo: id
	 */ 
	private Integer id;
	/**
	 * @Fields: name
	 * @Todo: 职位名称
	 */ 
	private String name;
	/**
	 * @Fields: person
	 * @Todo: 人员
	 */ 
	private Person person;
	/**
	 * @Fields: dep
	 * @Todo: 部门
	 */ 
	private Department dep;
	/**
	 * @Fields: adminFlag
	 * @Todo: 是否拥有管理权限
	 */ 
	private int adminFlag;

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

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Department getDep() {
		return dep;
	}

	public void setDep(Department dep) {
		this.dep = dep;
	}

	public int getAdminFlag() {
		return adminFlag;
	}

	public void setAdminFlag(int adminFlag) {
		this.adminFlag = adminFlag;
	}

	@Override
	public String toString() {
		return "Position [id=" + id + ", name=" + name + ", person=" + person
				+ ", dep=" + dep + ", adminFlag=" + adminFlag + "]";
	}

}