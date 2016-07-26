package com.nb.org.dao;

import com.nb.org.domain.DepartmentSN;


public interface IDepartmentSNDao {
    int deleteByPrimaryKey(Integer id);

    int insert(DepartmentSN record);

    int insertSelective(DepartmentSN record);

    DepartmentSN selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DepartmentSN record);

    int updateByPrimaryKey(DepartmentSN record);
    
    DepartmentSN selectByParentDeptIdAndFlag(int parentDeptId, int flag);
}