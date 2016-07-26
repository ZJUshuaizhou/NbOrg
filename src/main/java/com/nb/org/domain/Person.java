package com.nb.org.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @ClassName: Person
 * @Description: 人员实体类
 * @author: Naughtior
 * @date:2016年2月21日 下午1:02:01
 */ 
public class Person {
	/**
	 * @Fields: id
	 * @Todo: 人员id
	 */ 
	private Integer id;

	/**
	 * @Fields: sn
	 * @Todo: 人员的唯一标识编号
	 */ 
	@JsonIgnore
	private String sn;

	/**
	 * @Fields: name
	 * @Todo: 人员真实姓名
	 */ 
	private String name;

	/**
	 * @Fields: username
	 * @Todo: 人员的登录名
	 */ 
	private String username;

	/**
	 * @Fields: gender
	 * @Todo: 性别
	 */ 
	private Integer gender;

	/**
	 * @Fields: telephone
	 * @Todo: 电话
	 */ 
	private String telephone;

	/**
	 * @Fields: mobilephone
	 * @Todo: 手机号码
	 */ 
	private String mobilephone;

	/**
	 * @Fields: email
	 * @Todo: 邮箱
	 */ 
	private String email;

	/**
	 * @Fields: createDep
	 * @Todo: 创建该人员的部门（只有创建者才可以修改人员信息）
	 */ 
	private Department createDep;

	/**
	 * @Fields: deps
	 * @Todo: 人员所在的部门列表（允许一个人存在于对个部门）
	 */ 
	private List<Department> deps = new ArrayList<Department>();

	public List<Department> getDeps() {
		return deps;
	}

	public void setDeps(List<Department> deps) {
		this.deps = deps;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Department getCreateDep() {
		return createDep;
	}

	public void setCreateDep(Department createDep) {
		this.createDep = createDep;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", sn=" + sn + ", name=" + name + ", username=" + username + ", gender=" + gender + ", telephone=" + telephone + ", mobilephone=" + mobilephone + ", email=" + email + ", createDep=" + createDep + ", deps=" + deps + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createDep == null) ? 0 : createDep.hashCode());
		result = prime * result + ((deps == null) ? 0 : deps.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mobilephone == null) ? 0 : mobilephone.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sn == null) ? 0 : sn.hashCode());
		result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (createDep == null) {
			if (other.createDep != null)
				return false;
		} else if (!createDep.equals(other.createDep))
			return false;
		if (deps == null) {
			if (other.deps != null)
				return false;
		} else if (!deps.equals(other.deps))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mobilephone == null) {
			if (other.mobilephone != null)
				return false;
		} else if (!mobilephone.equals(other.mobilephone))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sn == null) {
			if (other.sn != null)
				return false;
		} else if (!sn.equals(other.sn))
			return false;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}