package com.nb.org.service;

import java.util.List;

import com.nb.org.domain.Department;
import com.nb.org.domain.DepartmentSN;
import com.nb.org.domain.SimpleDepartment;
import com.nb.org.exception.DepartmentException;
import com.nb.org.vo.DepartmentVO;

public interface IDepartmentService {
	/*
	 * 插入部门
	 */
	int insertDepartment(Department dep, DepartmentSN sn) throws DepartmentException;

	/*
	 * 根据id查找部门
	 */
	Department getDepartmentById(int id) throws DepartmentException;
	

	/*
	 * 更新部门
	 */

	int updateDepartment(Department dep) throws DepartmentException;

	/*
	 * 删除部门
	 */

	int delDepartment(int id) throws DepartmentException;

	/*
	 * 根据SN查找部门
	 */

	Department selectDepBySN(String name) throws DepartmentException;
	/*
	 * 根据name查找部门
	 */
	
	Department selectDepByName(String name);
	/*
	 * 根据fullname查找部门
	 */
	
	Department selectDepByFullName(String fullname);

	/*
	 * 根据父部门查找所有直属子部门
	 */

	List<Department> getDepByParentDep(Department parentdep) throws DepartmentException;
	/*
	 * 查询所有子部门
	 */
	List<Department> getAllChildDeps(List<Department> resultlist,Department dep);
	
	
	/*
     * 根据父部门分页查找部门列表
     */
	
	DepartmentVO listDepsPageByPdep(DepartmentVO vo);
	
	/*
     * 根据名称分页查找部门列表
     */
	
	DepartmentVO listDepsPageByName(DepartmentVO vo);
	
	/*
     * 根据部门全称分页查找部门列表
     */
	
	DepartmentVO listDepsPageByFullName(DepartmentVO vo);
	
	/*
     * 分页列出全部部门列表
     */
	
	DepartmentVO listDepsPage(DepartmentVO vo);
	
	/*
	 * 通过部门名称的范围查询部门   by  upshi 20160302
	 * */
	List<Department> getDepartmentsByNames(List<String> spliceNames);
	/*
	 * 查询所有部门 by ishadow 20160822
	 */
	List<SimpleDepartment> selectAllDepartments(String name);
}
