package com.nb.org.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nb.org.dao.IDepartmentSNDao;
import com.nb.org.domain.DepartmentSN;
import com.nb.org.service.IDepartmentSNService;


/**
 * @author upshi
 * @date 20160121
 */
@Service("departmentSNService")
public class DepartmentSNServiceImpl implements IDepartmentSNService {

	@Autowired
	private IDepartmentSNDao departmentSNDao;
	
	@Override
	public int insertDepartmentSN(DepartmentSN sn) {
		return departmentSNDao.insert(sn);
	}

	@Override
	public int updateDepartmentSN(DepartmentSN sn) {
		return departmentSNDao.updateByPrimaryKey(sn);
	}

	@Override
	public int deleteDepartmentSN(int id) {
		return departmentSNDao.deleteByPrimaryKey(id);
	}

	@Override
	public DepartmentSN getDepartmentSNById(int id) {
		return departmentSNDao.selectByPrimaryKey(id);
	}

	@Override
	public DepartmentSN selectByParentDeptIdAndFlag(int parentDeptId, int flag) {
		return departmentSNDao.selectByParentDeptIdAndFlag(parentDeptId, flag);
	}
	
}
