package com.nb.org.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @ClassName: Department
 * @Description: 部门实体类
 * @author: Naughtior
 * @date:2016年2月21日 下午12:56:32
 */ 
public class Department {
    /**
     * @Fields: id
     * @Todo: 部门id
     */ 
    private Integer id;
    
    /**
     * @Fields: sn
     * @Todo: 部门唯一标识号
     */ 
    private String sn;
    
    /**
     * @Fields: name
     * @Todo: 部门名称（可能是简略名称，如办公室）
     */ 
    private String name;
    
    /**
     * @Fields: fullname
     * @Todo: 部门全称（部门的全称，如宁波市公安局办公室）
     */ 
    private String fullname;
    
    /**
     * @Fields: contactPerson
     * @Todo: 部门的联系人
     */ 
    private String contactPerson;
    
    /**
     * @Fields: contactNumber
     * @Todo: 部门的联系方式
     */ 
    private String contactNumber;
    
    /**
     * @Fields: address
     * @Todo: 部门的地址
     */ 
    private String address;
    
    /**
     * @Fields: description
     * @Todo: 部门的简介或描述
     */ 
    private String description;
    
    /*
     * upshi 20160219 转json时不包含父部门信息，递归很深jackson会报错
     * */
    /**
     * @Fields: parentdep
     * @Todo: 该部门的上级部门（父部门）
     */ 
    @JsonIgnore
    private Department parentdep;
    
    /**
     * @Fields: persons
     * @Todo: 该部门的人员列表
     */ 
    private List<Person> persons;

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
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

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Department getParentdep() {
		return parentdep;
	}

	public void setParentdep(Department parentdep) {
		this.parentdep = parentdep;
	}

	@Override
	public String toString() {
		String depName = "";
		if(parentdep!=null)
			depName=parentdep.getFullname();
		return "Department [id=" + id + ", sn=" + sn + ", name=" + name + ", fullname=" + fullname + ", contactPerson="
				+ contactPerson + ", contactNumber=" + contactNumber + ", address=" + address + ", description="
				+ description + ", parentdep=" + depName + ", persons=" + persons + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((contactNumber == null) ? 0 : contactNumber.hashCode());
		result = prime * result + ((contactPerson == null) ? 0 : contactPerson.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((fullname == null) ? 0 : fullname.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parentdep == null) ? 0 : parentdep.hashCode());
		result = prime * result + ((persons == null) ? 0 : persons.hashCode());
		result = prime * result + ((sn == null) ? 0 : sn.hashCode());
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
		Department other = (Department) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (contactNumber == null) {
			if (other.contactNumber != null)
				return false;
		} else if (!contactNumber.equals(other.contactNumber))
			return false;
		if (contactPerson == null) {
			if (other.contactPerson != null)
				return false;
		} else if (!contactPerson.equals(other.contactPerson))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (fullname == null) {
			if (other.fullname != null)
				return false;
		} else if (!fullname.equals(other.fullname))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parentdep == null) {
			if (other.parentdep != null)
				return false;
		} else if (!parentdep.equals(other.parentdep))
			return false;
		if (persons == null) {
			if (other.persons != null)
				return false;
		} else if (!persons.equals(other.persons))
			return false;
		if (sn == null) {
			if (other.sn != null)
				return false;
		} else if (!sn.equals(other.sn))
			return false;
		return true;
	}

	
	
	
}