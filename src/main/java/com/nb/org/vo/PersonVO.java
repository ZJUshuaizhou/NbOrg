package com.nb.org.vo;

import com.nb.org.domain.Person;

/**
 * @ClassName: PersonVO
 * @Description: 人员的VO对象，用于人员分页查询
 * @author: Naughtior
 * @date:2016年2月21日 下午1:40:50
 */ 
public class PersonVO extends PageVO<Person>{
	private int departmentId;
	private String pername;
	private int pdepartmentId;
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public String getName() {
		return pername;
	}
	public void setName(String name) {
		this.pername = name;
	}
	public int getPdepartmentId() {
		return pdepartmentId;
	}
	public void setPdepartmentId(int pdepartmentId) {
		this.pdepartmentId = pdepartmentId;
	}
	public PersonVO(int pageNo, int len,int departmentId, String name, int pdepartmentId) {
		super(pageNo,len);
		this.departmentId = departmentId;
		this.pername = name;
		this.pdepartmentId = pdepartmentId;
	}
	@Override
	public String toString() {
		return "PersonVO [departmentId=" + departmentId + ", pername=" + pername + ", pdepartmentId=" + pdepartmentId
				+ "]";
	}
	
	
	

	
}
