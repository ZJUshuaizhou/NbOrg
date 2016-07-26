package com.nb.org.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nb.org.dao.IDepartmentDao;
import com.nb.org.dao.IPersonDao;
import com.nb.org.dao.IPositionDao;
import com.nb.org.domain.Department;
import com.nb.org.domain.Person;
import com.nb.org.domain.Position;
import com.nb.org.service.IPositionService;

@Service("positionService")
public class PositionServiceImpl implements IPositionService {

	@Autowired
	private IPositionDao positionDao;
	
	@Autowired
	private IPersonDao personDao;
	
	@Autowired
	private IDepartmentDao departmentDao;
	
	/*
	 * @author upshi
	 * @date 20160121
	 * 判断是否是管理员
	 */
	public boolean isAdmin(String userName, String departmentFullName) {
		Person person = personDao.selectPersonByUserName(userName);
		Department department = departmentDao.selectDepByFullName(departmentFullName);
		if(department != null) {
			Position position = positionDao.selectPositionByPerDep(person.getId(), department.getId());
			if(position != null && position.getAdminFlag() == 1) {
				return true;
			}
		} 
		return false;
	}

	public Position selectPositionByPerDep(int perId, int depId) {
		return positionDao.selectPositionByPerDep(perId, depId);
	}

	public List<Position> selectPositionsByPersonId(int personId) {
		return positionDao.selectPositionsByPersonId(personId);
	}

}
