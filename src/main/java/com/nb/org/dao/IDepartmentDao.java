package com.nb.org.dao;

import java.util.List;

import com.nb.org.domain.Department;
import com.nb.org.exception.DepartmentException;
import com.nb.org.vo.DepartmentVO;

public interface IDepartmentDao {

	int insertDepartment(Department dep);

	Department getDepartmentById(int id);

	int updateDepartment(Department dep);

	int delDepartment(int id);

	Department selectDepBySN(String sn);
	
	Department selectDepByName(String name);
	
	List<Department> getDepByParentDep(Department parentdep);
	
	Department selectDepByFullName(String fullname);
	
	List<Department> getPageDepByParentDep(DepartmentVO departmentvo);
	
	int getTotalDepPagesByParentDep(int pid);
	List<Department> getPageDepByName(DepartmentVO departmentvo);
	
	int getTotalDepPagesByName(String name);
	List<Department> getPageDepByFullName(DepartmentVO departmentvo);
	int getTotalDepPagesByFullName(String fullname);
	
	List<Department> getPageDep(DepartmentVO vo);
	int getTotalDepPages(DepartmentVO vo);

	List<Department> getDepartmentsByNames(List<String> spliceNames);

}