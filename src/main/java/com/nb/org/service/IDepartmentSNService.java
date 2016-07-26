package com.nb.org.service;

import com.nb.org.domain.DepartmentSN;

/**
 * @author upshi
 * @date 20160121
 */
public interface IDepartmentSNService {
	
	//插入一条新的记录
	int insertDepartmentSN(DepartmentSN sn);
	
	//修改记录
	int updateDepartmentSN(DepartmentSN sn);
	
	//删除记录
	int deleteDepartmentSN(int id);
	
	//根据主键查询记录
	DepartmentSN getDepartmentSNById(int id);
	
	//根据父部门id和flag查询
	DepartmentSN selectByParentDeptIdAndFlag(int parentDeptId, int flag); 
	

}
