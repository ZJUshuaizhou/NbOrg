package com.nb.org.vo;

import com.nb.org.domain.Department;

/**
 * @ClassName: DepartmentVO
 * @Description: 部门的VO对象，用于分页查询
 * @author: Naughtior
 * @date:2016年2月21日 下午1:40:21
 */ 
public class DepartmentVO extends PageVO<Department>{
	private int departmentId;
	private int pid;
	private String name;
	private String fullname;
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
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
	
	public DepartmentVO(int pageNo, int len,int departmentId, int pid, String name, String fullname) {
		super(pageNo,len);
		this.departmentId = departmentId;
		this.pid = pid;
		this.name = name;
		this.fullname = fullname;
	}
	public DepartmentVO(int pageNo, int len,int departmentId) {
		super(pageNo,len);
		this.departmentId = departmentId;
	}
	public DepartmentVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DepartmentVO(int pageNo, int len) {
		super(pageNo, len);
		// TODO Auto-generated constructor stub
	}
	
	

}
