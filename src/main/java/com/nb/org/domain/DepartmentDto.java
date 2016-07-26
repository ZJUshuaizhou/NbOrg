package com.nb.org.domain;

/**
 * @ClassName: DepartmentDto
 * @Description: 部门的数据传输对象（用于转换数据库中部门对象与实际部门对象之间的差异）
 * @author: Naughtior
 * @date:2016年2月21日 下午1:07:29
 */ 
public class DepartmentDto {
    private Integer id;
    
    private String sn;
    
    private String name;
    
    private String fullname;
    
    private String contactPerson;
    
    private String contactNumber;
    
    private String address;
    
    private String description;
    
    private String parentdep;
    
    private int isEdit;

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

	public String getParentdep() {
		return parentdep;
	}

	public void setParentdep(String parentdep) {
		this.parentdep = parentdep;
	}

	public int getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(int isEdit) {
		this.isEdit = isEdit;
	}
	
	

	public DepartmentDto(Integer id, String sn, String name, String fullname, String contactPerson,
			String contactNumber, String address, String description, String parentdep, int isEdit) {
		super();
		this.id = id;
		this.sn = sn;
		this.name = name;
		this.fullname = fullname;
		this.contactPerson = contactPerson;
		this.contactNumber = contactNumber;
		this.address = address;
		this.description = description;
		this.parentdep = parentdep;
		this.isEdit = isEdit;
	}
	

	@Override
	public String toString() {
		return "DepartmentDto [id=" + id + ", sn=" + sn + ", name=" + name + ", fullname=" + fullname
				+ ", contactPerson=" + contactPerson + ", contactNumber=" + contactNumber + ", address=" + address
				+ ", description=" + description + ", parentdep=" + parentdep + ", isEdit=" + isEdit + "]";
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
		result = prime * result + isEdit;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parentdep == null) ? 0 : parentdep.hashCode());
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
		DepartmentDto other = (DepartmentDto) obj;
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
		if (isEdit != other.isEdit)
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
		if (sn == null) {
			if (other.sn != null)
				return false;
		} else if (!sn.equals(other.sn))
			return false;
		return true;
	}
    
    
	
	
}